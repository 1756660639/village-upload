package com.village.villageupload.codeGen.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.codeGen.common.GeneratorParam;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import com.village.villageupload.codeGen.entity.GenGenerateHistoryEntity;
import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import com.village.villageupload.codeGen.entity.GenerateHistoryVO;
import com.village.villageupload.codeGen.mapper.GenGenerateHistoryMapper;
import com.village.villageupload.codeGen.service.GenDatasourceConfigService;
import com.village.villageupload.codeGen.service.GenGenerateHistoryService;
import com.village.villageupload.codeGen.service.GenTemplateConfigService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GenGenerateHistoryServiceImpl extends ServiceImpl<GenGenerateHistoryMapper, GenGenerateHistoryEntity> implements GenGenerateHistoryService {

    @Autowired
    private GenGenerateHistoryMapper genGenerateHistoryMapper;

    @Autowired
    private GenDatasourceConfigService datasourceConfigService;

    @Autowired
    private GenTemplateConfigService templateConfigService;

    @Override
    public List<GenerateHistoryVO> listAll(GenGenerateHistoryEntity resource) {
        List<GenGenerateHistoryEntity> generateHistories = genGenerateHistoryMapper.listAll(resource);
        List<GenerateHistoryVO> generateHistoryVOS = generateHistories.stream()
                .map(generateHistory -> {
                    GenerateHistoryVO generateHistoryVO = new GenerateHistoryVO();
                    GeneratorParam generatorParam = JSON.parseObject(generateHistory.getConfigContent(), GeneratorParam.class);
                    String datasourceInfo = getDatasourceInfo(generatorParam.getDatasourceConfigId());
                    if (datasourceInfo == null) {
                        return null;
                    }
                    List<String> templateNames = this.listTemplateNames(generatorParam.getTemplateConfigIdList());
                    generateHistoryVO.setGenerateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(generateHistory.getGenerateTime()));
                    generateHistoryVO.setConfigContent(generatorParam);
                    generateHistoryVO.setDatasource(datasourceInfo);
                    generateHistoryVO.setTemplateNames(templateNames);
                    return generateHistoryVO;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return generateHistoryVOS;
    }

    private String getDatasourceInfo(String datasourceConfigId) {
        GenDatasourceConfigEntity datasourceConfig = datasourceConfigService.getById(datasourceConfigId);
        if (datasourceConfig == null) {
            return null;
        }
        String tpl = "%s（%s:%s）";
        return String.format(tpl, datasourceConfig.getDbName(), datasourceConfig.getHost(), datasourceConfig.getPort());
    }

    private List<String> listTemplateNames(List<String> idList) {
        return templateConfigService.listTemplate(idList)
                .stream()
                .map(GenTemplateConfigEntity::getName)
                .collect(Collectors.toList());
    }

    @Override
    public PageResource<GenGenerateHistoryEntity> listAllPage(GenGenerateHistoryEntity resource) {
        Page<GenGenerateHistoryEntity> page = genGenerateHistoryMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public GenGenerateHistoryEntity insert(GenGenerateHistoryEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        genGenerateHistoryMapper.insert(resource);
        return resource;
    }

    @Override
    public GenGenerateHistoryEntity update(GenGenerateHistoryEntity resource){
        genGenerateHistoryMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return genGenerateHistoryMapper.deleteById(id);
    }
    @Override
    public void saveHistory(GeneratorParam param) {
        String content = JSON.toJSONString(param);
        String md5 = DigestUtils.md5DigestAsHex(content.getBytes(StandardCharsets.UTF_8));
        GenGenerateHistoryEntity history = genGenerateHistoryMapper.getByMd5(md5);
        if (history != null) {
            history.setGenerateTime(new Date());
            genGenerateHistoryMapper.updateById(history);
            return;
        }
        GenGenerateHistoryEntity generateHistory = new GenGenerateHistoryEntity();
        generateHistory.setConfigContent(content);
        generateHistory.setMd5Value(md5);
        generateHistory.setGenerateTime(new Date());
        this.insert(generateHistory);
    }
}
