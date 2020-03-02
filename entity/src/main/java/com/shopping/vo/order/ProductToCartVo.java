package com.shopping.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@ApiModel(value = "购物车实体")
@Data
public class ProductToCartVo {
    @ApiModelProperty(name="productId",notes = "商品ID",dataType = "String",required = true,allowEmptyValue = false)
    private  String productId;
    @ApiModelProperty(name="productCount",notes = "商品数量",dataType = "int",required = true,allowEmptyValue = false)
    private int   productCount;
    @ApiModelProperty(name="productAttrs",notes = "商品属性",dataType = "String",required = true,allowEmptyValue = false)
    private String   productAttrs;
    @ApiModelProperty(name="rmbPrice",notes = "商品价格", dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal  rmbPrice;
}
