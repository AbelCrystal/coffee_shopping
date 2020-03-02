package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("tb_user_login_log")
public class UserLoginLog implements Serializable {
    @TableField(value = "login_id", exist = true)
    @TableId(value = "login_id")
    private String loginId;
    @TableField(value = "user_id", exist = true)
    private String userId;
    @TableField(value = "login_time", exist = true)
    private Date loginTime;
    @TableField(value = "login_ip", exist = true)
    private String loginIp;
    @TableField(value = "login_state", exist = true)
    private String loginState;
    @TableField(value = "is_delete", exist = true)
    private String isDelete;

}