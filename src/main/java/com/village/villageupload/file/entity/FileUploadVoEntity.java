package com.village.villageupload.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class FileUploadVoEntity {
    /**
     * id
     */
    private String id;
    /**
     * 文件原名称
     */
    private String oldName;
    /**
     * 相对路径
     */
    private String relativePath;
    /**
     * 是否为文件 0否 1是
     */
    private Integer isFile;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 文件类型
     */
    private String fileType;

}
