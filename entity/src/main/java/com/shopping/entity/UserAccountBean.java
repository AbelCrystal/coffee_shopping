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
 *@description: 用户资产表
 *@author:Darryl_Tang
 *@Time:2019/7/1 0025 17:08
 *
 */

@Data
@TableName("tb_user_account")
public class UserAccountBean implements Serializable {
    @TableId(value = "id")
    private String id;       //账户id

    @TableField(value = "user_id",exist = true)
    private String userId;        //用户id

    @TableField(value = "currency_id", exist = true)
    private String currencyId;      //币种id
    @TableField(exist = false)
    private String currencyName;      //币种Name

    @TableField(value = "amount", exist = true)
    private BigDecimal amount;       //账户总资产

    @TableField(value = "usable", exist = true)
    private BigDecimal usable;    //可用

    @TableField(value = "frozen",exist = true)
    private BigDecimal frozen;    //冻结
    @TableField(value = "accout_type", exist = true)
    private String accoutType;      //币种id
    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改删除时间
    @TableField(value = "phone")
    private String phone;  //支付货币类型
    @Version
    private Integer version;    //版本

}