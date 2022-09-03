package com.village.villageupload.codeGen.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.codeGen.gen.GeneratorConfig;
import com.village.villageupload.codeGen.gen.SQLService;
import com.village.villageupload.codeGen.gen.SQLServiceFactory;
import com.village.villageupload.codeGen.gen.TableDefinition;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import com.village.villageupload.codeGen.mapper.GenDatasourceConfigMapper;
import com.village.villageupload.codeGen.service.GenDatasourceConfigService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenDatasourceConfigServiceImpl extends ServiceImpl<GenDatasourceConfigMapper, GenDatasourceConfigEntity> implements GenDatasourceConfigService {

    @Autowired
    private GenDatasourceConfigMapper genDatasourceConfigMapper;

    @Override
    public List<GenDatasourceConfigEntity> listAll(GenDatasourceConfigEntity resource) {
        return genDatasourceConfigMapper.listAll(resource);
    }

    @Override
    public PageResource<GenDatasourceConfigEntity> listAllPage(GenDatasourceConfigEntity resource) {
        Page<GenDatasourceConfigEntity> page = genDatasourceConfigMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public GenDatasourceConfigEntity insert(GenDatasourceConfigEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        genDatasourceConfigMapper.insert(resource);
        return resource;
    }

    @Override
    public GenDatasourceConfigEntity update(GenDatasourceConfigEntity resource){
        genDatasourceConfigMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return genDatasourceConfigMapper.deleteById(id);
    }

    @Override
    public List listTable(String id) {
        GenDatasourceConfigEntity dataSourceConfig = genDatasourceConfigMapper.selectById(id);
        GeneratorConfig generatorConfig = GeneratorConfig.build(dataSourceConfig);
        SQLService service = SQLServiceFactory.build(generatorConfig);
        List<TableDefinition> list = service.getTableSelector(generatorConfig).getSimpleTableDefinitions();
        return list;
    }
}
