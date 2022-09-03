package com.village.villageupload.cache;

import com.village.villageupload.cache.base.AbstractRedisCacheOperator;
import com.village.villageupload.login.entity.UserEntity;
import org.springframework.data.redis.core.RedisTemplate;

public class UserCache extends AbstractRedisCacheOperator<UserEntity> {
    /**
     * 登录用户缓存前缀
     */
    public static final String LOGIN_USER_CACHE_PREFIX = "LOGIN_USER_";

    public UserCache(RedisTemplate<String, UserEntity> redisTemplate) {
        super(redisTemplate);
    }

    @Override
    public String getCommonKeyPrefix() {
        return LOGIN_USER_CACHE_PREFIX;
    }
}
