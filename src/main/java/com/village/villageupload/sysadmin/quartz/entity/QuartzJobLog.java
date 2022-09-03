package com.village.villageupload.sysadmin.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tst02_scheduletasklog")
public class QuartzJobLog {

	@TableId("TSC002_Id")
	private String id;
	@TableField("TSC002_JobName")
	private String jobName;
	@TableField("TSC002_BeanName")
	private String beanName;
	@TableField("TSC002_MethodName")
	private String methodName;
	@TableField("TSC002_Params")
	private String params;
	@TableField("TSC002_CronExpression")
	private String cronExpression;
	@TableField("TSC002_IsSuccess")
	private String isSuccess;
	@TableField("TSC002_ExceptionDetail")
	private String exceptionDetail;
	@TableField("TSC002_TIME")
	private Long time;


}
