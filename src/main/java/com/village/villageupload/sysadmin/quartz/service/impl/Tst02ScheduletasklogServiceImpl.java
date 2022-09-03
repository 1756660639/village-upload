package com.village.villageupload.sysadmin.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJobLog;
import com.village.villageupload.sysadmin.quartz.mapper.Tst02ScheduletasklogMapper;
import com.village.villageupload.sysadmin.quartz.service.Tst02ScheduletasklogService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Tst02ScheduletasklogServiceImpl extends ServiceImpl<Tst02ScheduletasklogMapper, QuartzJobLog> implements Tst02ScheduletasklogService {

    @Autowired
    private Tst02ScheduletasklogMapper scheduletasklogMapper;

    @Override
    public List<QuartzJobLog> listAll(QuartzJobLog resource) {
        return scheduletasklogMapper.listAll(resource);
    }

    @Override
    public PageResource<QuartzJobLog> listAllPage(QuartzJobLog resource) {
        Page<QuartzJobLog> page = scheduletasklogMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public QuartzJobLog insert(QuartzJobLog resource) {
        resource.setId(UuidUtils.uuid(true));
        scheduletasklogMapper.insert(resource);
        return resource;
    }

    @Override
    public QuartzJobLog update(QuartzJobLog resource){
        scheduletasklogMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return scheduletasklogMapper.deleteById(id);
    }
}
