package com.village.villageupload.sysadmin.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface Tst01ScheduletaskService extends IService<QuartzJob> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<QuartzJob> listAll(QuartzJob resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<QuartzJob> listAllPage(QuartzJob resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    QuartzJob insert(QuartzJob resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    QuartzJob update(QuartzJob resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    void execution(String id);

    void updateIsPause(String id);
}
