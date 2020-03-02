package com.shopping.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(value = "支付实体")
@Data
public class RechargeOrderVo {

    @ApiModelProperty(name="rechargeAmount",notes = "充值金额",dataType = "String",required = true,allowEmptyValue = false)
    @NotNull(message="充值金额")
    private BigDecimal rechargeAmount;
}
