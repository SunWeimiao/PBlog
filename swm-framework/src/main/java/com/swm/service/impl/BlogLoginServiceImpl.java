package com.swm.service.impl;

import com.swm.domain.ResponseResult;
import com.swm.domain.entity.LoginUser;
import com.swm.domain.entity.User;
import com.swm.domain.vo.BlogUserLoginVo;
import com.swm.domain.vo.UserInfoVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.service.BlogLoginService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.JwtUtil;
import com.swm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException(AppHttpCodeEnum.LOGIN_ERROR.getMsg());
        }
        //获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("SunWeiMiaobloglogin"+userId, loginUser);
        //把token和userinfo封装 返回
        //把user转化成userinfovo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token，解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject("SunWeiMiaobloglogin"+userId);
        return ResponseResult.okResult();
    }
}
