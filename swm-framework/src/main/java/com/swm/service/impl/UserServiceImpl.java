package com.swm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.entity.User;
import com.swm.mapper.UserMapper;
import com.swm.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author swm
 * @since 2023-03-01 13:57:27
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

