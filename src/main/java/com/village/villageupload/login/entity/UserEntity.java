package com.village.villageupload.login.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class UserEntity {
    /**
     * id
     */
    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户名
     */
    private String user_name;

    /**
     * 用户密码
     */
    private String user_pwd;

    /**
     * 是否删除
     */
    private Integer is_del;

}
