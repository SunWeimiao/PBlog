package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.constants.SystemConstants;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Article;
import com.swm.domain.entity.Category;
import com.swm.domain.entity.Tag;
import com.swm.domain.vo.CategoryVo;
import com.swm.domain.vo.PageVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.mapper.CategoryMapper;
import com.swm.service.ArticleService;
import com.swm.service.CategoryService;
import com.swm.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author swm
 * @since 2023-02-26 17:47:59
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表状态为已发布
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id并去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        //封装
        categories = categories.stream()
                .filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

    @Override
    public PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize) {


        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(category.getName()),Category::getName, category.getName());
        queryWrapper.eq(StringUtils.hasText(category.getStatus()),Category::getStatus, category.getStatus());

        Page<Category> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<Category> categories = page.getRecords();

        PageVo pageVo = new PageVo();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(categories);
        return pageVo;
    }

    @Override
    public ResponseResult addCategory(Category category) {
        //若有分类的name相同 则报错
        if (categoryNameExist(category.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_EXIST);
        }
        if(!StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getDescription()) ){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_AND_DESCRIPTION_NOT_NULL);
        }
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateCategoryById(Category category) {
        //若有分类的name相同 则报错
        if (categoryNameExist(category.getName())){
            throw new SystemException(AppHttpCodeEnum.CATEGORYNAME_EXIST);
        }
        if(!StringUtils.hasText(category.getName()) || !StringUtils.hasText(category.getDescription()) ){
            throw new SystemException(AppHttpCodeEnum.CATEGORY_AND_DESCRIPTION_NOT_NULL);
        }
        updateById(category);
        return ResponseResult.okResult();
    }

    private boolean categoryNameExist(String Name) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getName,Name);
        return count(queryWrapper)>0;
    }
}

