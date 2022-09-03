package com.village.villageupload.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.process.entity.FlowLcglEntity;
import com.village.villageupload.process.entity.FlowLcsprEntity;
import com.village.villageupload.process.entity.FlowYfqlcglEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;
import java.util.Map;

public interface FlowLcglService extends IService<FlowLcglEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<FlowLcglEntity> listAll(FlowLcglEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<FlowLcglEntity> listAllPage(FlowLcglEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    FlowLcglEntity insert(FlowLcglEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    FlowLcglEntity update(FlowLcglEntity resource);
    void updateProcessData(FlowLcglEntity resource);
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
    FlowLcglEntity findById(String id);

    List<FlowLcsprEntity> getApproveNode(String lcid,String dataId);

    void saveApproveUser(FlowYfqlcglEntity resource);

    List<FlowLcsprEntity> getApproveNodeDetail(String lcid,String dataId);

    List<Map<String,Object>> getApprovalPending();
}
