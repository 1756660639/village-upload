package com.village.villageupload.codeGen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.codeGen.entity.GenDatasourceConfigEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface GenDatasourceConfigService extends IService<GenDatasourceConfigEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenDatasourceConfigEntity> listAll(GenDatasourceConfigEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<GenDatasourceConfigEntity> listAllPage(GenDatasourceConfigEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    GenDatasourceConfigEntity insert(GenDatasourceConfigEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    GenDatasourceConfigEntity update(GenDatasourceConfigEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    List listTable(String id);
}
