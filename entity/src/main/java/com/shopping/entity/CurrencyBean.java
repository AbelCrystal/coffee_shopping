package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;


/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2
 * @Description: 币种
 */


@Data
@TableName("tb_currency")
public class CurrencyBean {
    @TableId
    private String id;  //币id

    @TableField(value = "currency_name")
    private String currencyName;    //虚拟币名称

    @TableField(value = "status")
    private String status;      //虚拟币状态 0正常 1禁用,

    @TableField(value = "url")
    private String url;     //币种图片

    @TableField(value = "access_key")
    private String accessKey;   //存取键

    @TableField(value = "secrt_key")
    private String secrtKey;    //密匙

    @TableField(value = "ip")
    private String ip;      //IP地址

    @TableField(value = "port")
    private String port;    //端口

    @TableField(value = "is_withdraw")
    private String isWithdraw;  //是否支持提现 默认0 正常 1禁用

    @TableField(value = "is_charge")
    private String isCharge;    //是否支持充值 默认 0正常 1禁用

    @TableField(value = "confirms")
    private int confirms;   //充值确认次数

    @TableField(value = "remark")
    private String remark;      //备注

    @TableField(value = "create_time")
    private Date createTime;    //创建时间

    @TableField(value = "modified_time")
    private Date modifiedTime;  //修改时间

}