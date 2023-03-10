package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.entity.RoleMenu;


public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}