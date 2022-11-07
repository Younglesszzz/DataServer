package com.pyamc.dataserver.entity;

public class ChunkInfo {
    private String offset;
    private byte[] buffer;

    public ChunkInfo(String offset, byte[] buffer) {
        this.offset = offset;
        this.buffer = buffer;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }
}
