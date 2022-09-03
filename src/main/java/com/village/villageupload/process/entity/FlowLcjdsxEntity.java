package com.village.villageupload.process.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("flow_lcjdsx")
public class FlowLcjdsxEntity {

    /** 主键ID */
    private String id;

    /** 流程ID */
    private String lcglId;

    /** 步骤名称 */
    private String name;

    /** 步骤类型 */
    private String type;

    /** 转交下一步骤号 */
    private String to;

    /** 后置动作类型（SQL 或者 调用服务 等）1: SQL， 2: 调用服务 */
    private Integer hzdzlx;

    /** 后置动作内容（具体SQL 或者 服务名称路径等） */
    private String hzdznr;

    /** 编号 */
    private String number;

    /** 条件内容SQL */
    private String tjfznrSql;

}
