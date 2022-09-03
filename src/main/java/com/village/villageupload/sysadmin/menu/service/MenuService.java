package com.village.villageupload.sysadmin.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.village.villageupload.login.entity.UserEntity;
import com.village.villageupload.sysadmin.menu.entity.MenuEntity;
import com.village.villageupload.utils.PageResource;

import java.util.List;
import java.util.Map;

public interface MenuService extends IService<MenuEntity> {

    List<MenuEntity> queryAllMenu();

    /**
     * 新增数据
     * @param resource
     * @return
     */
    MenuEntity insert(MenuEntity resource);

    /**
     * 更新数据
     * @param resource
     * @return
     */
    MenuEntity update(MenuEntity resource);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteById(String id);

}
