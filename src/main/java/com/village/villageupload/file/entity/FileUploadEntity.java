package com.village.villageupload.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("files")
public class FileUploadEntity {
    /**
     * id
     */
    private String id;
    /**
     * 文件原名称
     */
    private String oldName;
    /**
     * 名称
     */
    private String name;
    /**
     * url
     */
    private String url;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 是否为文件 0否 1是
     */
    private Integer isFile;
    /**
     * 后缀
     */
    private String suffix;
    /**
     * md5
     */
    private String md5;
    /**
     * 父id
     */
    private String parentId;
    /**
     * userId
     */
    private String userId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否删除 0否 1是
     */
    private Integer isDel;

}
