package com.shopping.vo.money;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 提币地址
 *@author:Darryl_Tang
 *@Time:2019/7/6 16:32
 *
 */
@ApiModel(value = "提币地址查询实体")
@Data
public class WithdrawAddressVo implements Serializable {

    @ApiModelProperty(name="withdrawId",notes = "提币地址id",dataType = "String",required = true,allowEmptyValue = false)
    private String withdrawId;

    @ApiModelProperty(name="userId",notes="用户id",dataType = "String",required = true,allowEmptyValue = false)
    private String userId;

    @ApiModelProperty(name="currencyId",notes = "币种id",dataType = "String",required = true,allowEmptyValue = false)
    private String currencyId;

    @ApiModelProperty(name="label",notes = "备注",dataType = "String",required = true,allowEmptyValue = false)
    private String label;

    @ApiModelProperty(name="isDefault",notes = "是否默认 0 否 1默认",dataType = "String",required = true,allowEmptyValue = false)
    private String isDefault;

    @ApiModelProperty(name="address",notes = "提币地址",dataType = "String",required = true,allowEmptyValue = false)
    private String address;
}
