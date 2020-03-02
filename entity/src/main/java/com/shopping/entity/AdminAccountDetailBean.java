package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sys_admin_account_detail")
public class AdminAccountDetailBean implements Serializable {

    @TableField(value = "id", exist = true)
    @TableId(value = "id")
    private String id;

    @TableField(value = "currency_id", exist = true)
    private String currencyId;// '币种id',

    @TableField(value = "admin_id", exist = true)
    private String  adminId;// '平台id',

    @TableField(value = "amount", exist = true)
    private BigDecimal amount;// '操作金额',

    @TableField(value = "changes_user_id", exist = true)
    private String changesUserId;// '来源用户id',

    @TableField(value = "changes_source_id", exist = true)
    private String changesSourceId;// '来源id',

    @TableField(value = "update_time", exist = true)
    private Date updateTime;// '修改时间',

    @TableField(value = "changes_type", exist = true)
    private String changesType;// 0支出  1收入 2充值 3转币,4冻结，5解冻，6分润

    @TableField(value = "pay_type", exist = true)
    private String payType;// 支付方式0商城币，1微信，2支付宝
    @TableField(value = "account_type", exist = true)
    private String accountType;// 货币类型

    @TableField(value = "create_time", exist = true)
    private Date createTime;// '创建时间',

    @TableField(value = "remark")
    private String remark;  //备注

}