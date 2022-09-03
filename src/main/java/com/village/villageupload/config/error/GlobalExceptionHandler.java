package com.village.villageupload.config.error;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.log.Log;
import com.village.villageupload.exception.LoginException;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log log = Log.get();


    /**
     * 请求参数缺失异常
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseResponsePojo<Object> missParamException(MissingServletRequestParameterException e) {
        log.error(">>> 请求参数异常，具体信息为：{}", e.getMessage());
        String parameterType = e.getParameterType();
        String parameterName = e.getParameterName();
        String message = StrUtil.format(">>> 缺少请求的参数{}，类型为{}", parameterName, parameterType);
        return BaseResponse.newErrorResponse(message);
    }

    /**
     * 拦截参数格式传递异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public BaseResponsePojo<Object> httpMessageNotReadable(HttpMessageNotReadableException e) {
        log.error(">>> 参数格式传递异常，具体信息为：{}", e.getMessage());
        return BaseResponse.newErrorResponse((e.getMessage()));
    }

    /**
     * 拦截不支持媒体类型异常
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public BaseResponsePojo<Object> httpMediaTypeNotSupport(HttpMediaTypeNotSupportedException e) {
        log.error(">>> 参数格式传递异常，具体信息为：{}", e.getMessage());
        return BaseResponse.newErrorResponse(e.getMessage());
    }

    /**
     * 拦截请求方法的异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public BaseResponsePojo<Object> methodNotSupport(HttpServletRequest request) {
        if (ServletUtil.isPostMethod(request)) {
            log.error(">>> 请求方法异常，具体信息为：{}", "不支持该请求方法，请求方法应为GET");
            return BaseResponse.newErrorResponse("不支持该请求方法，请求方法应为GET");
        }
        if (ServletUtil.isGetMethod(request)) {
            log.error(">>> 请求方法异常，具体信息为：{}", "不支持该请求方法，请求方法应为GET");
            return BaseResponse.newErrorResponse("不支持该请求方法，请求方法应为GET");
        }
        return null;
    }

    /**
     * 拦截参数校验错误异常,JSON传参
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponsePojo<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，具体信息为：{}", argNotValidMessage);
        return BaseResponse.newErrorResponse(argNotValidMessage);
    }

    /**
     * 拦截参数校验错误异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public BaseResponsePojo<Object> paramError(BindException e) {
        String argNotValidMessage = getArgNotValidMessage(e.getBindingResult());
        log.error(">>> 参数校验错误异常，具体信息为：{}", argNotValidMessage);
        return BaseResponse.newErrorResponse(argNotValidMessage);
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponsePojo<Object> serverError(Throwable e) {
        log.error(">>> 服务器运行异常");
        e.printStackTrace();
        return BaseResponse.newErrorResponse(e);
    }
    /**
     * 拦截runtime运行异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public BaseResponsePojo<Object> runTimeExceptionError(Throwable e) {
        log.error(">>> 服务器运行异常");
        if(e instanceof LoginException){
            return BaseResponse.newErrorResponse(((LoginException) e).getCode(),e.getMessage(),null);
        }
        e.printStackTrace();
        return BaseResponse.newErrorResponse(e.getMessage());
    }

    /**
     * 获取请求参数不正确的提示信息
     * <p>
     * 多个信息，拼接成用逗号分隔的形式
     */
    private String getArgNotValidMessage(BindingResult bindingResult) {
        if (bindingResult == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        //多个错误用逗号分隔
        List<ObjectError> allErrorInfos = bindingResult.getAllErrors();
        for (ObjectError error : allErrorInfos) {
            stringBuilder.append(",").append(error.getDefaultMessage());
        }

        //最终把首部的逗号去掉
        return StrUtil.removePrefix(stringBuilder.toString(), ",");
    }

}
