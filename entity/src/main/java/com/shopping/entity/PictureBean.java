package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_picture")
public class PictureBean {
    @TableId(value = "pic_id")
    @TableField(value = "pic_id", exist = true)
    private String picId;       //图片id

    @TableField(value = "source_id", exist = true)
    private String sourceId;       //来源ID

    @TableField(value = "pic_type",exist = true)
    private String picType;     //图片来源类型 0退货 1意见反馈

    @TableField(value = "pic_desc", exist = true)
    private String picDesc;     //图片描述

    @TableField(value = "pic_url", exist = true)
    private String picUrl;      //图片URL

    @TableField(value = "pic_path", exist = true)
    private String picPath;     //图片服务器物理地址

    @TableField(value = "pic_status", exist = true)
    private String picStatus;   //图片是否有效 0有效 1无效

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改时间

}