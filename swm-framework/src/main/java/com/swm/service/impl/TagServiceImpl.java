package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.TagListDto;
import com.swm.domain.entity.Article;
import com.swm.domain.entity.Category;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
        if (tagNameExist(tag.getName(), tag.getId())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_EXIST);
        }
        if(!StringUtils.hasText(tag.getName()) || !StringUtils.hasText(tag.getRemark()) ){
            throw new SystemException(AppHttpCodeEnum.TAG_AND_REMARK_NOT_NULL);
        }
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateTagById(Tag tag) {
        //若有标签的name相同 则报错
        if (tagNameExist(tag.getName(), tag.getId())){
            throw new SystemException(AppHttpCodeEnum.TAGNAME_EXIST);
        }
        if(!StringUtils.hasText(tag.getName()) || !StringUtils.hasText(tag.getRemark()) ){
            throw new SystemException(AppHttpCodeEnum.TAG_AND_REMARK_NOT_NULL);
        }
        updateById(tag);
        return ResponseResult.okResult();
    }


    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return tagVos;
    }


    private boolean tagNameExist(String tagName,Long id) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getName,tagName);
        int numName = count(queryWrapper);
        queryWrapper.eq(Tag::getId,id);
        int numId = count(queryWrapper);
        // numId==numName 命名不重复
        if (numId==numName){
            return false;
        }
        return true;
    }
}

