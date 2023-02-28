package com.swm.service;

import com.swm.domain.ResponseResult;
import com.swm.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);
}
