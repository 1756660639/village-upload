package com.village.villageupload.file.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class FileUploadQueryEntity {
    /**
     * id
     */
    private String id;
    /**
     * 父id
     */
    private String parentId;
    /**
     * userId
     */
    private String userId;

}
