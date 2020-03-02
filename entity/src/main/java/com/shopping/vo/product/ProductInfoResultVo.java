package com.shopping.vo.product;

import com.shopping.entity.ProductAttribute;
import com.shopping.entity.ProductCategory;
import com.shopping.entity.ProductSpec;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "首页商品查询")
public class ProductInfoResultVo implements Serializable {
    @ApiModelProperty(name = "productCategories",notes = "商品属性列表",dataType ="List")
    private  List<ProductCategory> productCategories;
    @ApiModelProperty(name = "listProduct",notes = "商品列表",dataType ="List")
    private  List<ProductByCategoryVo> listProduct;
}