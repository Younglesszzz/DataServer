package com.pyamc.dataserver.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataNodeRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // --addr=ip:xxxx --name=DATANODE_01
        String[] sourceArgs = args.getSourceArgs();
    }
}
