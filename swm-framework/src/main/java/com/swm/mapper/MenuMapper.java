package com.swm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swm.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author swm
 * @since 2023-03-07 21:08:10
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long UserId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

