package com.village.villageupload.exception.enums;

import com.village.villageupload.exception.abs.AbstractBaseExceptionEnum;

public enum LoginEnum implements AbstractBaseExceptionEnum {

    LOGIN_ENUM_ERROR(1,"登陆失败用户不存在或存在多个!"),
    NO_LOGIN_ERROR(4001,"未获取到用户信息请重新登录");

    private Integer code;

    private String message;

    LoginEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
