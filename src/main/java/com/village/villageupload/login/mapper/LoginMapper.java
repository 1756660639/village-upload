package com.village.villageupload.login.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.village.villageupload.login.entity.UserEntity;

import java.util.Map;

public interface LoginMapper extends BaseMapper<UserEntity> {
    int insertRecord(Map<String, Object> body);
}
