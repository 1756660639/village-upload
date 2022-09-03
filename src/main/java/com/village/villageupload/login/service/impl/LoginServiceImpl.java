package com.village.villageupload.login.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.cache.UserCache;
import com.village.villageupload.exception.FileUploadException;
import com.village.villageupload.exception.LoginException;
import com.village.villageupload.exception.enums.FileUploadEnum;
import com.village.villageupload.exception.enums.LoginEnum;
import com.village.villageupload.login.entity.UserEntity;
import com.village.villageupload.login.mapper.LoginMapper;
import com.village.villageupload.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.village.villageupload.constant.Constant.ONE;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, UserEntity> implements LoginService {

    @Resource
    private LoginMapper loginMapper;

    @Resource
    private UserCache userCache;

    @Override
    public UserEntity getUserByAccount(String account,String pwd) {
        Map map = new HashMap();
        map.put("account",account);
        map.put("user_pwd",pwd);
        List<UserEntity> userList = loginMapper.selectByMap(map);
        // 多条数据和无数据不进行继续登陆
        if (userList.size() != ONE){
            throw new LoginException(LoginEnum.LOGIN_ENUM_ERROR);
        }
        StpUtil.login(userList.get(0).getId());
        // 获取当前`StpLogic`的token名称
        StpUtil.getTokenName();

        // 获取当前会话的token值
        StpUtil.getTokenValue();

        // 获取当前会话的token信息参数
        StpUtil.getTokenInfo();
        userCache.put(StpUtil.getTokenValue(),userList.get(0));
        return userList.get(0);
    }

    @Override
    public void quitLogin() {
        userCache.remove(StpUtil.getTokenValue());
    }

    @Override
    public boolean checkLogin() {
        UserEntity user = userCache.get(StpUtil.getTokenValue());
        return ObjectUtil.isNotEmpty(ObjectUtil.isNotEmpty(user)?user.getId():null)?true:false;
    }

    @Override
    public void record(Map<String, Object> body) {
        loginMapper.insertRecord(body);
    }
}
