package com.shopping.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ApiModel(value = "虚拟币支付实体")
@Data
public class VirtualCurrencyVo {

    @ApiModelProperty(name="orderId",notes = "订单id",dataType = "String",required = true,allowEmptyValue = false)
    @NotNull(message="订单id")
    private String orderId;
    @ApiModelProperty(name="currencyId",notes = "虚拟币Id",dataType = "String",required = true,allowEmptyValue = false)
    @NotNull(message="币Id")
    private String currencyId;
}
