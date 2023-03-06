package com.swm.controller;

import com.swm.annotation.SystemLog;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.UserDto;
import com.swm.domain.entity.User;
import com.swm.service.UserService;
import com.swm.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    @ApiOperation("获取用户信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    @ApiOperation("更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public ResponseResult register(@RequestBody UserDto userDto){
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        return userService.register(user);
    }
}
