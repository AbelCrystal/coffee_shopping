package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_pay_money")
@ApiModel(value = "支付金额")
public class PayMoney {
    @ApiModelProperty(name = "id",notes = "id",dataType = "String",required = true,allowEmptyValue = false)
    @TableId
    private String id;
    @ApiModelProperty(name = "money",notes = "标题",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal money;



}