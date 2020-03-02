package com.shopping.vo.order;

import com.shopping.entity.Advertisement;
import com.shopping.entity.Notice;
import com.shopping.entity.OrderDetailBean;
import com.shopping.vo.supplier.SupplierInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单查询实体")
@Data
public class OrderVo implements Serializable {

    @ApiModelProperty(name = "masterId", notes = "订单id", dataType = "String")
    private String masterId;
    @ApiModelProperty(name = "userId", notes = "用户Id", dataType = "String")
    private String userId;
    @ApiModelProperty(name = "supplierName", notes = "供应商名称", dataType = "String")
    private String supplierName;
    @ApiModelProperty(name = "currencyId", notes = "币种Id", dataType = "String")
    private String currencyId;
    @ApiModelProperty(name = "tableNo", notes = "桌号", dataType = "String")
    private String tableNo;
    @ApiModelProperty(name = "dinnerCode", notes = "取餐码", dataType = "String")

    private long dinnerCode;

    @ApiModelProperty(name = "currencyName", notes = "币种名称", dataType = "String")
    private String currencyName;
    @ApiModelProperty(name = "paymentMoney", notes = "订单实际金额", dataType = "BigDecimal")
    private BigDecimal paymentMoney;
    @ApiModelProperty(name = "orderStatus", notes = " 0:待付款 1:已支付 2:已出单 3：交易关闭'", dataType = "String")
    private String orderStatus;
    @ApiModelProperty(name = "payType", notes = "支付方式:0商城币 1 支付宝 2 微信,3用户余额", dataType = "String")
    private String payType;
    @ApiModelProperty(name = "paycoinAmount", notes = "通证数量", dataType = "BigDecimal")
    private BigDecimal paycoinAmount;
    @ApiModelProperty(name = "shippingMoney", notes = "运费", dataType = "BigDecimal")
    private BigDecimal shippingMoney;
    @ApiModelProperty(name = "paycoinRatio", notes = "通证币兑换人民币比例", dataType = "BigDecimal")
    private BigDecimal paycoinRatio;    //
    @ApiModelProperty(name = "districtMoney", notes = "优惠金额", dataType = "BigDecimal")
    private BigDecimal districtMoney;   //优惠金额
    @ApiModelProperty(name = "supplierId", notes = "供应商ID", dataType = "String")
    private String supplierId;

    /**
     * 微信用户Id
     */
    @ApiModelProperty(name = "wx_uuid", notes = "微信uuid", dataType = "String")
    private String wxUUId;
    /**
     * 支付宝用户Id
     */
    @ApiModelProperty(name = "ali_uuid", notes = "支付宝uuid", dataType = "String")
    private String aliUUId;
    @ApiModelProperty(name = "orderDetails", notes = "订单详情", dataType = "List")
    private  List<OrderDetailBean> orderDetails;
    @ApiModelProperty(name = "createTime", notes = "订单创建时间", dataType = "String")
    private String createTime;



}
