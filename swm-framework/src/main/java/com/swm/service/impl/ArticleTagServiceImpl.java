package com.swm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.entity.ArticleTag;
import com.swm.mapper.ArticleTagMapper;
import com.swm.service.ArticleTagService;
import org.springframework.stereotype.Service;


@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
