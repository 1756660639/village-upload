package com.village.villageupload.sysadmin.quartz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJobLog;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface Tst02ScheduletasklogService extends IService<QuartzJobLog> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<QuartzJobLog> listAll(QuartzJobLog resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<QuartzJobLog> listAllPage(QuartzJobLog resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    QuartzJobLog insert(QuartzJobLog resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    QuartzJobLog update(QuartzJobLog resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);
}
