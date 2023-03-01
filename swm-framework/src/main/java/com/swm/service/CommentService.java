package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author swm
 * @since 2023-03-01 12:17:21
 */
public interface CommentService extends IService<Comment> {
    ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize);
}

