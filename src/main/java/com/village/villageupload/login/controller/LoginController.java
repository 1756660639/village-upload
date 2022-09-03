package com.village.villageupload.login.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.village.villageupload.login.entity.UserEntity;
import com.village.villageupload.login.service.LoginService;
import com.village.villageupload.utils.BaseResponse;
import com.village.villageupload.utils.BaseResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * 登陆接口
     * @param body
     * @return
     */
    @PostMapping
    public BaseResponsePojo<Object> login(@RequestBody Map<String,Object> body){
        UserEntity user = loginService.getUserByAccount(body.get("account").toString(),body.get("pwd").toString());
        //返回token的名称以及token值
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("tokenName", StpUtil.getTokenName());
        hashMap.put("tokenValue", StpUtil.getTokenValue());
        return BaseResponse.newSuccessResponse(hashMap);
    }

    /**
     * 退出登录
     */
    @GetMapping("/quitLogin")
    public void quitLogin(){
        loginService.quitLogin();
    }

    /**
     * 校验是否登录了并且token有效
     * @param
     * @return
     */
    @GetMapping("/checkLogin")
    public BaseResponsePojo<Object> checkLogin(){
        boolean b = loginService.checkLogin();
        if(b){
            return BaseResponse.newSuccessResponse();
        }else {
            return BaseResponse.newErrorResponse();
        }
    }

    @PostMapping("/record")
    public BaseResponsePojo<Object> record(@RequestBody Map<String,Object> body){
        loginService.record(body);
        return BaseResponse.newSuccessResponse();
    }

}
