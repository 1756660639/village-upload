package com.village.villageupload.sysadmin.user.controller;

import com.village.villageupload.sysadmin.user.entity.SysUserEntity;
import com.village.villageupload.sysadmin.user.service.SysUserService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAll")
    public BaseResponsePojo<Object> listAll(SysUserEntity resource){
        return BaseResponse.newSuccessResponse(sysUserService.listAll(resource));
    }

    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    @GetMapping("/listAllPage")
    public BaseResponsePojo<Object> listAllPage(SysUserEntity resource){
        return BaseResponse.newSuccessResponse(sysUserService.listAllPage(resource));
    }

    /**
     * 新增数据
     * @param resource
     * @return
     */
    @PostMapping("/insert")
    public BaseResponsePojo<Object> insert(@RequestBody SysUserEntity resource){
        SysUserEntity sysUserEntity = sysUserService.insert(resource);
        return BaseResponse.newSuccessResponse(sysUserEntity);
    }

    /**
     * 修改数据
     * @param resource
     * @return
     */
    @PostMapping("/update")
    public BaseResponsePojo<Object> update(@RequestBody SysUserEntity resource){
        SysUserEntity sysUserEntity = sysUserService.update(resource);
        return BaseResponse.newSuccessResponse(sysUserEntity);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public BaseResponsePojo<Object> deleteById(@RequestBody String id){
        int num = sysUserService.deleteById(id);
        return BaseResponse.newSuccessResponse();
    }
    
    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public BaseResponsePojo<Object> findById(String id) {
        return BaseResponse.newSuccessResponse(sysUserService.findById(id));
    }

    @GetMapping("/listAllByUserNameAndDept")
    public BaseResponsePojo<Object> listAllByUserNameAndDept(SysUserEntity resource){
        return BaseResponse.newSuccessResponse(sysUserService.listAllByUserNameAndDept(resource));
    }
}
