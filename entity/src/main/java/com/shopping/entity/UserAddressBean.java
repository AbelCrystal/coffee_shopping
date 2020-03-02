package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *@description: 用户收货地址表
 *@author:Darryl_Tang
 *@Time:2019/6/25 0025
 *
 */

@Data
@TableName("tb_user_address")
public class UserAddressBean implements Serializable {
    @TableId(value = "address_id")
    private String addressId;       //地址id

    @TableField(value = "user_id",exist = true)
    private String userId;        //用户id

    @TableField(value = "user_name", exist = true)
    private String userName;      //收件人姓名

    @TableField(value = "user_phone", exist = true)
    private String userPhone;       //收件人联系方式

    @TableField(value = "postcode", exist = true)
    private String postcode;    //邮政编码

    @TableField(value = "province",exist = true)
    private String province;    //省份

    @TableField(value = "city",exist = true)
    private String city;        //市

    @TableField(value = "district",exist = true)
    private String district;    //区

    @TableField(value = "detaild_address",exist = true)
    private String detaildAddress;  //详细地址

    @TableField(value = "is_default",exist = true)
    private String isDefault;       //是否为默认收货地址

    @TableField(value = "is_delete",exist = true)
    private String isDelete;        //是否删除  0正常  1删除

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改删除时间

    @TableField(value = "mark", exist = true)
    private String mark;    //备注
}