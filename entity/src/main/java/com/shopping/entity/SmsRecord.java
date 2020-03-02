package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_sms_record")
public class SmsRecord implements Serializable {
    @TableId(value = "id")
    private String id;
    @TableField(value = "mobile_number", exist = true)
    private String mobileNumber;
    @TableField(value = "validate_code", exist = true)
    private String validateCode;
    @TableField(value = "sms_content", exist = true)
    private String smsContent;
    @TableField(value = "dead_time", exist = true)
    private Date deadTime;
    private String sended;
    @TableField(value = "create_time", exist = true)
    private Date createTime;
    @TableField(value = "update_time", exist = true)
    private Date updateTime;
    @TableField(value = "sms_type", exist = true)
    private String smsType;
}