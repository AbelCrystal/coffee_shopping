package com.shopping.vo.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "消息实体")
@Data
public class MessageBeanVo implements Serializable {
    @ApiModelProperty(name = "id", notes = "id", dataType = "String")
    private String id;

    @ApiModelProperty(name = "userId", notes = "用户id", dataType = "String")
    private String userId;

    @ApiModelProperty(name = "type", notes = "消息类型", dataType = "String")
    private String type;

    @ApiModelProperty(name = "status", notes = "消息状态 0：未读 ，1：已读", dataType = "String")
    private String status;

    @ApiModelProperty(name = "title", notes = "消息标题", dataType = "String")
    private String title;

    @ApiModelProperty(name = "content", notes = "消息内容", dataType = "String")
    private String content;

    @ApiModelProperty(name = "createTime", notes = "创建时间", dataType = "Date")
    private Date createTime;

    @ApiModelProperty(name = "updateTime",notes = "修改时间",dataType = "Date")
    private Date updateTime;
}
