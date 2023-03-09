package com.swm.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "分类表DTO")
public class EditCategoryDto {
    private Long id;
    //分类名
    private String name;
    //描述
    private String description;
    //状态0:正常,1禁用
    private String status;

}
