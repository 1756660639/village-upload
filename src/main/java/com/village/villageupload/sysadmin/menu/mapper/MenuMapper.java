package com.village.villageupload.sysadmin.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.village.villageupload.sysadmin.menu.entity.MenuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<MenuEntity> {

    List<MenuEntity> queryAllMenu();

    /**
     * 分页查询所有数据
     * @param page
     * @param resource
     * @return
     */
    Page<MenuEntity> listAllPage(@Param("Page") Page page, @Param("resource") MenuEntity resource);

}
