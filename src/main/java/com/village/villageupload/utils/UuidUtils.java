package com.village.villageupload.utils;

import java.util.UUID;

/**
 * UUID工具
 *
 * @author Liuxx
 * @date 2021-7-26
 */
public class UuidUtils {

    /**
     * delHorizontal 是否删除横线  true 删除，false不删除
     *
     * @return
     */
    public static String uuid(boolean delHorizontal){
        if (delHorizontal){
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
        else {
            return UUID.randomUUID().toString();
        }
    }
}
