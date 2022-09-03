package com.village.villageupload.exception;

import com.village.villageupload.exception.abs.AbstractBaseExceptionEnum;
import com.village.villageupload.exception.enums.FileUploadEnum;
import lombok.Getter;

@Getter
public class FileUploadException extends RuntimeException{

    private Integer code;

    private String message;

    public FileUploadException(AbstractBaseExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMessage());
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }

}
