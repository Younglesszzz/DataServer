package com.pyamc.dataserver.entity;

public class Result {
    public String code;
    public String message;
    public byte[] data;

    public Result(String code, String message, byte[] data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result Success(byte[] data) {
        return new Result("0", "success", data);
    }

    public static Result Fail(byte[] data) {
        return new Result("500", "Fail", data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
