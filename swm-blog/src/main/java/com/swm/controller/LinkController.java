package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;
    /**
     * 展示所有已通过的友链
     * */
    @GetMapping("/getAllLink")
    public ResponseResult getAllLinks(){
        return linkService.getAllLinks();
    }
}
