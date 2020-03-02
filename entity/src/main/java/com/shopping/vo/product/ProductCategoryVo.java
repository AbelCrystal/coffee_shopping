package com.shopping.vo.product;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "商品分类")
public class ProductCategoryVo implements Serializable {

    @ApiModelProperty(name = "categoryId",notes = "商品分类Id",dataType ="String")
    @TableField(value = "category_id",exist = true)
    private String categoryId;

    @ApiModelProperty(name = "categoryName",notes = "商品分类名称",dataType ="String")
    @TableField(value = "category_name",exist = true)
    private String categoryName;

    @ApiModelProperty(name = "imgUrl",notes = "分类图片路劲",dataType ="String")
    private String imgUrl;

    @ApiModelProperty(name = "childCategory",notes = "子分类集合",dataType ="String")
    private List<ProductCategoryVo> childCategory;

}