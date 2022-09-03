package com.village.villageupload.sysadmin.record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.sysadmin.record.entity.RecordEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper extends BaseMapper<RecordEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<RecordEntity> listAll(@Param("resource") RecordEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<RecordEntity> listAllPage(@Param("Page") Page page, @Param("resource") RecordEntity resource);
}
