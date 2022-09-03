package com.village.villageupload.utils;

import com.village.villageupload.enums.BaseResponseEnum;

/**
 * @author Liuxx
 */
public class BaseResponse {

    /**
     * 统一返回数据
     * @param code
     * @param message
     * @param isSuccess
     * @param data
     * @param <T>
     * @return
     */
    private static <T> BaseResponsePojo<T> newResponse(Integer code, String message,boolean isSuccess ,T data) {
        BaseResponsePojo<T> res = new BaseResponsePojo<>();
        res.setCode(code);
        res.setMessage(message);
        res.setSuccess(isSuccess);
        res.setData(data);
        return res;
    }

    /**
     * 自定义返回成功信息
     * @param code 自定义编码
     * @param message 自定义提示信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newSuccessResponse(Integer code, String message,T data){
        return newResponse(code,message,true,data);
    }

    /**
     * 统一返回成功信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newSuccessResponse(T data){
        return newResponse(BaseResponseEnum.SUCCESS.getCode(),BaseResponseEnum.SUCCESS.getMessage(),true,data);
    }

    /**
     * 统一返回成功信息
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newSuccessResponse(){
        return newResponse(BaseResponseEnum.SUCCESS.getCode(),BaseResponseEnum.SUCCESS.getMessage(),true,null);
    }

    /**
     * 自定义返回错误信息
     * @param code 自定义编码
     * @param message 自定义提示信息
     * @param data 数据
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newErrorResponse(Integer code, String message,T data){
        return newResponse(code,message,false,data);
    }

    /**
     * 统一返回错误信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newErrorResponse(T data){
        return newResponse(BaseResponseEnum.ERROR.getCode(),BaseResponseEnum.ERROR.getMessage(),false,data);
    }

    /**
     * 统一返回错误信息
     * @param <T>
     * @return
     */
    public static <T> BaseResponsePojo<T> newErrorResponse(){
        return newResponse(BaseResponseEnum.ERROR.getCode(),BaseResponseEnum.ERROR.getMessage(),false,null);
    }

}
