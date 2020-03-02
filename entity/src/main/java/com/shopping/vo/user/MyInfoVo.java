package com.shopping.vo.user;

import com.shopping.entity.UserAccountDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "个人中心实体")
public class MyInfoVo {
    @ApiModelProperty(name = "userId", notes = "用户iD", dataType = "String", required = true, allowEmptyValue = false)
    private String userId;
    @ApiModelProperty(name = "phone", notes = "手机号码", dataType = "String", required = true, allowEmptyValue = false)
    private String phone;
    @ApiModelProperty(name = "assets", notes = "用户资产", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal assets;
    @ApiModelProperty(name = "rmbAssets", notes = "用户人民币资产", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal rmbAssets;
    @ApiModelProperty(name = "currencyName", notes = "币种名称", dataType = "String", required = true, allowEmptyValue = false)
    private String currencyName;
    @ApiModelProperty(name = "userAccountDetails", notes = "资金明细", dataType = "List", required = true, allowEmptyValue = false)
    private List<UserAccountDetail> userAccountDetails;
    @ApiModelProperty(name = "total",notes = "总笔数",dataType ="Long")
    private long total;
}
