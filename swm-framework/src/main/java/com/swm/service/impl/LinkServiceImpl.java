package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.constants.SystemConstants;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Link;
import com.swm.domain.vo.LinkVo;
import com.swm.mapper.LinkMapper;
import com.swm.service.LinkService;
import com.swm.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author swm
 * @since 2023-02-27 16:19:48
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLinks() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        //转换为Vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list,LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

