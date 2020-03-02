package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "修改手机实体")
public class UpdatePhoneVo implements Serializable {
    @ApiModelProperty(name = "oldPhone",notes = "旧手机号码",dataType = "String",required = true,allowEmptyValue = false)
    private String oldPhone;
    @ApiModelProperty(name = "phone",notes = "新手机号码",dataType = "String",required = true,allowEmptyValue = false)
    private String phone;
    @ApiModelProperty(name = "smsCode",notes = "新手机验证码",dataType = "String",required = true,allowEmptyValue = false)
    private String smsCode;
}
