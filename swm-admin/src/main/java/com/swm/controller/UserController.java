package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.ChangeRoleStatusDto;
import com.swm.domain.dto.ChangeUserStatusDto;
import com.swm.domain.entity.Role;
import com.swm.domain.entity.User;
import com.swm.domain.vo.UserInfoAndRoleIdsVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.service.RoleService;
import com.swm.service.UserService;
import com.swm.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/user")
@Api("用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @ApiOperation("获取用户列表")
    public ResponseResult list(User user, Integer pageNum, Integer pageSize) {
        return userService.selectUserPage(user,pageNum,pageSize);
    }


    @PostMapping
    @ApiOperation("新增用户")
    public ResponseResult add(@RequestBody User user)
    {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }


    @GetMapping(value = { "/{userId}" })
    @ApiOperation("根据用户编号获取详细信息")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId)
    {
        List<Role> roles = roleService.selectRoleAll();
        User user = userService.getById(userId);
        //当前用户所具有的角色id列表
        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);

        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }


    @PutMapping
    @ApiOperation("修改用户")
    public ResponseResult edit(@RequestBody User user) {
        userService.updateUser(user);
        return ResponseResult.okResult();
    }


    @DeleteMapping("/{userIds}")
    @ApiOperation("删除用户")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }

    @PutMapping("/changeStatus")
    @ApiOperation("修改角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeUserStatusDto userStatusDto){
        User user = new User();
        user.setId(userStatusDto.getUserId());
        user.setStatus(userStatusDto.getStatus());
        return ResponseResult.okResult(userService.updateById(user));
    }

}
