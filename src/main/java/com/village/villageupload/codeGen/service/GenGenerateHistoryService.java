package com.village.villageupload.codeGen.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.codeGen.common.GeneratorParam;
import com.village.villageupload.codeGen.entity.GenGenerateHistoryEntity;
import com.village.villageupload.codeGen.entity.GenerateHistoryVO;
import com.village.villageupload.utils.PageResource;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public interface GenGenerateHistoryService extends IService<GenGenerateHistoryEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<GenerateHistoryVO> listAll(GenGenerateHistoryEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<GenGenerateHistoryEntity> listAllPage(GenGenerateHistoryEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    GenGenerateHistoryEntity insert(GenGenerateHistoryEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    GenGenerateHistoryEntity update(GenGenerateHistoryEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

    void saveHistory(GeneratorParam param);
}
