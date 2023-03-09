package com.swm.service;

import com.swm.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission){
        //如果是超级管理员 直接返回true
        if (SecurityUtils.isAdmin()){
            return true;
        }
        //否则获取当前登录用户的权限列表，
        List<String> permissions = SecurityUtils.getLoginUser().getPermission();
        return permissions.contains(permission);
    }
}
