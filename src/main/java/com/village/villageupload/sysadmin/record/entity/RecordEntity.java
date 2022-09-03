package com.village.villageupload.sysadmin.record.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.math.BigDecimal;

@Data
@TableName("record")
public class RecordEntity {

	/** id */
	private String id;
    
	/** 金额 */
	private BigDecimal amountOfMoney;
    
	/** 消费来源 */
	private String source;
    
	/** 消费类型 */
	private String type;
    
	/** 创建时间 */
	private Date createTime;
    
	/** 备注 */
	private String remark;

	/** 审批状态 */
	private String approve;
    

}
