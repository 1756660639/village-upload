package com.village.villageupload.enums;


/**
 * @author Liuxx
 */


public enum BaseResponseEnum {
    SUCCESS(200,"请求成功!"),
    ERROR(400,"请求失败!");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    BaseResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
