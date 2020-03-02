package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 16:24
 * @Description: 充提币记录表
 */

@Data
@TableName("tb_capital_operation")
public class CapitalOperationBean {

    @TableId(value = "id")
    private String id;       //记录id

    @TableField(value = "user_id", exist = true)
    private String userId;      //用户id

    @TableField(value = "currency_id", exist = true)
    private String currencyId;       //币种id

    @TableField(value = "amount")
    private BigDecimal amount;      //操作金额

    @TableField(value = "fees")
    private BigDecimal fees;    //手续费

    @TableField(value = "fee_coin_id")
    private String feeCoinId;       //手续费币id

    @TableField(value = "type")
    private String type;        //类型 0充币 1提币

    @TableField(value = "status")
    private String status;      //'状态 充值 0项确认 1项确认 2项确认 3充值成功 提现 0等待提现 1正在处理 2提现中 3提现成功 4提现失败 5用户撤销',

    @TableField(value = "address",exist = true)
    private String address;     //充币或提币地址  由type 决定

    @TableField(value = "confirms")
    private int confirms;   //确认次数

    @TableField(value = "has_owner")
    private String hasOwner;    //'该地址是否为平台内用户地址 0是 1否', 默认0

    @TableField(value = "admin_id")
    private String adminId;     //审核管理员id

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改时间

    @Version
    private Integer version;    //版本

}