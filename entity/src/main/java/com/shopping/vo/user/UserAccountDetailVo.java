package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "资金明细列表")
public class UserAccountDetailVo {
    @ApiModelProperty(name = "id", notes = "明细id", dataType = "String", required = true, allowEmptyValue = false)
    private String id;
    @ApiModelProperty(name = "currencyId", notes = "币种id", dataType = "String", required = true, allowEmptyValue = false)
    private String currencyId;
    @ApiModelProperty(name = "userId", notes = "用户id", dataType = "String", required = true, allowEmptyValue = false)
    private String userId;
    @ApiModelProperty(name = "amount", notes = "操作金额", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal amount;
    @ApiModelProperty(name = "changesSourceId", notes = "变更来源id", dataType = "String", required = true, allowEmptyValue = false)
    private String changesSourceId;
    @ApiModelProperty(name = "changesType", notes = "类型：0支出  1收入 2充值 3转币,4冻结，5解冻，6分润", dataType = "String", required = true, allowEmptyValue = false)
    private String changesType;
    @ApiModelProperty(name = "updateTime", notes = "修改时间", dataType = "Date", required = true, allowEmptyValue = false)
    private Date updateTime;
    @ApiModelProperty(name = "createTime", notes = "创建时间", dataType = "Date", required = true, allowEmptyValue = false)
    private Date createTime;
    @ApiModelProperty(name = "flag", notes = "收入支出", dataType = "String", required = true, allowEmptyValue = false)
    private String flag;
    @ApiModelProperty(name = "payType", notes = "支付类型：支付方式0商城币，1微信，2支付宝", dataType = "String", required = true, allowEmptyValue = false)
    private String payType;

}
