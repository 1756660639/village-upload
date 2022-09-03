package com.village.villageupload.codeGen.controller;

import com.village.villageupload.codeGen.entity.TemplateEntity;
import com.village.villageupload.codeGen.service.TemplateService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(TemplateEntity resource){
        return BaseResponse.newSuccessResponse(templateService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(TemplateEntity resource){
        return BaseResponse.newSuccessResponse(templateService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody TemplateEntity resource){
        TemplateEntity templateEntity = templateService.insert(resource);
        return BaseResponse.newSuccessResponse(templateEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody TemplateEntity resource){
        TemplateEntity templateEntity = templateService.update(resource);
        return BaseResponse.newSuccessResponse(templateEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> update(@RequestBody String id){
        int num = templateService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping("/queryById")
    public BaseResponsePojo<Object> queryById(String id){
        return BaseResponse.newSuccessResponse(templateService.queryById(id));
    }

}
