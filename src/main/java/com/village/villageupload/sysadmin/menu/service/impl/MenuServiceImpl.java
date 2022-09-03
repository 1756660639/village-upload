package com.village.villageupload.sysadmin.menu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.village.villageupload.sysadmin.menu.entity.MenuEntity;
import com.village.villageupload.sysadmin.menu.mapper.MenuMapper;
import com.village.villageupload.sysadmin.menu.service.MenuService;
import com.village.villageupload.utils.PageResource;
import com.village.villageupload.utils.Pageable;
import com.village.villageupload.utils.TreeUtil;
import com.village.villageupload.utils.UuidUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuEntity> queryAllMenu() {
        List<MenuEntity> menu = menuMapper.queryAllMenu();
        List<MenuEntity> menuTree = TreeUtil.bulid(menu,"-1");
        return menuTree;
    }

    @Override
    public MenuEntity insert(MenuEntity resource) {
        resource.setId(UuidUtils.uuid(true));
        menuMapper.insert(resource);
        return resource;
    }

    @Override
    public MenuEntity update(MenuEntity resource){
        menuMapper.updateById(resource);
        return resource;
    }

    @Override
    public int deleteById(String id) {
        return menuMapper.deleteById(id);
    }
}
