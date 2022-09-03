package com.village.villageupload.sysadmin.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJobLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Tst02ScheduletasklogMapper extends BaseMapper<QuartzJobLog> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<QuartzJobLog> listAll(@Param("resource") QuartzJobLog resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<QuartzJobLog> listAllPage(@Param("Page") Page page, @Param("resource") QuartzJobLog resource);
}
