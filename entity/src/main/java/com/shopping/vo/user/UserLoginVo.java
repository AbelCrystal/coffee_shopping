package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户登录实体")
public class UserLoginVo implements Serializable {
    @ApiModelProperty(name = "phone",notes = "用户手机号",dataType = "String",required = true,allowEmptyValue = false)
    private String phone ;
    @ApiModelProperty(name = "smsCode",notes = "短信验证码",dataType = "String",required = true,allowEmptyValue = false)
    private String smsCode ;
}
