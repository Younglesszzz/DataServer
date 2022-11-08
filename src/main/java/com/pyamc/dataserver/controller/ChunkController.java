package com.pyamc.dataserver.controller;

import com.pyamc.dataserver.entity.Chunk;
import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.service.StorageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chunk")
public class ChunkController {
    @Resource
    StorageService storageService;

    @PostMapping("/put")
    public Result put(@RequestBody ChunkInfo chunk) {
        return storageService.saveChunk(chunk);
    }

    @PostMapping("/get")
    public Result get(@RequestParam ChunkInfo chunk) {
        return storageService.readChunk(chunk.getOffset());
    }
}
