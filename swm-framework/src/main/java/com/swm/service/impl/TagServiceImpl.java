package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.TagListDto;
import com.swm.domain.entity.Article;
import com.swm.domain.entity.Tag;
import com.swm.domain.entity.User;
import com.swm.domain.vo.PageVo;
import com.swm.domain.vo.TagVo;
import com.swm.enums.AppHttpCodeEnum;
import com.swm.exception.SystemException;
import com.swm.mapper.TagMapper;
import com.swm.service.TagService;
import com.swm.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 标签(Tag)表服务实现类
 *
 * @author swm
 * @since 2023-03-07 16:28:05
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.like(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());

        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);
        //封装
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        //若有标签的name相同 则报错
        if (tagNameExist(tag.getName())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_EXIST);
        }
        if(!StringUtils.hasText(tag.getName()) || !StringUtils.hasText(tag.getRemark()) ){
            throw new SystemException(AppHttpCodeEnum.TAG_AND_REMARK_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTagById(Long id) {
        TagMapper tagMapper = getBaseMapper();
        tagMapper.deleteTagById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectTagById(Long id) {
        Tag tag = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTagById(Tag tag) {
//        Tag tag = updateById(tag.getId())
        return null;
    }


    private boolean tagNameExist(String tagName) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tagName);
        return count(queryWrapper)>0;
    }
}

