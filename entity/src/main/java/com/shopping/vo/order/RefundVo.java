package com.shopping.vo.order;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.shopping.entity.OrderRefundRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "退款返回实体")
@Data
public class RefundVo {
    @ApiModelProperty(name = "refundTradeNo", notes = "退款单号", dataType = "String")
    private String refundTradeNo;
    @ApiModelProperty(name = "createTime", notes = "申请时间", dataType = "String")
    private Date createTime;
    @ApiModelProperty(name = "description", notes = "问题描述", dataType = "String")
    private String description;
    @ApiModelProperty(name = "auditMessage", notes = "审核留言", dataType = "String")
    private String auditMessage;
    @ApiModelProperty(name = "receivingAddress", notes = "卖家地址", dataType = "String")
    private String receivingAddress;
    @ApiModelProperty(name = "contacts", notes = "联系人姓名", dataType = "String")
    private String contacts;
    @ApiModelProperty(name = "zipCode", notes = "联系人邮编", dataType = "String")
    private String zipCode;
    @ApiModelProperty(name = "refundPhone", notes = "售后联系电话", dataType = "String")
    private String refundPhone;
    @ApiModelProperty(name = "listRefund", notes = "审核流程", dataType = "List")
    private List<OrderRefundRecord> listRefund;
}
