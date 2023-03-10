package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author swm
 * @since 2023-03-07 21:08:05
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectMenuList(Menu menu);

    List<Long> selectMenuListByRoleId(Long roleId);

    boolean hasChild(Long menuId);
}

