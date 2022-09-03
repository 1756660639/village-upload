package com.village.villageupload.sysadmin.record.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.sysadmin.record.entity.RecordEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;

public interface RecordService extends IService<RecordEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<RecordEntity> listAll(RecordEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<RecordEntity> listAllPage(RecordEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    RecordEntity insert(RecordEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    RecordEntity update(RecordEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    RecordEntity findById(String id);
}
