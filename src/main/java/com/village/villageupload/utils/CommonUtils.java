package com.village.villageupload.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

import static com.village.villageupload.constant.Constant.INDEX_OF_INEXISTENCE;

/**
 * 公共工具类
 */
public class CommonUtils {
    /**
     * 获取当前的操作系统类型
     * @return
     */
    public static String getOsName(){
        Properties props = System.getProperties();
//        System.out.println("操作系统的名称：" + props.getProperty("os.name"));
        return props.getProperty("os.name").toLowerCase();
    }
    /**
     * 去除路径中的//为/
     */
    public static String removeUrl(String url){
        url = url.replaceAll("//","/");
        if (INDEX_OF_INEXISTENCE != url.indexOf("//")){
            url = removeUrl(url);
        }
        return url;
    }

    /**
     * 获取当前请求的request对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        } else {
            return requestAttributes.getRequest();
        }
    }

    /**
     * 获取当前请求的response对象
     */
    public static HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        } else {
            return requestAttributes.getResponse();
        }
    }

}
