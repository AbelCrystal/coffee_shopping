package com.shopping.vo.product;

import com.baomidou.mybatisplus.annotations.TableField;
import com.shopping.entity.ProductInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "商品分类列表")
public class ProductByCategoryVo {
    @ApiModelProperty(name = "categoryId",notes = "商品分类Id",dataType ="String")
    private String categoryId;

    @ApiModelProperty(name = "categoryName",notes = "商品分类名称",dataType ="String")
    private String categoryName;
    @ApiModelProperty(name = "productInfos",notes = "商品列表",dataType ="List")
    private List<ProductInfo> productInfos;
}
