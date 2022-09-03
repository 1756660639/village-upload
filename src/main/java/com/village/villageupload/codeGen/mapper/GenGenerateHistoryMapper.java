package com.village.villageupload.codeGen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.codeGen.entity.GenGenerateHistoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GenGenerateHistoryMapper extends BaseMapper<GenGenerateHistoryEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenGenerateHistoryEntity> listAll(@Param("resource") GenGenerateHistoryEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<GenGenerateHistoryEntity> listAllPage(@Param("Page") Page page, @Param("resource") GenGenerateHistoryEntity resource);

    /**
     * 根据md5查询
     *
     * @param md5 md5
     * @return 返回记录，没有返回null
     */
    GenGenerateHistoryEntity getByMd5(@Param("md5") String md5);
}
