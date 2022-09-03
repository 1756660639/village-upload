package com.village.villageupload.codeGen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.codeGen.entity.TemplateEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateMapper extends BaseMapper<TemplateEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<TemplateEntity> listAll(@Param("resource") TemplateEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<TemplateEntity> listAllPage(@Param("Page") Page page, @Param("resource") TemplateEntity resource);
}
