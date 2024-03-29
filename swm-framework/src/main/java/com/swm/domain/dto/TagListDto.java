package com.swm.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "标签表DTO")
public class TagListDto {
    private Long id;
    private String name;
    private String remark;
}
