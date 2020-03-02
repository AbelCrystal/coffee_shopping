package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@TableName("tb_refunds_goods")
public class OrderRefundsGoods {
    @TableId
    private String id;
    @TableField(value = "order_id",exist = true)
    private String orderId;
    @TableField(value = "order_detail_id",exist = true)
    private String orderDetailId;
    @TableField(value = "supplier_id",exist = true)
    private String supplierId;
    @TableField(value = "refund_to_id",exist = true)
    private String refundToId;
    @TableField(value = "refund_trade_no",exist = true)
    private String refundTradeNo;
    @TableField(value = "refund_remark",exist = true)
    private String refundRemark;
    @TableField(value = "refund_time",exist = true)
    private Date refundTime;
    @TableField(value = "refund_other_reson",exist = true)
    private String refundOtherReson;
    @TableField(value = "back_money",exist = true)
    private BigDecimal backMoney;
    @TableField(value = "shop_reject_reason",exist = true)
    private String shopRejectReason;
    @TableField(value = "refund_status",exist = true)
    private String refundStatus;
    @TableField(value = "contact_name",exist = true)
    private String contactName;
    @TableField(value = "contact_phone",exist = true)
    private String contactPhone;
    @TableField(value = "description",exist = true)
    private String description;
    @TableField(value = "auditor_reason",exist = true)
    private String auditorReason;
    @TableField(value = "audit_message",exist = true)
    private String auditMessage;
    @TableField(value = "only_refund",exist = true)
    private String onlyRefund;
    @TableField(value = "pay_type",exist = true)
    private String payType;
    @TableField(value = "create_time",exist = true)
    private Date createTime;
}