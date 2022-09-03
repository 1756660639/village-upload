package com.village.villageupload.codeGen.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import com.village.villageupload.codeGen.mapper.GenTemplateConfigMapper;
import com.village.villageupload.codeGen.service.GenTemplateConfigService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class GenTemplateConfigServiceImpl extends ServiceImpl<GenTemplateConfigMapper, GenTemplateConfigEntity> implements GenTemplateConfigService {

    @Autowired
    private GenTemplateConfigMapper genTemplateConfigMapper;

    @Override
    public List<GenTemplateConfigEntity> listAll(GenTemplateConfigEntity resource) {
        return genTemplateConfigMapper.listAll(resource);
    }

    @Override
    public PageResource<GenTemplateConfigEntity> listAllPage(GenTemplateConfigEntity resource) {
        Page<GenTemplateConfigEntity> page = genTemplateConfigMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public GenTemplateConfigEntity insert(GenTemplateConfigEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        genTemplateConfigMapper.insert(resource);
        return resource;
    }

    @Override
    public GenTemplateConfigEntity update(GenTemplateConfigEntity resource){
        genTemplateConfigMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return genTemplateConfigMapper.deleteById(id);
    }

    @Override
    public GenTemplateConfigEntity queryById(String id) {
        return genTemplateConfigMapper.selectById(id);
    }

    @Override
    public List<GenTemplateConfigEntity> listTemplate(List<String> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return genTemplateConfigMapper.listTemplate(idList);
    }
}
