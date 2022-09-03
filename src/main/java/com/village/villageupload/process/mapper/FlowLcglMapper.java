package com.village.villageupload.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.process.entity.FlowLcglEntity;
import com.village.villageupload.process.entity.FlowLcjdsxEntity;
import com.village.villageupload.process.entity.FlowLcsprEntity;
import com.village.villageupload.process.entity.FlowYfqlcglEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FlowLcglMapper extends BaseMapper<FlowLcglEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<FlowLcglEntity> listAll(@Param("resource") FlowLcglEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<FlowLcglEntity> listAllPage(@Param("Page") Page page, @Param("resource") FlowLcglEntity resource);

    int updateProcessData(String id,String flowData);

    int insertLcjdsx(List<Map<String,Object>> processNoe);

    int deleteByLcglId(String lcglId);

    List<FlowLcjdsxEntity> getApproveNode(String id);

    int conditionSelect(String sql);

    int savaYfqlc(@Param("resource") FlowYfqlcglEntity resource);

    int saveLcspr(List<FlowLcsprEntity> resource);

    List<FlowLcsprEntity> getApproveNodeDetail(String id);

    List<Map<String,Object>> getApprovalPending(String userId);
}
