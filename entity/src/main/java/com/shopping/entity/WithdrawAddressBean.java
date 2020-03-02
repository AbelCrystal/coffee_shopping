package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 16:24
 * @Description: 提币地址
 */

@Data
@TableName("tb_withdraw_address")
public class WithdrawAddressBean {

    @TableId(value = "withdraw_id")
    private String withdrawId;       //提币地址id

    @TableField(value = "currency_id", exist = true)
    private String currencyId;       //币种id

    @TableField(value = "address",exist = true)
    private String address;     //充币地址

    @TableField(value = "user_id", exist = true)
    private String userId;      //用户id

    @TableField(value = "tag")
    private String tag;     //tag域

    @TableField(value = "label")
    private String label;   //备注

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改时间

    @TableField(value = "is_delete",exist = true)
    private String isDelete;    //是否删除 0正常 1删除

    @TableField(value = "is_default")
    private String isDefault;   //是否默认 0否 1是

}