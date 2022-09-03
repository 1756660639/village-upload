package com.village.villageupload.sysadmin.quartz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Tst01ScheduletaskMapper extends BaseMapper<QuartzJob> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<QuartzJob> listAll(@Param("resource") QuartzJob resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<QuartzJob> listAllPage(@Param("Page") Page page, @Param("resource") QuartzJob resource);
}
