package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_user_info_pic")
public class UserInfoPic {
    @TableId(value = "id")
    private String id;
    @TableField(value = "pic_url", exist = true)
    private String picUrl;
    @TableField(value = "pic_path", exist = true)
    private String picPath;
    private String remark;
    private String status;
    @TableField(value = "update_time", exist = true)
    private Date updateTime;
    @TableField(value = "create_time", exist = true)
    private Date createTime;
}