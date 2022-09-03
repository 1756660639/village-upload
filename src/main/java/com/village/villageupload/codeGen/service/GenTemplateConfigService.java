package com.village.villageupload.codeGen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface GenTemplateConfigService extends IService<GenTemplateConfigEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenTemplateConfigEntity> listAll(GenTemplateConfigEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<GenTemplateConfigEntity> listAllPage(GenTemplateConfigEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    GenTemplateConfigEntity insert(GenTemplateConfigEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    GenTemplateConfigEntity update(GenTemplateConfigEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据id查询查询所有数据
     * @param resource
     * @return
     */
    GenTemplateConfigEntity queryById(String resource);

    List<GenTemplateConfigEntity> listTemplate(List<String> idList);
}
