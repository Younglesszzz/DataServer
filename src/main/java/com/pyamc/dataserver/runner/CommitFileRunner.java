package com.pyamc.dataserver.runner;

import com.pyamc.dataserver.entity.DataNode;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
public class CommitFileRunner implements ApplicationRunner {
    private static String fileName = "src/CommitFile";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // --addr=ip:xxxx --name=DATANODE_01
        Set<String> options = args.getOptionNames();
        DataNode node = new DataNode();
        if (options.contains("name")) {
            List<String> optionalValues = args.getOptionValues("name");
            fileName = fileName + optionalValues.get(0);
        }
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
