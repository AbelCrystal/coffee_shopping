package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 购物车
 *@author:Darryl_Tang
 *@Time:2019/6/25 16:42
 *
 */
@ApiModel(value = "购物车查询实体")
@Data
public class OrderCartVo implements Serializable {

    @ApiModelProperty(name="cartId",notes = "购物车id",dataType = "String",required = true,allowEmptyValue = false)
    private String cartId;

    @ApiModelProperty(name="userId",notes="用户id",dataType = "String",required = true,allowEmptyValue = false)
    private String userId;

    @ApiModelProperty(name="productId",notes = "商品id",dataType = "String",required = true,allowEmptyValue = false)
    private String productId;

    @ApiModelProperty(name="supplierId",notes = "供应商id",dataType = "String",required = true,allowEmptyValue = false)
    private String supplierId;

    @ApiModelProperty(name="productName",notes = "商品名称",dataType = "String",required = true,allowEmptyValue = false)
    private String productName;

    @ApiModelProperty(name="productCount",notes = "加入购物车数量",dataType = "int",required = true,allowEmptyValue = false)
    private int productCount;

    @ApiModelProperty(name="productImage",notes = "商品主图",dataType = "String",required = true,allowEmptyValue = false)
    private String productImage;
    @ApiModelProperty(name="productAttr",notes = "商品属性",dataType = "String",required = true,allowEmptyValue = false)
    private String productAttr;


    @ApiModelProperty(name="rmbPrice",notes = "商品人民币价格",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal rmbPrice;
    @ApiModelProperty(name="createTime",notes = "创建时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date createTime;

    @ApiModelProperty(name="updateTime",notes = "修改时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date updateTime;
}
