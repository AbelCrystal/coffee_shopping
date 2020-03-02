package com.shopping.vo.user;

import com.shopping.entity.UserAccountDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "用户钱包资产")
public class MyAccountVo {
    @ApiModelProperty(name = "userId", notes = "用户iD", dataType = "String", required = true, allowEmptyValue = false)
    private String userId;
    @ApiModelProperty(name = "phone", notes = "手机号码", dataType = "String", required = true, allowEmptyValue = false)
    private String phone;
    @ApiModelProperty(name = "btcAssets", notes = "用户BTC资产", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal btcAssets;
    @ApiModelProperty(name = "rmbAssets", notes = "用户人民币资产", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal rmbAssets;
}
