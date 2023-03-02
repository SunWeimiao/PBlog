package com.swm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.User;
import com.swm.domain.vo.UserInfoVo;
import com.swm.mapper.UserMapper;
import com.swm.service.UserService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author swm
 * @since 2023-03-01 13:57:27
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}

