package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.constants.SystemConstants;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddArticleDto;
import com.swm.domain.entity.Article;
import com.swm.domain.entity.ArticleTag;
import com.swm.domain.entity.Category;
import com.swm.domain.vo.ArticleListVo;
import com.swm.domain.vo.HotArticleVo;
import com.swm.domain.vo.PageVo;
import com.swm.domain.vo.articleDetailVo;
import com.swm.mapper.ArticleMapper;
import com.swm.service.ArticleService;
import com.swm.service.ArticleTagService;
import com.swm.service.CategoryService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult getHotArticleList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是已发布的文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按浏览量降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查十条
        Page<Article> page = new Page<>(1, SystemConstants.HOT_ARTICLE_COUNTS);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        /**
         * 从redis中获取文章浏览量
         * */
        for (Article article_i : articles){
            //从redis中获取浏览量
            Long id = article_i.getId();
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDISKEY_VIEWCOUNT, id.toString());
            article_i.setViewCount(viewCount.longValue());
        }
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles,HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //判断pageNum和pageSize是否为空，若为空，设置默认值
        if(pageNum==null||pageSize==null){
            pageNum = 1;
            pageSize = 10;
        }

        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //若有id 查询时和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && (categoryId> 0),Article::getCategoryId,categoryId);
        //已发布的文章
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        //对istop进行排序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        //查询分类名称categoryName
        List<Article> articles = page.getRecords();
        /**
         * 从redis中获取文章浏览量
         * */
        for (Article article_i : articles){
            //从redis中获取浏览量
            Long id = article_i.getId();
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDISKEY_VIEWCOUNT, id.toString());
            article_i.setViewCount(viewCount.longValue());
        }

        //根据categoryId查询categoryName
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取浏览量
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.REDISKEY_VIEWCOUNT, id.toString());
        article.setViewCount(viewCount.longValue());
        //转换成VO
        articleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, articleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }
        //封装
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新reids中对应文章的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.REDISKEY_VIEWCOUNT,id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDto articleDto) {
        //添加 博客
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        save(article);


        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());

        //添加 博客和标签的关联
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }
}
