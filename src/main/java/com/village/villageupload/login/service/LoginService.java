package com.village.villageupload.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.login.entity.UserEntity;

import java.util.Map;

public interface LoginService extends IService<UserEntity> {

    UserEntity getUserByAccount(String account,String pwd);

    void quitLogin();

    boolean checkLogin();

    void record(Map<String,Object> body);
}
