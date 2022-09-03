package com.village.villageupload.codeGen.controller;

import com.village.villageupload.codeGen.entity.GenGenerateHistoryEntity;
import com.village.villageupload.codeGen.service.GenGenerateHistoryService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genGenerateHistory")
public class GenGenerateHistoryController {

    @Autowired
    private GenGenerateHistoryService genGenerateHistoryService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(GenGenerateHistoryEntity resource){
        return BaseResponse.newSuccessResponse(genGenerateHistoryService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(GenGenerateHistoryEntity resource){
        return BaseResponse.newSuccessResponse(genGenerateHistoryService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody GenGenerateHistoryEntity resource){
        GenGenerateHistoryEntity genGenerateHistoryEntity = genGenerateHistoryService.insert(resource);
        return BaseResponse.newSuccessResponse(genGenerateHistoryEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody GenGenerateHistoryEntity resource){
        GenGenerateHistoryEntity genGenerateHistoryEntity = genGenerateHistoryService.update(resource);
        return BaseResponse.newSuccessResponse(genGenerateHistoryEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = genGenerateHistoryService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
}
