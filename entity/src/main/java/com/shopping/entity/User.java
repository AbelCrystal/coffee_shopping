package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_user")
public class User implements Serializable {
    @TableId(value = "id")
    private String id;

    @TableField(value = "inviter_id", exist = true)
    private String inviterId;
    @TableField(value = "wx_uuid", exist = true)
    private String wxUUId;
    @TableField(value = "ali_uuid", exist = true)
    private String aliUUId;
    private String username;

    private String password;

    private String phone;

    private String email;

    @TableField(value = "pay_password", exist = true)
    private String payPassword;

    private String sex;

    @TableField(value = "user_img_url", exist = true)
    private String userImgUrl;

    @TableField(value = "real_name", exist = true)
    private String realName;

    private String profession;

    private Date birthday;

    @TableField(value = "user_score", exist = true)
    private int userScore;

    private String status;

    @TableField(value = "invitation_code",exist = true)
    private String invitationCode;

    @TableField(value = "is_delete", exist = true)
    private String isDelete;

    @TableField(value = "last_login_time", exist = true)
    private Date lastLoginTime;

    @TableField(value = "create_time", exist = true)
    private Date createTime;

    @TableField(value = "update_time", exist = true)
    private Date updateTime;
    @TableField(exist = false)
    private String tableNo;
    @TableField(exist = false)
    private String supplierId;
}