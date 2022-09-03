package com.village.villageupload.exception;

import com.village.villageupload.exception.abs.AbstractBaseExceptionEnum;
import lombok.Getter;

@Getter
public class LoginException extends RuntimeException {
    private Integer code;

    private String message;

    public LoginException(AbstractBaseExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
