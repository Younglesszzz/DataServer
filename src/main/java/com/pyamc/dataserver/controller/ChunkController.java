package com.pyamc.dataserver.controller;

import com.pyamc.dataserver.entity.Chunk;
import com.pyamc.dataserver.entity.ChunkInfo;
import com.pyamc.dataserver.entity.Result;
import com.pyamc.dataserver.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/chunk")
public class ChunkController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    StorageService storageService;
    @PostMapping("/put")
    public Result put(@RequestBody MultipartFile chunk, @RequestParam("offset") long offset) {
        logger.info("PUT CHUNK {} OFFSET {}", chunk.getName(), offset);
        return storageService.saveChunk(chunk, offset);
    }

    @PostMapping("/get")
    public void get(@RequestParam("offset") String offset, @RequestParam("chunkKey") String chunkKey, HttpServletResponse response) {
        logger.info("GET CHUNK KEY {} OFFSET {}", chunkKey, offset);
        storageService.readChunk(offset, chunkKey, response);
    }
}
