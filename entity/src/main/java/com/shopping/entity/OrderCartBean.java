package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 购物车表
 *@author:Darryl_Tang
 *@Time:2019/6/25 0025
 *
 */

@Data
@TableName("tb_order_cart")
public class OrderCartBean implements Serializable {

    @TableId(value = "cart_id")
    private String cartId;      //购物车id

    @TableField(value = "customer_id")
    private String userId;      //用户id

    @TableField(value = "wx_uuid")
    private String wxUUId;      //微信Id

    @TableField(value = "ali_uuid")
    private String aliUUId;      //支付宝Id
    @TableField(value = "product_id")
    private String productId;   //商品id

    @TableField(value = "supplier_id")
    private String supplierId;  //供应商id

    @TableField(value = "product_attr")
    private String productAttr;      //商品属性

    @TableField(value = "product_name")
    private String productName; //商品名称

    @TableField(value = "product_count")
    private int productCount;  //加入购物车数量

    @TableField(value = "rmb_price")
    private BigDecimal rmbPrice;    //商品人民币单价
    @TableField(value = "postage")
    private BigDecimal postage; //邮费
    @TableField(value = "product_image")
    private String productImage;    //商品主图

    @TableField(value = "create_time")
    private Date createTime;     //加入购物车时间

    @TableField(value = "update_time")
    private Date updateTime;    //修改或删除时间

    @TableField(value = "is_delete")
    private String isDelete;    //是否删除 0正常，1为删除
}
