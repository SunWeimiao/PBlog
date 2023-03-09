package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddLinkDto;
import com.swm.domain.dto.EditLinkDto;
import com.swm.domain.entity.Link;
import com.swm.domain.vo.PageVo;
import com.swm.service.LinkService;
import com.swm.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Link link, Integer pageNum, Integer pageSize)
    {
        PageVo pageVo = linkService.selectLinkPage(link,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @PostMapping
    public ResponseResult add(@RequestBody AddLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.save(link);
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

    @PutMapping
    public ResponseResult edit(@RequestBody EditLinkDto linkDto){
        Link link = BeanCopyUtils.copyBean(linkDto, Link.class);
        linkService.updateById(link);
        return ResponseResult.okResult();
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }



    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Link link = linkService.getById(id);
        return ResponseResult.okResult(link);
    }
}
