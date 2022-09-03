package com.village.villageupload.codeGen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GenTemplateConfigMapper extends BaseMapper<GenTemplateConfigEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenTemplateConfigEntity> listAll(@Param("resource") GenTemplateConfigEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<GenTemplateConfigEntity> listAllPage(@Param("Page") Page page, @Param("resource") GenTemplateConfigEntity resource);

    List<GenTemplateConfigEntity> listTemplate(@Param("idList") List<String> idList);
}
