package com.village.villageupload.codeGen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gen_datasource_config")
public class GenDatasourceConfigEntity {

	private String id;
    
	/** 数据库类型，1：MySql, 2:Oracle, 3:sqlserver */
	private Integer dbType;
    
	/** 数据库驱动 */
	private String driverClass;
    
	/** 数据库名称 */
	private String dbName;
    
	/** 数据库host */
	private String host;
    
	/** 数据库端口 */
	private Integer port;
    
	/** 数据库用户名 */
	private String username;
    
	/** 数据库密码 */
	private String password;
    
	/** 是否已删除，1：已删除，0：未删除 */
	private Integer isDeleted;
    
	private String packageName;
    
	private String delPrefix;
    
	private Integer groupId;
    
	private String schemaName;
    
	private String author;
    

}
