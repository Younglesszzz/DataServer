package com.pyamc.dataserver.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class CommitFileRunner implements ApplicationRunner {
    private static final String fileName = "src/CommitFile.meil";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File commitFile = new File(fileName);
        if (!commitFile.exists()) {
            commitFile.getParentFile().mkdir();
            try {
                commitFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] zeros = new byte[16 * 1024];
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(commitFile, true));
        int offset = 0;
        while (offset < Math.pow(2, 28)) {
            out.write(zeros);
            offset += zeros.length;
        }
        return;
    }
}
