package com.village.villageupload.codeGen.controller;

import com.village.villageupload.codeGen.entity.GenTemplateConfigEntity;
import com.village.villageupload.codeGen.service.GenTemplateConfigService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genTemplateConfig")
public class GenTemplateConfigController {

    @Autowired
    private GenTemplateConfigService genTemplateConfigService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(GenTemplateConfigEntity resource){
        return BaseResponse.newSuccessResponse(genTemplateConfigService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(GenTemplateConfigEntity resource){
        return BaseResponse.newSuccessResponse(genTemplateConfigService.listAllPage(resource));
    }

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @GetMapping("/queryById")
    public BaseResponsePojo<Object> queryById(String id){
        return BaseResponse.newSuccessResponse(genTemplateConfigService.queryById(id));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody GenTemplateConfigEntity resource){
        GenTemplateConfigEntity genTemplateConfigEntity = genTemplateConfigService.insert(resource);
        return BaseResponse.newSuccessResponse(genTemplateConfigEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody GenTemplateConfigEntity resource){
        GenTemplateConfigEntity genTemplateConfigEntity = genTemplateConfigService.update(resource);
        return BaseResponse.newSuccessResponse(genTemplateConfigEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = genTemplateConfigService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
}
