package com.village.villageupload.sysadmin.quartz.controller;

import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import com.village.villageupload.sysadmin.quartz.service.Tst01ScheduletaskService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scheduletask")
public class Tst01ScheduletaskController {

    @Autowired
    private Tst01ScheduletaskService tst01ScheduletaskService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(QuartzJob resource){
        return BaseResponse.newSuccessResponse(tst01ScheduletaskService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(QuartzJob resource){
        return BaseResponse.newSuccessResponse(tst01ScheduletaskService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody QuartzJob resource){
        QuartzJob quartzJob = tst01ScheduletaskService.insert(resource);
        return BaseResponse.newSuccessResponse(quartzJob);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody QuartzJob resource){
        QuartzJob quartzJob = tst01ScheduletaskService.update(resource);
        return BaseResponse.newSuccessResponse(quartzJob);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = tst01ScheduletaskService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 立即执行
     * @param id
     * @return
     */
    @GetMapping("/execution")
    public BaseResponsePojo<Object> execution(String id) {
        tst01ScheduletaskService.execution(id);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 更改执行状态
     * @param id
     * @return
     */
    @GetMapping("/updateIsPause")
    public BaseResponsePojo<Object> updateIsPause(String id) {
        tst01ScheduletaskService.updateIsPause(id);
        return BaseResponse.newSuccessResponse();
    }
    @GetMapping("/findById")
    public BaseResponsePojo<Object> findById(String id){
        return BaseResponse.newSuccessResponse(tst01ScheduletaskService.getById(id));
    }
}
