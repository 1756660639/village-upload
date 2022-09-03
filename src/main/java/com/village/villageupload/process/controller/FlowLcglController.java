package com.village.villageupload.process.controller;

import com.village.villageupload.process.entity.FlowLcglEntity;
import com.village.villageupload.process.entity.FlowYfqlcglEntity;
import com.village.villageupload.process.service.FlowLcglService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flowLcgl")
public class FlowLcglController {

    @Autowired
    private FlowLcglService flowLcglService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(FlowLcglEntity resource){
        return BaseResponse.newSuccessResponse(flowLcglService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(FlowLcglEntity resource){
        return BaseResponse.newSuccessResponse(flowLcglService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody FlowLcglEntity resource){
        FlowLcglEntity flowLcglEntity = flowLcglService.insert(resource);
        return BaseResponse.newSuccessResponse(flowLcglEntity);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public BaseResponsePojo<Object> findById(String id){
        FlowLcglEntity flowLcglEntity = flowLcglService.findById(id);
        return BaseResponse.newSuccessResponse(flowLcglEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody FlowLcglEntity resource){
        FlowLcglEntity flowLcglEntity = flowLcglService.update(resource);
        return BaseResponse.newSuccessResponse(flowLcglEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = flowLcglService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
    /**
     * 修改流程数据
     * @param resource
     * @return
     */
    @PostMapping("/updateProcessData")
    public BaseResponsePojo<Object> updateProcessData(@RequestBody FlowLcglEntity resource){
        flowLcglService.updateProcessData(resource);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 获取流程节点
     * @param lcid
     * @param dataId
     * @return
     */
    @GetMapping("/getApproveNode")
    public BaseResponsePojo<Object> getApproveNode(@RequestParam("lcid") String lcid ,@RequestParam("dataId")String dataId){
        return BaseResponse.newSuccessResponse(flowLcglService.getApproveNode(lcid,dataId));
    }

    /**
     * 保存设置的用户
     * @param resource
     * @return
     */
    @PostMapping("/saveApproveUser")
    public BaseResponsePojo<Object> saveApproveUser(@RequestBody FlowYfqlcglEntity resource){
        flowLcglService.saveApproveUser(resource);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 查询设置审批人详情
     * @param lcid
     * @param dataId
     * @return
     */
    @GetMapping("/getApproveNodeDetail")
    public BaseResponsePojo<Object> getApproveNodeDetail(@RequestParam("lcid") String lcid ,@RequestParam("dataId")String dataId){
        return BaseResponse.newSuccessResponse(flowLcglService.getApproveNodeDetail(lcid,dataId));
    }

    /**
     * 待审批台账
     */
    @GetMapping("/getApprovalPending")
    public BaseResponsePojo<Object> getApprovalPending(){
        return BaseResponse.newSuccessResponse(flowLcglService.getApprovalPending());
    }
}
