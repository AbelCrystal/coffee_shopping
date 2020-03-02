package com.shopping.vo.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "商品列表返回")
public class BaseListResult<T> implements Serializable {
    @ApiModelProperty(name = "total",notes = "总笔数",dataType ="Long")
    private int total;
    @ApiModelProperty(name = "listProduct",notes = "商品列表",dataType ="List")
    private T listProduct;
    @ApiModelProperty(name = "pageNum",notes = "第几页",dataType ="int")
    private int pageNum;
}
