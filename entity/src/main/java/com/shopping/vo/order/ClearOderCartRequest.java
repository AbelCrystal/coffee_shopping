package com.shopping.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
@ApiModel(value = "删除购物车实体")
@Data
public class ClearOderCartRequest {
    @ApiModelProperty(name = "orderCartList", notes = "购物车id列表", dataType = "List")
    private List<String> orderCartList;
}
