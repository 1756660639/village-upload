package com.village.villageupload.codeGen.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.codeGen.entity.TemplateEntity;
import com.village.villageupload.codeGen.mapper.TemplateMapper;
import com.village.villageupload.codeGen.service.TemplateService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, TemplateEntity> implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public List<TemplateEntity> listAll(TemplateEntity resource) {
        return templateMapper.listAll(resource);
    }

    @Override
    public PageResource<TemplateEntity> listAllPage(TemplateEntity resource) {
        Page<TemplateEntity> page = templateMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public TemplateEntity insert(TemplateEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        resource.setIsDeleted(0);
        templateMapper.insert(resource);
        return resource;
    }

    @Override
    public TemplateEntity update(TemplateEntity resource){
        templateMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return templateMapper.deleteById(id);
    }

    @Override
    public TemplateEntity queryById(String id) {
        return templateMapper.selectById(id);
    }
}
