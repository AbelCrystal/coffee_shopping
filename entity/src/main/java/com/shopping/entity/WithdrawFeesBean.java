package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 16:24
 * @Description: 提币手续费
 */

@Data
@TableName("tb_withdraw_fees")
public class WithdrawFeesBean {

    @TableId(value = "fee_id")
    private String feeId;       //手续费id

    @TableField(value = "currency_id", exist = true)
    private String currencyId;       //币种id

    @TableField(value = "withdraw_fee_id",exist = true)
    private String withdrawFeeId;     //提现手续费币id

    @TableField(value = "withdraw", exist = true)
    private BigDecimal withdraw;      //提现手续费固定值

    @TableField(value = "min_withdraw")
    private BigDecimal minWithdraw;     //最小提现额度

    @TableField(value = "withdraw_ratio")
    private BigDecimal withdrawRatio;   //提现手续费比例

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //创建时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改时间

}