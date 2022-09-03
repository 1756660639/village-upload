package com.village.villageupload.sysadmin.menu.controller;

import com.village.villageupload.sysadmin.menu.entity.MenuEntity;
import com.village.villageupload.sysadmin.menu.service.MenuService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/queryAllMenu")
    public BaseResponsePojo<Object> queryAllMenu(){
        return BaseResponse.newSuccessResponse(menuService.queryAllMenu());
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody MenuEntity resource){
        MenuEntity menuEntity = menuService.insert(resource);
        return BaseResponse.newSuccessResponse(resource);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody MenuEntity resource){
        MenuEntity menuEntity = menuService.update(resource);
        return BaseResponse.newSuccessResponse(resource);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = menuService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
    
}
