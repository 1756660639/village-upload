package com.village.villageupload.sysadmin.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tst01_scheduletask")
public class QuartzJob {

	public static final String JOB_KEY = "JOB_KEY";

	/** 主键 */
	@TableId("TSC001_Id")
	private String id;
    
	/** bean名称 */
	@TableField("TSC001_BeanName")
	private String beanName;
    
	/** 表达式 */
	@TableField("TSC001_CronExpression")
	private String cronExpression;
    
	/** 是否执行 */
	@TableField("TSC001_IsPause")
	private String isPause;
    
	/** 名称 */
	@TableField("TSC001_JobName")
	private String jobName;
    
	/** 方法名 */
	@TableField("TSC001_MethodName")
	private String methodName;
    
	/** 参数 */
	@TableField("TSC001_Params")
	private String params;
    
	/** 备注 */
	@TableField("TSC001_Remark")
	private String remark;
    

}
