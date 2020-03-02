package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *@description: 系统参数表
 *@author:Darryl_Tang
 *@Time:2019/7/2
 *
 */

@Data
@TableName("tb_systemargs")
public class SystemArgsBean implements Serializable {
    @TableId(value = "sys_id")
    private String sysId;       //参数id

    @TableField(value = "sys_key",exist = true)
    private String key;        //键

    @TableField(value = "sys_value", exist = true)
    private String value;      //值

    @TableField(value = "description", exist = true)
    private String description;       //描述

    @TableField(value = "url", exist = true)
    private String url;    //url路径

    @TableField(value = "is_open",exist = true)
    private String isOpen;    //是否开启 默认 0未开启  1开启

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改删除时间

    @TableField(value = "is_delete",exist = true)
    private String isDelete;    //是否删除
}