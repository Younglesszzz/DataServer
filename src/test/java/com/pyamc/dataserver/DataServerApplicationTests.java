package com.pyamc.dataserver;

import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.service.StorageService;
import com.pyamc.dataserver.util.FileUtil;
import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
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
//        Result res = storageService.saveChunk(new ChunkInfo("0", new byte[] {1,1}));
//        assert res.code.equals("0");
    }

    @Test
    void ReadChunk() {
        Result res = storageService.readChunk("0", "2945C3ACDD8ADEF1396C54CF9F34DD230000");
        System.out.println(Arrays.toString((byte[]) res.data));
    }

    @Test
    void getMd5Code() {
//        Result res = storageService.readChunk("0");
//        String code = FileUtil.getMD5sum((byte[]) res.getData());
//        System.out.println(code);
//        System.out.println(Arrays.toString((code + "_1").getBytes()));
    }

    @Test
    void toHex() {
        byte[] b = new byte[]{0, 0, -41, 24};
        System.out.println(Arrays.toString(b));
        System.out.println(ByteBuffer.wrap(b).getInt());
    }

}
