package com.village.villageupload.enums;

public enum OsEnum {

    WINDOWS(1,"windows"),
    LINUX(2,"linux");

    private Integer code;

    private String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    OsEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
