package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Link;
import com.swm.domain.vo.PageVo;


/**
 * 友链(Link)表服务接口
 *
 * @author swm
 * @since 2023-02-27 16:19:46
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLinks();

    PageVo selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}

