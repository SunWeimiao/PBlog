package com.swm.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddCategoryDto;
import com.swm.domain.dto.EditCategoryDto;
import com.swm.domain.entity.Category;
import com.swm.domain.vo.CategoryVo;
import com.swm.domain.vo.ExcelCategoryVo;
import com.swm.domain.vo.PageVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.service.CategoryService;
import com.swm.utils.BeanCopyUtils;
import com.swm.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/content/category")
@Api(description = "分类管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    @ApiOperation("写博文时获取所有分类")
    public ResponseResult listAllCategory(){
        List<CategoryVo> list = categoryService.listAllCategory();
        return ResponseResult.okResult(list);
    }
    @PutMapping
    @ApiOperation("更新分类")
    public ResponseResult edit(@RequestBody EditCategoryDto editTagDto){
        Category category = BeanCopyUtils.copyBean(editTagDto,Category.class);
        categoryService.updateCategoryById(category);
        return ResponseResult.okResult();
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("删除分类")
    public ResponseResult remove(@PathVariable(value = "id")Long id){
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("获取分类详情")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }
    @PostMapping
    @ApiOperation("新增分类")
    public ResponseResult add(@RequestBody AddCategoryDto addCategoryDto){
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        categoryService.addCategory(category);
        return ResponseResult.okResult();

    }

    @GetMapping("/list")
    @ApiOperation("获取分类列表")
    public ResponseResult list(Category category, Integer pageNum, Integer pageSize) {
        PageVo pageVo = categoryService.selectCategoryPage(category,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }
//      TODO 权限检查
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    @ApiOperation("导出分类")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }
}
