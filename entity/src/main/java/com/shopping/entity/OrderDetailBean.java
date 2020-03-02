package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@ApiModel(value = "订单查询实体详情")
@Data
@TableName("tb_order_detail")
public class OrderDetailBean implements Serializable {

    @TableField(value = "detail_id", exist = true)
    @TableId(value = "detail_id")
    @ApiModelProperty(name = "detailId", notes = "订单详情Id", dataType = "String")
    private String detailId;         //详情id

    @TableField(value = "master_id")
    @ApiModelProperty(name = "masterId", notes = "主订单id", dataType = "String")
    private String masterId;        //主订单id

    @TableField(value = "product_id")
    @ApiModelProperty(name = "productId", notes = "商品id", dataType = "String")
    private String productId;       //商品id

    @TableField(value = "product_name")
    @ApiModelProperty(name = "productName", notes = "商品名称", dataType = "String")
    private String productName;  //商品名称

    @TableField(value = "product_img")
    @ApiModelProperty(name = "productImg", notes = "商品图片", dataType = "String")
    private String productImg;  //商品图片

    @TableField(value = "rmb_price")
    @ApiModelProperty(name = "rmbPrice", notes = "人民币单价", dataType = "BigDecimal")
    private BigDecimal rmbPrice;    //人民币单价

    @TableField(value = "cost_price")
    @ApiModelProperty(name = "costPrice", notes = "商品成本价", dataType = "BigDecimal")
    private BigDecimal costPrice;    //商品成本价

    @TableField(value = "coin_price")
    @ApiModelProperty(name = "coinPrice", notes = "支付币单价", dataType = "BigDecimal")
    private BigDecimal coinPrice;   //支付币单价

    @TableField(value = "currency_id")
    @ApiModelProperty(name = "currencyId", notes = "币id", dataType = "String")
    private String currencyId;   //币id

    @TableField(value = "coin_rmb_ratio")
    @ApiModelProperty(name = "coinRmbRatio", notes = "通证换算人民币比例", dataType = "BigDecimal")
    private BigDecimal coinRmbRatio;    //通证换算人民币比例

    @TableField(value = "product_num")
    @ApiModelProperty(name = "productNum", notes = "商品数量", dataType = "int")
    private int productNum;     //商品数量

    @TableField(value = "product_attr")
    @ApiModelProperty(name = "productAttr", notes = "商品属性", dataType = "String")
    private String productAttr;     //商品数量

    @TableField(value = "create_time")
    private Date createTime;    //创建时间

    @TableField(value = "update_time")
    private Date updateTime;    //修改时间
}