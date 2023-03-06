package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
@Api(description = "友链相关接口")
public class LinkController {

    @Autowired
    private LinkService linkService;
    /**
     * 展示所有已通过的友链
     * */
    @GetMapping("/getAllLink")
    @ApiOperation(value = "获取所有友链")
    public ResponseResult getAllLinks(){
        return linkService.getAllLinks();
    }
}
