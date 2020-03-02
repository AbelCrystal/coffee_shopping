package com.shopping.vo.user;

import com.shopping.entity.UserAccountDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "用户钱包资产明细实体")
public class MyAccountDetailVo {

    @ApiModelProperty(name = "account", notes = "用户资产", dataType = "BigDecimal", required = true, allowEmptyValue = false)
    private BigDecimal account;
    @ApiModelProperty(name = "total",notes = "总笔数",dataType ="Long")
    private long total;
    @ApiModelProperty(name = "listReult",dataType ="List")
    private List<UserAccountDetail> listReult;
    @ApiModelProperty(name = "pageNum",notes = "第几页",dataType ="int")
    private int pageNum;
}
