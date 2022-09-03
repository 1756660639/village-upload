package com.village.villageupload.process.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("flow_lcgl")
public class FlowLcglEntity {

	/** id */
	private String id;
    
	/** 名称 */
	private String name;

	/** 是否启用 0未启用 1启用 */
	private String state;
    
	/** 是否删除 */
	private String isDel;
    
	/** 流程数据 */
	private String flowData;
    
	/** 审批表单url */
	private String fromUrl;
    

}
