package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddTagDto;
import com.swm.domain.dto.EditTagDto;
import com.swm.domain.dto.TagListDto;
import com.swm.domain.entity.Tag;
import com.swm.domain.vo.PageVo;
import com.swm.domain.vo.TagVo;
import com.swm.service.TagService;
import com.swm.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/tag")
@Api("标签管理")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @ApiOperation(value = "查询标签列表")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping
    @ApiOperation(value = "添加标签")
    public ResponseResult addTag(@RequestBody AddTagDto addTagDto){
        Tag tag = BeanCopyUtils.copyBean(addTagDto, Tag.class);
        tagService.addTag(tag);
        return ResponseResult.okResult();
    }
    /**
     * 删除标签
     * */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除标签（逻辑删除）")
    public ResponseResult deleteTag(@PathVariable("id")Long id){
//        tagService.deleteTagById(id);
        tagService.removeById(id);
        return ResponseResult.okResult();
    }
    /**
     * 更新标签信息前，先获取
     * */
    @GetMapping("/{id}")
    @ApiOperation(value = "获取标签详情")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Tag tag = tagService.getById(id);
        return ResponseResult.okResult(tag);
    }
//    public ResponseResult selectTagById(@PathVariable("id")Long id){
//        tagService.selectTagById(id);
//        return ResponseResult.okResult();
//    }

    /**
     * 获取标签信息之后进行更新
     * */


    @PutMapping
    @ApiOperation(value = "更新标签")
    public ResponseResult edit(@RequestBody EditTagDto editTagDto){
        Tag tag = BeanCopyUtils.copyBean(editTagDto,Tag.class);
        tagService.updateTagById(tag);
        return ResponseResult.okResult();
    }
    @GetMapping("/listAllTag")
    @ApiOperation("写博文时获取所有标签")
    public ResponseResult listAllTag(){
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }
}

