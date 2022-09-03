package com.village.villageupload.codeGen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GenDatasourceConfigMapper extends BaseMapper<GenDatasourceConfigEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenDatasourceConfigEntity> listAll(@Param("resource") GenDatasourceConfigEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<GenDatasourceConfigEntity> listAllPage(@Param("Page") Page page, @Param("resource") GenDatasourceConfigEntity resource);
}
