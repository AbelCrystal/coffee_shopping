package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(value = "用户资产查询实体")
public class UserAccountVo implements Serializable {

    @ApiModelProperty(name="id",notes = "id",dataType = "String",required = true,allowEmptyValue = false)
    private String id;

    @ApiModelProperty(name="userId",notes = "用户id",dataType = "String",required = true,allowEmptyValue = false)
    private String userId;

    @ApiModelProperty(name="currencyId",notes = "币种id",dataType = "String",required = true,allowEmptyValue = false)
    private String currencyId;

    @ApiModelProperty(name="amount",notes = "账户总资产",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal amount;

    @ApiModelProperty(name="usable",notes = "可用资产",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal usable;

    @ApiModelProperty(name="frozen",notes = "冻结资产",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal frozen;
}
