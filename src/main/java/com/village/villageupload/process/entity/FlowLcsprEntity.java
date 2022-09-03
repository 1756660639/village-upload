package com.village.villageupload.process.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("flow_lcspr")
public class FlowLcsprEntity {

	/** 主键ID */
	private String id;

	/** 已发起流程管理id */
	private String yfqlcId;

	/** 审批人id */
	private String approveId;

	/** 审批人名称 */
	private String approveName;

	/** 审批人部门 */
	private String approveDept;

	/** 排序 */
	private Integer sort;

	/** 审批状态 */
	private String state;

	/** 审批开始时间 */
	private Date startDate;

	/** 审批结束时间 */
	private Date endDate;

	/** 流程节点名称 */
	private String nodeName;

	/** 审批意见 */
	private String idea;


}
