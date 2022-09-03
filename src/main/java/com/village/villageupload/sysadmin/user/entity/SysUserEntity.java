package com.village.villageupload.sysadmin.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_user")
public class SysUserEntity {

	/** id */
	private String id;
    
	/** 账号 */
	private String account;
    
	/** 用户名 */
	private String userName;
    
	/** 用户密码 */
	private String userPwd;
    
	/** 是否删除 */
	private Short isDel;
    

}
