package com.village.villageupload.process.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@TableName("flow_yfqlcgl")
public class FlowYfqlcglEntity {

    /** 主键ID */
    private String id;

    /** 流程管理id */
    private String lcglId;

    /** 业务数据id */
    private String dataId;

    /** 审批状态 */
    private String state;

    /** 创建人 */
    private String createUser;

    /** 创建时间 */
    private Date createTime;

    /** 修改人 */
    private String updateUser;

    /** 修改时间 */
    private Date updateTime;

    /** 审批人数据 */
    @TableField(exist = false)
    private List<FlowLcsprEntity> flowLcsprEntities;

}
