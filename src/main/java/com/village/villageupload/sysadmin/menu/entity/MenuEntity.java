package com.village.villageupload.sysadmin.menu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.village.villageupload.common.TreeNode;
import lombok.Data;

@Data
@TableName("sys_menu")
public class MenuEntity extends TreeNode {
    // id
    private String id;
    // 名称
    private String name;
    // 路径
    private String path;
    // 父id
    private String parentId;
    // 是否删除0否 1是
    private String isdel;
    // 是否可用0否 1是
    private String isenable;
    // 排序
    private String orderNum;
    // 类型
    private String type;

}
