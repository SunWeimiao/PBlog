package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddLinkDto;
import com.swm.domain.dto.EditLinkDto;
import com.swm.domain.entity.Link;
import com.swm.domain.vo.PageVo;
import com.swm.service.LinkService;
import com.swm.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
@Api("友链管理")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    @ApiOperation("获取友链列表")
    public ResponseResult list(Link link, Integer pageNum, Integer pageSize)
    {
        PageVo pageVo = linkService.selectLinkPage(link,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    @ApiOperation("新增友链")
    public ResponseResult add(@RequestBody AddLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除友链")
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

    @PutMapping
    @ApiOperation("修改友链")
    public ResponseResult edit(@RequestBody EditLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @PutMapping("/changeLinkStatus")
    @ApiOperation("修改友链状态")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }



    @GetMapping(value = "/{id}")
    @ApiOperation("获取友链详情")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }
}
