package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "资金明细")
public class UserAccountCollectVo {
    @ApiModelProperty(name = "amount", notes = "获得", dataType = "double", required = true, allowEmptyValue = false)
    private double amount;
    @ApiModelProperty(name = "amountOut", notes = "使用", dataType = "double", required = true, allowEmptyValue = false)
    private String time;
    @ApiModelProperty(name = "userAccountDetailList", notes = "资金明细列表", dataType = "List", required = true, allowEmptyValue = false)
    private List<UserAccountDetailVo> userAccountDetailList;

}
