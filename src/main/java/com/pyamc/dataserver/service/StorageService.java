package com.pyamc.dataserver.service;

import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.channels.FileChannel;

@Service
public class StorageService {
    private static final String fileName = "src/CommitFile.meil";

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

    public Result readChunk(long offset) {
        try {
            RandomAccessFile commitFile = new RandomAccessFile(fileName, "r");
            commitFile.seek(0);
            byte[] buffer = new byte[10];
            commitFile.read(buffer);
            return Result.Success(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.Fail(null);
    }
}
