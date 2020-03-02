package com.shopping.vo.product;

import com.baomidou.mybatisplus.annotations.TableField;
import com.shopping.entity.ProductAttribute;
import com.shopping.entity.ProductSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "商品详情")
public class ProductInfoVo implements Serializable {
    @ApiModelProperty(name = "productId", notes = "商品Id", dataType = "String")
    private String productId;
    @ApiModelProperty(name = "productName", notes = "商品名称", dataType = "String")
    private String productName;
    @ApiModelProperty(name = "oneCategoryId", notes = "一级分类Id", dataType = "String")
    private String oneCategoryId;
    @ApiModelProperty(name = "rmbPrice", notes = "人民币价格", dataType = "BigDecimal")
    private BigDecimal rmbPrice;
    @ApiModelProperty(name = "marketPrice", notes = "商品市场价", dataType = "BigDecimal")
    private BigDecimal marketPrice;
    @ApiModelProperty(name = "masterPicUrl", notes = "商品主图url", dataType = "String")
    private String masterPicUrl;
    @ApiModelProperty(name = "inventory", notes = "库存", dataType = "String")
    private Long inventory;
    @ApiModelProperty(name = "currencyId", notes = "币种id", dataType = "String")
    private String currencyId;
    @ApiModelProperty(name = "descript", notes = "商品描述", dataType = "String")
    private String descript;    //商品描述
    @ApiModelProperty(name = "currencyName", notes = "币种名称", dataType = "String")
    private String currencyName;
    @ApiModelProperty(name = "atts", notes = "商品属性", dataType = "Map")
    private Map<String, List<ProductAttribute>> atts;
    @ApiModelProperty(name = "spec", notes = "商品规格", dataType = "Map")
    private List<ProductSpec>  specs;

}