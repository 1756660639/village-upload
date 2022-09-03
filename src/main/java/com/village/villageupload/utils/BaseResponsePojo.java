package com.village.villageupload.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponsePojo<T> implements Serializable {

    private static final long serialVersionUID = 1194901656259089600L;
    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String message;

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 数据
     */
    private T data;

}
