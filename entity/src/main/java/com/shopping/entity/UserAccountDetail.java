package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_user_account_detail")
public class UserAccountDetail implements Serializable {

    @TableField(value = "id", exist = true)
    @TableId(value = "id")
    private String id;

    @TableField(value = "currency_id", exist = true)
    private String currencyId;// '币种id',

    @TableField(value = "user_id", exist = true)
    private String  userId;// '用户id',

    @TableField(value = "amount", exist = true)
    private BigDecimal amount;// '操作金额',

    @TableField(value = "changes_source_id", exist = true)
    private String changesSourceId;// '变更来源id',

    @TableField(value = "update_time", exist = true)
    private Date updateTime;// '修改时间',

    @TableField(value = "changes_type", exist = true)
    private String changesType;// 0支出  1收入 2充值 3转币,4冻结，5解冻，6分润

    @TableField(value = "pay_type", exist = true)
    private String payType;// 支付方式0商城币，1微信，2支付宝

    @TableField(value = "create_time", exist = true)
    private Date createTime;// '创建时间',

    @TableField(value = "remark")
    private String remark;  //备注
    @TableField(value = "account_type")
    private String accountType;  //支付货币类型
    @TableField(value = "phone")
    private String phone;  //支付货币类型

}