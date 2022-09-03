package com.village.villageupload.sysadmin.quartz.controller;

import com.village.villageupload.sysadmin.quartz.entity.QuartzJobLog;
import com.village.villageupload.sysadmin.quartz.service.Tst02ScheduletasklogService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tst02Scheduletasklog")
public class Tst02ScheduletasklogController {

    @Autowired
    private Tst02ScheduletasklogService tst02ScheduletasklogService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(QuartzJobLog resource){
        return BaseResponse.newSuccessResponse(tst02ScheduletasklogService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(QuartzJobLog resource){
        return BaseResponse.newSuccessResponse(tst02ScheduletasklogService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody QuartzJobLog resource){
        QuartzJobLog tst02ScheduletasklogEntity = tst02ScheduletasklogService.insert(resource);
        return BaseResponse.newSuccessResponse(tst02ScheduletasklogEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody QuartzJobLog resource){
        QuartzJobLog tst02ScheduletasklogEntity = tst02ScheduletasklogService.update(resource);
        return BaseResponse.newSuccessResponse(tst02ScheduletasklogEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = tst02ScheduletasklogService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
}
