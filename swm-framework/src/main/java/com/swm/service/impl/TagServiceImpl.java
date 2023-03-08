package com.swm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.entity.Tag;
import com.swm.mapper.TagMapper;
import com.swm.service.TagService;
import org.springframework.stereotype.Service;

/**
 * 标签(Tag)表服务实现类
 *
 * @author swm
 * @since 2023-03-07 16:28:05
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

