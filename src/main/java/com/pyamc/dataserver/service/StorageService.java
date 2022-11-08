package com.pyamc.dataserver.service;

import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.Callable;

@Service
public class StorageService {
    private static final String fileName = "src/CommitFile.meil";
    private static final int chunkSize = 64 * 1024 * 1024;
    private static final int md5BytesSize = 16;
    public Result saveChunk(ChunkInfo chunk) {
        try {
            RandomAccessFile commitFile = new RandomAccessFile(fileName, "rw");
            commitFile.seek(Long.parseLong(chunk.getOffset()));
            commitFile.write(chunk.getBuffer(), 0, chunk.getBuffer().length);
            commitFile.close();
            // thread pool async update meta data
            return Result.Success(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.Fail(null);
    }

    public Result readChunk(String offset) {
        try {
            RandomAccessFile commitFile = new RandomAccessFile(fileName, "r");
            commitFile.seek(Long.parseLong(offset));
            byte[] buffer = new byte[chunkSize];
            commitFile.read(buffer, 0, chunkSize);
            // 校验磁盘文件
            byte[] md5Bytes = Arrays.copyOfRange(buffer, 0, md5BytesSize);
            byte[] fileBytes = Arrays.copyOfRange(buffer, md5BytesSize + 1, chunkSize);
            if (!FileUtil.byte2hex(md5Bytes).equals(FileUtil.getMD5sum(fileBytes))) {
                return Result.Fail(null);
            }
            return Result.Success(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.Fail(null);
    }
}
