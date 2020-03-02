package com.shopping.vo.order;

import com.shopping.entity.OrderDetailBean;
import com.shopping.entity.OrderRefundRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "退款返回实体")
@Data
public class RefundsRecordVo {
    @ApiModelProperty(name = "refundTradeNo", notes = "退款单号", dataType = "String")
    private String refundTradeNo;
    @ApiModelProperty(name = "orderDetailBeans", notes = "订单详情", dataType = "List")
    private List<OrderDetailBean> orderDetailBeans;
    @ApiModelProperty(name = "refundStatus", notes = "售后状态：0申请，1审核通过，2审核不通过，3售后收货，4进行退款，5处理完成", dataType = "String")
    private String refundStatus;
    @ApiModelProperty(name = "createTime", notes = "申请时间", dataType = "String")
    private Date createTime;
    @ApiModelProperty(name = "supplierId", notes = "供应商ID", dataType = "String")
    private String supplierId;
}
