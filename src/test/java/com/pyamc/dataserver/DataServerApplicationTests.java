package com.pyamc.dataserver;

import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.service.StorageService;
import com.pyamc.dataserver.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
class DataServerApplicationTests {

    @Resource
    private StorageService storageService;

    @Test
    void contextLoads() {
    }

    @Test
    void writeChunk() {
        Result res = storageService.saveChunk(new ChunkInfo("0", new byte[] {1,1}));
        assert res.code.equals("0");
    }

    @Test
    void ReadChunk() {
        Result res = storageService.readChunk("0");
        System.out.println(Arrays.toString((byte[]) res.data));
    }

    @Test
    void getMd5Code() {
        Result res = storageService.readChunk("0");
        FileUtil.getMD5sum((byte[]) res.getData());
    }
}
