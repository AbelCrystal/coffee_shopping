package com.shopping.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "列表")
public class BaseListResult<T> implements Serializable {
    @ApiModelProperty(name = "total",notes = "总笔数",dataType ="Long")
    private long total;
    @ApiModelProperty(name = "listReult",dataType ="List")
    private T listReult;
    @ApiModelProperty(name = "pageNum",notes = "第几页",dataType ="int")
    private int pageNum;
    @ApiModelProperty(name = "outType",notes = "返回数据类型1：商品 2：商家",dataType ="int")
    private int outType;
}
