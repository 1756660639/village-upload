package com.village.villageupload.codeGen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gen_template_config")
public class GenTemplateConfigEntity {

	private String id;
    
	/** 模板名称 */
	private String name;
    
	/** 目录名称 */
	private String folder;
    
	/** 文件名称 */
	private String fileName;
    
	/** 内容 */
	private String content;
    
	/** 是否删除，1：已删除，0：未删除 */
	private Integer isDeleted;
    
	private String groupId;
    
	private String groupName;
    

}
