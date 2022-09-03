package com.village.villageupload.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.file.entity.FileUploadEntity;
import com.village.villageupload.file.entity.FileUploadQueryEntity;
import com.village.villageupload.file.entity.FileUploadVoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileUploadMapper extends BaseMapper<FileUploadEntity> {

    Page<FileUploadVoEntity> queryFileListPage(@Param("Page") Page page, @Param("resource") FileUploadQueryEntity resource);

    List<FileUploadVoEntity> queryFileList(@Param("resource") FileUploadQueryEntity resource);

    Integer rename(@Param("resource") FileUploadEntity resource);

    Integer delFile(@Param("id") String id);

    Integer delFileBatch(@Param("id") String id);
}
