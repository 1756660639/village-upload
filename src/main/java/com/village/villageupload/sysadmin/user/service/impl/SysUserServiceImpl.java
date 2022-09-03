package com.village.villageupload.sysadmin.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.sysadmin.user.entity.SysUserEntity;
import com.village.villageupload.sysadmin.user.mapper.SysUserMapper;
import com.village.villageupload.sysadmin.user.service.SysUserService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUserEntity> listAll(SysUserEntity resource) {
        return sysUserMapper.listAll(resource);
    }

    @Override
    public PageResource<SysUserEntity> listAllPage(SysUserEntity resource) {
        Page<SysUserEntity> page = sysUserMapper.listAllPage(Pageable.Pageable(), resource);
        return new PageResource<>(page);
    }

    @Override
    public SysUserEntity insert(SysUserEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        sysUserMapper.insert(resource);
        return resource;
    }

    @Override
    public SysUserEntity update(SysUserEntity resource){
        sysUserMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return sysUserMapper.deleteById(id);
    }
    
    @Override
    public SysUserEntity findById(String id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> listAllByUserNameAndDept(SysUserEntity resource) {
        return sysUserMapper.listAllByUserNameAndDept(resource);
    }

}
