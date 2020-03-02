package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_message")
@ApiModel(value = "消息")
public class MessageBean {
    @TableField(value = "id",exist = true)
    @TableId(value = "id")
    private String id;      //消息id

    @TableField(value = "user_id",exist = true)
    private String userId;  //用户id

    @TableField(value = "type",exist = true)
    private String type;    //消息类型 0系统消息 1交易物流消息 2客服消息

    @TableField(value = "status",exist = true)
    private String status;  //消息状态 0：未读 ，1：已读

    @TableField(value = "title",exist = true)
    private String title;   //消息标题

    @TableField(value = "content",exist = true)
    private String content; //消息内容

    @TableField(value = "create_time",exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time",exist = true)
    private Date updateTime;    //修改时间

}