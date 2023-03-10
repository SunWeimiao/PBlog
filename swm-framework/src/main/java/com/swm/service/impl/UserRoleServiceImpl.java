package com.swm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.entity.UserRole;
import com.swm.mapper.UserRoleMapper;
import com.swm.service.UserRoleService;
import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
