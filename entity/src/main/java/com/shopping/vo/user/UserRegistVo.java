package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户注册实体")
public class UserRegistVo implements Serializable {

    @ApiModelProperty(name = "phone",notes = "手机号码",dataType = "String",required = true,allowEmptyValue = false)
    private String phone;
    @ApiModelProperty(name = "password",notes = "用户密码",dataType = "String",required = true,allowEmptyValue = false)
    private String password;
    @ApiModelProperty(name = "payPassword",notes = "支付密码",dataType = "String",required = true,allowEmptyValue = false)
    private String payPassword;
    @ApiModelProperty(name = "inviter_id",notes = "邀请人",dataType = "String",required = false,allowEmptyValue = false)
    private String inviterId;
    @ApiModelProperty(name = "smsCode",notes = "手机验证码",dataType = "String",required = true,allowEmptyValue = false)
    private String smsCode;
}
