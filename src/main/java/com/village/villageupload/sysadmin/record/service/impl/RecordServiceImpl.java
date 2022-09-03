package com.village.villageupload.sysadmin.record.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.sysadmin.record.entity.RecordEntity;
import com.village.villageupload.sysadmin.record.mapper.RecordMapper;
import com.village.villageupload.sysadmin.record.service.RecordService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordEntity> implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<RecordEntity> listAll(RecordEntity resource) {
        return recordMapper.listAll(resource);
    }

    @Override
    public PageResource<RecordEntity> listAllPage(RecordEntity resource) {
        Page<RecordEntity> page = recordMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public RecordEntity insert(RecordEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        recordMapper.insert(resource);
        return resource;
    }

    @Override
    public RecordEntity update(RecordEntity resource){
        recordMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return recordMapper.deleteById(id);
    }

    @Override
    public RecordEntity findById(String id) {
        return recordMapper.selectById(id);
    }
}
