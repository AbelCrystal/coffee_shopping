package com.shopping.vo.order;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "提交购物车订单实体")
@Data
public class CartOrdersV0 {
    @ApiModelProperty(name="orderSrc",notes = "订单来源:0:微信 1:支付宝",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="订单来源")
    private  String orderSrc;
    @ApiModelProperty(name="cartIdArray",notes = "购物车id",dataType = "List",required = true,allowEmptyValue = false)
    @NotNull(message="购物车id不能为空")
    private List<String> cartIdArray;
}
