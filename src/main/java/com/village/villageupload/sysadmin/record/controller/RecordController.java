package com.village.villageupload.sysadmin.record.controller;

import com.village.villageupload.sysadmin.record.entity.RecordEntity;
import com.village.villageupload.sysadmin.record.service.RecordService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(RecordEntity resource){
        return BaseResponse.newSuccessResponse(recordService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(RecordEntity resource){
        return BaseResponse.newSuccessResponse(recordService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody RecordEntity resource){
        RecordEntity recordEntity = recordService.insert(resource);
        return BaseResponse.newSuccessResponse(recordEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody RecordEntity resource){
        RecordEntity recordEntity = recordService.update(resource);
        return BaseResponse.newSuccessResponse(recordEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = recordService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public BaseResponsePojo<Object> findById(String id) {
        return BaseResponse.newSuccessResponse(recordService.findById(id));
    }
}
