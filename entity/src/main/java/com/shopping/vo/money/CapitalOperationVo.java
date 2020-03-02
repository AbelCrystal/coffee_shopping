package com.shopping.vo.money;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 充提币记录
 *@author:Darryl_Tang
 *@Time:2019/7/3 14:42
 *
 */
@ApiModel(value = "充提币记录查询实体")
@Data
public class CapitalOperationVo implements Serializable {

    @ApiModelProperty(name="id",notes = "记录id",dataType = "String",required = true,allowEmptyValue = false)
    private String id;

    @ApiModelProperty(name="userId",notes="用户id",dataType = "String",required = true,allowEmptyValue = false)
    private String userId;

    @ApiModelProperty(name="currencyId",notes = "币种id",dataType = "String",required = true,allowEmptyValue = false)
    private String currencyId;

    @ApiModelProperty(name="amount",notes = "操作数量",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal amount;

    @ApiModelProperty(name="feeCoinId",notes = "手续费币id",dataType = "String",required = true,allowEmptyValue = false)
    private String feeCoinId;

    @ApiModelProperty(name="fees",notes = "手续费数量",dataType = "BigDecimal",required = true,allowEmptyValue = false)
    private BigDecimal fees;

    @ApiModelProperty(name="type",notes = "记录类型 0 充值 1 提现",dataType = "String",required = true,allowEmptyValue = false)
    private String type;

    @ApiModelProperty(name="status",notes = "记录状态 充值 0/项确认 1/项确认 2/项确认 3 充值成功 提现 0 等待提现 1 正在处理 2 提现中 3 提现成功 4 提现失败 5 用户撤销",dataType = "String",required = true,allowEmptyValue = false)
    private String status;

    @ApiModelProperty(name="address",notes = "充提币地址",dataType = "String",required = true,allowEmptyValue = false)
    private String address;

    @ApiModelProperty(name="confirms",notes = "确认数",dataType = "Integer",required = true,allowEmptyValue = false)
    private Integer confirms;

    @ApiModelProperty(name="createTime",notes = "创建时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date createTime;

    @ApiModelProperty(name="updateTime",notes = "修改时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date updateTime;
}
