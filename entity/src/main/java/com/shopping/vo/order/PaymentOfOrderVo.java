package com.shopping.vo.order;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "余额通证支付实体")
@Data
public class PaymentOfOrderVo {
    @ApiModelProperty(name="orderId",notes = "订单id",dataType = "String",required = true,allowEmptyValue = false)
    @NotNull(message="订单id")
    private String orderId;
}
