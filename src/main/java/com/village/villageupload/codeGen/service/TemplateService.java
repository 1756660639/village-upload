package com.village.villageupload.codeGen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.codeGen.entity.TemplateEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface TemplateService extends IService<TemplateEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<TemplateEntity> listAll(TemplateEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<TemplateEntity> listAllPage(TemplateEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    TemplateEntity insert(TemplateEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    TemplateEntity update(TemplateEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据id查询查询所有数据
     * @param id
     * @return
     */
    TemplateEntity queryById(String id);
}
