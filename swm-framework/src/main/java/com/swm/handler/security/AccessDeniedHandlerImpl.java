package com.swm.handler.security;

import com.alibaba.fastjson.JSON;
import com.swm.domain.ResponseResult;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * 授权失败
 * */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //BadCredentialsException报错     登录 用户名或密码错
        //InsufficientAuthenticationException报错     未携带token

        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
