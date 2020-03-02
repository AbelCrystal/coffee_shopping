package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@TableName("tb_product_info")
public class ProductInfo implements Serializable {
    @TableField(value = "product_id",exist = true)
    @TableId(value = "product_id")
    private String productId;       //商品id

    @TableField(value = "product_core",exist = true)
    private String productCore;     //商品编码

    @TableField(value = "product_name",exist = true)
    private String productName;     //商品名称

    @TableField(value = "bar_code",exist = true)
    private String barCode;         //国条码

    @TableField(value = "brand_id",exist = true)
    private String brandId;         //品牌id

    @TableField(value = "one_category_id",exist = true)
    private String oneCategoryId;   //一级分类id

    @TableField(value = "two_category_id",exist = true)
    private String twoCategoryId;   //二级分类id

    @TableField(value = "three_category_id",exist = true)
    private String threeCategoryId; //三级分类id

    @TableField(value = "supplier_id",exist = true)
    private String supplierId;      //供应商id

    @TableField(value = "supplier_name",exist = true)
    private String supplierName;    //供应商名称

    @TableField(value = "magnification")
    private BigDecimal magnification;   //商品出售价和结算价倍率

    @TableField(value = "rmb_price")
    private BigDecimal rmbPrice;    //商品销售价

    @TableField(value = "market_price")
    private BigDecimal marketPrice;     //商品市场价

    @TableField(value = "cost_price")
    private BigDecimal costPrice;       //商品成本价

    @TableField(value = "publish_status",exist = true)
    private String publishStatus;       //商品上下架状态 0下架 1上架

    @TableField(value = "audit_status",exist = true)
    private String auditStatus; //商品审核状态    0未审核  1已审核

    @TableField(value = "weight",exist = true)
    private BigDecimal weight;  //商品重量

    @TableField(value = "plength",exist = true)
    private BigDecimal plength; //商品长度

    @TableField(value = "height",exist = true)
    private BigDecimal height;  //商品高度

    @TableField(value = "width",exist = true)
    private BigDecimal width;   //商品宽度

    @TableField(value = "master_pic_url",exist = true)
    private String masterPicUrl;    //商品主图

    @TableField(value = "shelf_type",exist = true)
    private String shelfType;   //年月日
    @TableField(value = "introduction",exist = true)
    private String introduction;   //年月日
    @TableField(value = "platform_type",exist = true)
    private String platformType;    //平台类型 0 自营 1其他商户  默认 0

    @TableField(value = "inventory",exist = true)
    private Long inventory;     //库存 默认0

    @TableField(value = "sales_volume",exist = true)
    private Long salesVolume;   //销量

    @TableField(value = "drawing_rate",exist = true)
    private BigDecimal drawingRate; //商家费率

    @TableField(value = "descript",exist = true)
    private String descript;    //商品描述

    @TableField(value = "shelf_life",exist = true)
    private Integer shelfLife;  //商品有效期

    @TableField(value = "advertising_slogans",exist = true)
    private String advertisingSlogans;  //广告语

    @TableField(value = "production_date",exist = true)
    private Date productionDate;    //生产日期

    @TableField(value = "currency_id",exist = true)
    private String currencyId;      //币种id
    @TableField(exist = false)
    private String currencyName;      //币种名称

    @TableField(value = "logistics_price",exist = true)
    private BigDecimal logisticsPrice;  //物流价格

    @TableField(value = "shipping_name",exist = true)
    private String shippingName;    //物流公司名称

    @TableField(value = "free_shipping",exist = true)
    private String freeShipping;    //是否包邮 0为包邮 1为不包邮

    @TableField(value = "support_returns",exist = true)
    private String supportReturns;    //是否支持退货  0为支持，1为不支持

    @TableField(value = "after_sale",exist = true)
    private int afterSale;    //售后期限

    @TableField(value = "modified_time",exist = true)
    private Date modifiedTime;  //最后修改时间

    @TableField(value = "create_time",exist = true)
    private Date createTime;    //商品录入时间

    @Version
    private Integer version;
}