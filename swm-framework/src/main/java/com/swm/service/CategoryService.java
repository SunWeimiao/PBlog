package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Category;
import com.swm.domain.vo.CategoryVo;
import com.swm.domain.vo.PageVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author swm
 * @since 2023-02-26 17:47:56
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    PageVo selectCategoryPage(Category category, Integer pageNum, Integer pageSize);

    ResponseResult addCategory(Category category);

    ResponseResult updateCategoryById(Category category);
}

