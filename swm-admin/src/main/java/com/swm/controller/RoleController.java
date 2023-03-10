package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.ChangeRoleStatusDto;
import com.swm.domain.entity.Role;
import com.swm.service.RoleService;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Role;
import com.swm.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/system/role")
@Api("角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping("/listAllRole")
    @ApiOperation("查询角色列表接口")
    public ResponseResult listAllRole(){
        List<Role> roles = roleService.selectRoleAll();
        return ResponseResult.okResult(roles);
    }


    @GetMapping(value = "/{roleId}")
    @ApiOperation("根据角色编号获取详细信息")
    public ResponseResult getInfo(@PathVariable Long roleId)
    {
        Role role = roleService.getById(roleId);
        return ResponseResult.okResult(role);
    }


    @PutMapping
    @ApiOperation("修改保存角色")
    public ResponseResult edit(@RequestBody Role role)
    {
        roleService.updateRole(role);
        return ResponseResult.okResult();
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除角色")
    public ResponseResult remove(@PathVariable(name = "id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }


    @PostMapping
    @ApiOperation("新增角色")
    public ResponseResult add( @RequestBody Role role)
    {
        roleService.insertRole(role);
        return ResponseResult.okResult();

    }
    @GetMapping("/list")
    @ApiOperation("角色列表")
    public ResponseResult list(Role role, Integer pageNum, Integer pageSize) {
        return roleService.selectRolePage(role,pageNum,pageSize);
    }

    @PutMapping("/changeStatus")
    @ApiOperation("修改角色状态")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){
        Role role = new Role();
        role.setId(roleStatusDto.getRoleId());
        role.setStatus(roleStatusDto.getStatus());
        return ResponseResult.okResult(roleService.updateById(role));
    }

}
