package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_message_template")
public class MessageTemplate {
    @TableId
    private String id;
    @TableField(value = "sms_type", exist = true)
    private String smsType;
    private String template;
    @TableField(value = "create_time", exist = true)
    private Date createTime;


}