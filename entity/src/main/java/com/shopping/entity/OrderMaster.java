package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@TableName("tb_order_master")
public class OrderMaster  implements Serializable {

    @TableField(value = "master_id",exist = true)
    @TableId(value = "master_id")
    private String masterId;         //订单id

    @TableField(value = "user_id",exist = true)
    private String userId;      //下单人id
    @TableField(value = "wx_uuid",exist = true)
    private String wxUUId;      //微信Id
    @TableField(value = "ali_uuid",exist = true)
    private String aliUUId;      //支付宝Id
    @TableField(value = "table_no",exist = true)
    private String TableNo;      //桌号
    @TableField(value = "user_address_id",exist = true)
    private String userAddressId;   //收货地址id

    @TableField(value = "currency_id",exist = true)
    private String currencyId;      //币id
    @TableField(exist = false)
    private String currencyName;

    @TableField(value = "supplier_id",exist = true)
    private String supplierId;  //供应商id

    @TableField(value = "supplier_name",exist = true)
    private String supplierName;
    @TableField(value = "total_money",exist = true)
    private BigDecimal totalMoney;      //订单商品总金额

    @TableField(value = "pay_type",exist = true)
    private String payType;     //支付方式  0商城币 1支付宝  2微信 默认0

    @TableField(value = "order_src",exist = true)
    private String ordersrc;       // 订单来源  0安卓 1苹果 2商城 3微信  默认 0

    @TableField(value = "district_money",exist = true)
    private BigDecimal districtMoney;   //优惠金额

    @TableField(value = "shipping_money",exist = true)
    private BigDecimal shippingMoney;   //运费

    @TableField(value = "payment_money",exist = true)
    private BigDecimal paymentMoney;    //支付金额  = 商品总金额 - 优惠金额  + 运费

    @TableField(value = "paycoin_amount",exist = true)
    private BigDecimal  paycoinAmount; //转换通证币  = 支付金额 * 换算比例

    @TableField(value = "paycoin_ratio",exist = true)
    private BigDecimal paycoinRatio;    //通证币兑换人民币比例

    @TableField(value = "shipping_comp_name",exist = true)
    private String shippingCompName;    //快递公司名称

    @TableField(value = "shipping_sn",exist = true)
    private String shippingSn;  //快递单号

    @TableField(value = "shipping_no",exist = true)
    private String shippingNo;  //快递码

    @TableField(value = "invoice_title",exist = true)
    private String invoiceTitle;  //发票抬头

    @TableField(value = "invoice_type",exist = true)
    private String invoiceType; //发票类型  0个人',

    @TableField(value = "invoice_content",exist = true)
    private String invoiceContent;  //发票内容

    @TableField(value = "settlement_status",exist = true)
    private String settlementStatus;        //  结算状态 0未结算 1已结算',

    @TableField(value = "settlement_time",exist = true)
    private Date settlementTime;        //结算时间

    @TableField(value = "order_status",exist = true)
    private String orderStatus; //0:待付款 1:待发货 2:待收货 3:申请退款 4:申请退货退款 5:部分退货中 6:已完成 7:交易关闭 8:交易成功',

    @TableField(value = "order_point",exist = true)
    private Integer orderPoint; //订单积分

    @TableField(value = "cancel_reason",exist = true)
    private String cancelReason;    //取消原因

    @TableField(value = "cancel_time",exist = true)
    private Date cancelTime;    //取消时间

    @TableField(value = "modified_time",exist = true)
    private Date modifiedTime;  //最后修改时间

    @TableField(value = "create_time",exist = true)
    private Date createTime;    //下单时间

    @TableField(value = "shipping_time",exist = true)
    private Date shippingTime;  //发货时间

    @TableField(value = "pay_time",exist = true)
    private Date payTime;       //支付时间

    @TableField(value = "receive_time",exist = true)
    private Date receiveTime;   //收货时间
    @TableField(value = "dinner_code",exist = true)
    private long dinnerCode;   //取餐码
    @TableField(value = "is_delete",exist = true)
    private String isDelete;    //是否删除  0正常 1删除  默认 0

    @Version
    private Integer version;

}