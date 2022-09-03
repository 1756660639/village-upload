package com.village.villageupload.exception.enums;

import com.village.villageupload.exception.abs.AbstractBaseExceptionEnum;

public enum FileUploadEnum implements AbstractBaseExceptionEnum {

    FILE_UPLOAD_ISEMPTY(1,"文件不能为空!"),
    FILE_UPLOAD_ISERROR(2,"上传文件失败!"),
    FILE_DOWNLOAD_ERROR(3,"文件下载异常！");

    private Integer code;

    private String message;

    FileUploadEnum(Integer code, String message) {
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
