package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 平台资产表
 *@author:Darryl_Tang
 *@Time:2019/7/21 0025 17:08
 *
 */

@Data
@TableName("tb_rmb_recharge")
public class RmbRecharge implements Serializable {
    @TableId(value = "id")
    private String id;       //充值订单
    @TableField(value = "user_id",exist = true)
    private String userId;        //平台id
    @TableField(value = "phone",exist = true)
    private String phone;        //手机号
    @TableField(value = "recharge_amount", exist = true)
    private BigDecimal rechargeAmount;      //币种id
    @TableField(value = "status", exist = true)
    private String status;      //货币类型
    @TableField(value = "recharge_type", exist = true)
    private String rechargeType;      //充值方式1支付宝2微信
    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间
    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改删除时间

}