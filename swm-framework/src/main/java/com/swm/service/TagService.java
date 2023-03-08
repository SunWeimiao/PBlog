package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.TagListDto;
import com.swm.domain.entity.Tag;
import com.swm.domain.vo.PageVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author swm
 * @since 2023-03-07 16:28:02
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tag);

    ResponseResult deleteTagById(Long id);

    ResponseResult selectTagById(Long id);


    ResponseResult updateTagById(Tag tag);
}

