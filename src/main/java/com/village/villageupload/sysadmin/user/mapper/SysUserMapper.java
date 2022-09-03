package com.village.villageupload.sysadmin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.sysadmin.user.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUserEntity> {
    /**
     * 查询所有数据
     * @param resource
     * @return
     */
    List<SysUserEntity> listAll(@Param("resource") SysUserEntity resource);

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<SysUserEntity> listAllPage(@Param("Page") Page page, @Param("resource") SysUserEntity resource);

    List<Map<String,Object>> listAllByUserNameAndDept(@Param("resource") SysUserEntity resource);
}
