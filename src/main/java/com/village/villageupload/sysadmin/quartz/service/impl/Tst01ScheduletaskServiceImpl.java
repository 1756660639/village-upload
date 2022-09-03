package com.village.villageupload.sysadmin.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.sysadmin.quartz.core.utils.QuartzManage;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import com.village.villageupload.sysadmin.quartz.mapper.Tst01ScheduletaskMapper;
import com.village.villageupload.sysadmin.quartz.service.Tst01ScheduletaskService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Tst01ScheduletaskServiceImpl extends ServiceImpl<Tst01ScheduletaskMapper, QuartzJob> implements Tst01ScheduletaskService {

    @Autowired
    private Tst01ScheduletaskMapper tst01ScheduletaskMapper;

    @Autowired
    private QuartzManage quartzManage;

    @Override
    public List<QuartzJob> listAll(QuartzJob resource) {
        return tst01ScheduletaskMapper.listAll(resource);
    }

    @Override
    public PageResource<QuartzJob> listAllPage(QuartzJob resource) {
        Page<QuartzJob> page = tst01ScheduletaskMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public QuartzJob insert(QuartzJob resource) {
        resource.setId(UuidUtils.uuid(true));
        tst01ScheduletaskMapper.insert(resource);
        quartzManage.addJob(resource);
        return resource;
    }

    @Override
    public QuartzJob update(QuartzJob resource){
        tst01ScheduletaskMapper.updateById(resource);
        quartzManage.updateJobCron(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        QuartzJob quartzJob = tst01ScheduletaskMapper.selectById(id);
        quartzManage.deleteJob(quartzJob);
        return tst01ScheduletaskMapper.deleteById(id);
    }

    @Override
    public void execution(String id) {
        QuartzJob quartzJob = tst01ScheduletaskMapper.selectById(id);
        quartzManage.runJobNow(quartzJob);
    }

    @Override
    public void updateIsPause(String id) {
        QuartzJob quartzJob = tst01ScheduletaskMapper.selectById(id);
        if ("1".equals(quartzJob.getIsPause())) {
            quartzManage.pauseJob(quartzJob);
            quartzJob.setIsPause("0");
        } else {
            quartzManage.resumeJob(quartzJob);
            quartzJob.setIsPause("1");
        }

        tst01ScheduletaskMapper.updateById(quartzJob);
    }
}
