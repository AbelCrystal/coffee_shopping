package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@ApiModel(value = "忘记密码实体")
public class ForgetPasswordVo implements Serializable {

    @ApiModelProperty(name = "phone",notes = "手机号码",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="手机号码不能为空")
    private String phone;
    @ApiModelProperty(name = "password",notes = "用户密码",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="用户密码不能为空")
    private String password;;
    @ApiModelProperty(name = "smsCode",notes = "手机验证码",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="手机验证码不能为空")
    private String smsCode;
}
