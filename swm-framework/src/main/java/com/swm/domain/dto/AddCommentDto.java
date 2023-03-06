package com.swm.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "添加评论dto")
public class AddCommentDto {
    private Long id;
    @ApiModelProperty(notes = "0代表文章评论，1代表友链评论")
    //评论类型（0代表文章评论，1代表友链评论）
    private String type;

    //文章id
    @ApiModelProperty(notes = "文章id")
    private Long articleId;

    //根评论id
    @ApiModelProperty(notes = "根评论id")
    private Long rootId;

    //评论内容
    @ApiModelProperty(notes = "评论内容")
    private String content;

    @ApiModelProperty(notes = "所回复的目标评论的userid")
    private Long toCommentUserId;

    //
    @ApiModelProperty(notes = "所回复的目标评论的评论id")
    private Long toCommentId;

    @ApiModelProperty(notes = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;

    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
}
