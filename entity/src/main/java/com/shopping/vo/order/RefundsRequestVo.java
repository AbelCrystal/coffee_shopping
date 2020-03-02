package com.shopping.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@Data
@ApiModel(value = "退货退款实体")
public class RefundsRequestVo implements Serializable {
    @ApiModelProperty(name="orderId",notes = "订单号",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="订单号不能为空")
    private  String orderId;
    @ApiModelProperty(name="detailId",notes = "子订单号",dataType = "String",required = false,allowEmptyValue = true)
    private String detailId;
    @ApiModelProperty(name="refundReason",notes = "退款退货原因",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="退款退货原因不能为空")
    private String refundReason;
    @ApiModelProperty(name="description",notes = "退款描述",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="退款描述不能为空")
    private String description;
    @ApiModelProperty(name="contacts",notes = "联系人",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="联系人不能为空")
    private String contacts;
    @ApiModelProperty(name="phone",notes = "联系电话",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="联系电话不能为空")
    private String phone;
    @ApiModelProperty(name="backMoney",notes = "退款金额",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    @NotNull(message="退款金额不能为空")
    private BigDecimal backMoney;
    @ApiModelProperty(name="supplierId",notes = "供应商Id",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="供应商Id不能为空")
    private String supplierId;
    @ApiModelProperty(name="payType",notes = "支付方式 0商城币 1 支付宝 2 微信",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="支付方式不能为空")
    private String payType;
    @ApiModelProperty(name="refundType",notes = "售后类型：0为退款，1为退货退款",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="售后类型不能为空")
    private String refundType;
    @ApiModelProperty(name="picList",notes = "图片地址",dataType = "List",required = false,allowEmptyValue = true)
    private  List<String> picList;
}
