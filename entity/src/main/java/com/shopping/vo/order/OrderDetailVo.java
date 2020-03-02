package com.shopping.vo.order;

import com.shopping.entity.OrderDetailBean;
import com.shopping.vo.user.UserAddressVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单查询详情实体")
@Data
public class OrderDetailVo implements Serializable {
    @ApiModelProperty(name = "masterId", notes = "订单id", dataType = "String")
    private String masterId;         //订单id
    @ApiModelProperty(name = "userId", notes = "下单人id", dataType = "String")
    private String userId;      //下单人id
    @ApiModelProperty(name = "wxUUId", notes = "微信Id", dataType = "String")
    private String wxUUId;      //微信Id
    @ApiModelProperty(name = "aliUUId", notes = "支付宝UUid", dataType = "String")
    private String aliUUId;      //支付宝Id
    @ApiModelProperty(name = "TableNo", notes = "桌号", dataType = "String")
    private int TableNo;      //桌号
    @ApiModelProperty(name = "currencyId", notes = "币id", dataType = "String")
    private String currencyId;      //币id
    @ApiModelProperty(name = "currencyName", notes = "币种名称", dataType = "String")
    private String currencyName;      //币id
    @ApiModelProperty(name = "supplierId", notes = "供应商id", dataType = "String")
    private String supplierId;  //供应商id
    @ApiModelProperty(name = "supplierName", notes = "供应商名称", dataType = "String")
    private String supplierName;    //供应商名称
    @ApiModelProperty(name = "totalMoney", notes = "订单商品总金额", dataType = "BigDecimal")
    private BigDecimal totalMoney;      //订单商品总金额
    @ApiModelProperty(name = "payType", notes = "支付方式  0 KEO ,1支付宝  2微信 ，3用户余额,4BTC,5ETH", dataType = "String")
    private String payType;     //支付方式  0商城币 1支付宝  2微信 默认0
    @ApiModelProperty(name = "ordersrc", notes = "订单来源  0微信，1支付宝", dataType = "String")
    private String ordersrc;       // 订单来源 0微信，1支付宝
    @ApiModelProperty(name = "paymentMoney", notes = "支付金额  = 商品总金额 - 优惠金额  + 运费", dataType = "String")
    private BigDecimal paymentMoney;    //支付金额  = 商品总金额 - 优惠金额  + 运费
    @ApiModelProperty(name = "paycoinAmount", notes = "支付金额 * 换算比例", dataType = "BigDecimal")
    private BigDecimal paycoinAmount; //转换通证币  = 支付金额 * 换算比例
    @ApiModelProperty(name = "paycoinRatio", notes = "通证币兑换人民币比例", dataType = "BigDecimal")
    private BigDecimal paycoinRatio;    //通证币兑换人民币比例
    @ApiModelProperty(name = "orderStatus", notes = "订单状态： 0:待付款 1:已支付 2:已出单 3：交易关闭", dataType = "String")
    private String orderStatus; // 0:待付款 1:待发货 2:待收货 3:申请退款
    @ApiModelProperty(name = "payTime", notes = "支付时间", dataType = "String")
    private Date payTime;       //支付时间
    @ApiModelProperty(name = "orderDetailBeanList", notes = "订单列表", dataType = "List")
    private List<OrderDetailBean> orderDetailBeanList;
    @ApiModelProperty(name = "orderTimeOut", notes = "订单超时时间", dataType = "Long")
    private long orderTimeOut;
    @ApiModelProperty(name = "createTime", notes = "订单提交时间", dataType = "String")
    private Date createTime;    //下单时间
    @ApiModelProperty(name = "dinnerCode", notes = "订单提交时间", dataType = "Long")
    private long dinnerCode;   //取餐码

}
