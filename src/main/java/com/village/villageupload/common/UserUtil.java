package com.village.villageupload.common;

import cn.dev33.satoken.stp.StpUtil;
import com.village.villageupload.cache.UserCache;
import com.village.villageupload.login.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserUtil {

    @Autowired
    private UserCache userCache;

    public static UserUtil userUtil;

    @PostConstruct
    public void init() {
        userUtil = this;
    }

    public static UserEntity getUserInfo(){
        String token = StpUtil.getTokenValue();
        UserEntity user = userUtil.userCache.get(token);
        return user;
    }
    public static String getUserId(){
        return getUserInfo().getId();
    }
}
