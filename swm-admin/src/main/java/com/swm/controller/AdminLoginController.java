package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.UserDto;
import com.swm.domain.entity.LoginUser;
import com.swm.domain.entity.Menu;
import com.swm.domain.entity.User;
import com.swm.domain.vo.AdminUserInfoVo;
import com.swm.domain.vo.RoutersVo;
import com.swm.domain.vo.UserInfoVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.service.AdminLoginService;
import com.swm.service.BlogLoginService;
import com.swm.service.MenuService;
import com.swm.service.RoleService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.RedisCache;
import com.swm.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(description = "博客登录相关接口")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RoleService roleService;

    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录")
    public ResponseResult login(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        if(!StringUtils.hasText(user.getUserName())){
            //提示，必须传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //获取用户信息
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //封装
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询routers 结果是tree的形式
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        //封装
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    @ApiOperation(value = "用户登出")
    public ResponseResult logout(){
        return adminLoginService.logout();
    }

}
