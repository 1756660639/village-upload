package com.village.villageupload.codeGen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gen_template_group")
public class TemplateEntity {

    /** id */
    private String id;

    /** 分组名称 */
    private String groupName;

    /** 是否删除 */
    private Integer isDeleted;

}
