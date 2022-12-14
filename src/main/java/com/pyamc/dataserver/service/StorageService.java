package com.pyamc.dataserver.service;

import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.runner.CommitFileRunner;
import com.pyamc.dataserver.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
public class StorageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String fileName = CommitFileRunner.fileName;
    private static final int ChunkCapacity = 64 * 1024 * 1024;
    private static final int ChunkKeyBytesNum = 36;
    private static final int ChunkSizeBytesNum = 4;
    private static final int CheckSumBytesNum = 32;
    private static final int FileBytesSize = ChunkCapacity - ChunkKeyBytesNum - ChunkSizeBytesNum - CheckSumBytesNum;

    public Result saveChunk(MultipartFile chunk, long offset) {
        try {
            RandomAccessFile commitFile = new RandomAccessFile(CommitFileRunner.fileName, "rw");
            byte[] chunkBytes = chunk.getBytes();
            commitFile.seek(offset);
            commitFile.write(chunkBytes, 0, chunkBytes.length);
            commitFile.close();
            // thread pool async update meta data
            return Result.Success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.Fail(null);
    }

    public void readChunk(String offset, String chunkKey, HttpServletResponse response) {
        logger.info("ReadChunk#Offset {} ChunkKey {}", offset, chunkKey);
        try {
            RandomAccessFile commitFile = new RandomAccessFile(CommitFileRunner.fileName, "r");
            commitFile.seek(Long.parseLong(offset));
            byte[] chunkBytes = new byte[ChunkCapacity];
            FileInputStream in = new FileInputStream(commitFile.getFD());
            long start = System.currentTimeMillis();
            int num = in.read(chunkBytes, 0, chunkBytes.length);
            long end = System.currentTimeMillis();
            logger.info("ReadChunk#bytesNum {} #time {}", num, end - start);
            // ??????????????????
            byte[] chunkKeyBytes = Arrays.copyOfRange(chunkBytes, 0, ChunkKeyBytesNum);
            byte[] sizeBytes = Arrays.copyOfRange(chunkBytes, ChunkKeyBytesNum, ChunkKeyBytesNum + ChunkSizeBytesNum);
            int size = ByteBuffer.wrap(sizeBytes).getInt();
            byte[] toCheck = Arrays.copyOfRange(chunkBytes, 0, ChunkKeyBytesNum + ChunkSizeBytesNum + size);
            byte[] data = Arrays.copyOfRange(chunkBytes, ChunkKeyBytesNum + ChunkSizeBytesNum, ChunkKeyBytesNum + ChunkSizeBytesNum + size);
            byte[] checkSumBytes = Arrays.copyOfRange(chunkBytes, ChunkKeyBytesNum + ChunkSizeBytesNum + size, ChunkKeyBytesNum + ChunkSizeBytesNum + size + 32);
            String readKey = new String(chunkKeyBytes);
            // ??????????????????ChunkKey
            if (!readKey.equals(chunkKey)) {
                logger.error("Chunk Key Error");
                return;
            }
            // ??????md5
            String recheckMd5 = FileUtil.getMD5sum(toCheck);
            String saveMd5 = new String(checkSumBytes, StandardCharsets.UTF_8);
            if (!recheckMd5.equals(saveMd5)) {
                logger.error("Chunk CheckSum Error");
                return;
            }
            ServletOutputStream out =  response.getOutputStream();
            response.setContentType("application/octet-stream");
            response.setContentLength(data.length);
            out.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
