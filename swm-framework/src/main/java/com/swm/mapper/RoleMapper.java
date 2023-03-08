package com.swm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swm.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author swm
 * @since 2023-03-07 21:13:36
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

