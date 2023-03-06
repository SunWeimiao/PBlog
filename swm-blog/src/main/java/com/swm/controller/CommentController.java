package com.swm.controller;

import com.swm.constants.SystemConstants;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddCommentDto;
import com.swm.domain.entity.Comment;
import com.swm.service.CommentService;
import com.swm.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(description = "评论相关接口")
public class CommentController {
    @Autowired
    private CommentService commentService;
    /**
     * 展示文章评论
     * */
    @GetMapping("/commentList")
    @ApiOperation(value = "根据文章id获取评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.ARTICLE_COMMENT,articleId, pageNum, pageSize);
    }
    /**
     * 用户评论接口
     * */
    @PostMapping
    @ApiOperation(value = "添加评论")
    public ResponseResult addComment(@RequestBody AddCommentDto commentDto){
        Comment comment = BeanCopyUtils.copyBean(commentDto, Comment.class);
        return commentService.addComment(comment);
    }
    /**
     * 展示友链评论
     * */
    @GetMapping("/linkCommentList")
    @ApiOperation(value = "获取友链评论")
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}
