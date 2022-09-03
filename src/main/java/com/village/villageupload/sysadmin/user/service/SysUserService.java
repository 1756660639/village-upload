package com.village.villageupload.sysadmin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.sysadmin.user.entity.SysUserEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUserEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<SysUserEntity> listAll(SysUserEntity resource);

    /**
     * 分页查询所有数据
     * @param resource
     * @return
     */
    PageResource<SysUserEntity> listAllPage(SysUserEntity resource);

    /**
     * 新增数据
     * @param resource
     * @return
     */
    SysUserEntity insert(SysUserEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    SysUserEntity update(SysUserEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);
    
    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysUserEntity findById(String id);

    /**
     * 根据用户名称或部门查询人
     * @param resource
     * @return
     */
    List<Map<String,Object>> listAllByUserNameAndDept(SysUserEntity resource);
}
