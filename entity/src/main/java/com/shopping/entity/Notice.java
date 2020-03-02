package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tb_notice_msg")
@ApiModel(value = "公告")
public class Notice {
    @ApiModelProperty(name = "id",notes = "id",dataType = "String",required = true,allowEmptyValue = false)
    @TableId
    private String id;
    @ApiModelProperty(name = "title",notes = "标题",dataType = "String",required = true,allowEmptyValue = false)
    private String title;
    @ApiModelProperty(name = "noticeType",notes = "广告类型",dataType = "String",required = true,allowEmptyValue = false)
    private String noticeType;
    @ApiModelProperty(name = "endTime",notes = "结束时间",dataType = "String",required = true,allowEmptyValue = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    @ApiModelProperty(name = "startTime",notes = "开始时间",dataType = "String",required = true,allowEmptyValue = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty(name = "content",notes = "广告内容",dataType = "String",required = true,allowEmptyValue = false)
    private String content;


}