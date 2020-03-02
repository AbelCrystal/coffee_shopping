package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "修改密码")
public class UserInfo {
    @ApiModelProperty(name = "phone", notes = "用户手机号", dataType = "String", required = true, allowEmptyValue = false)
    private String phone;
    @ApiModelProperty(name = "oldPassWord", notes = "旧密码", dataType = "String", required = true, allowEmptyValue = false)
    private String oldPassWord;
    @ApiModelProperty(name = "newPassWord", notes = "新密码", dataType = "String", required = true, allowEmptyValue = false)
    private String newPassWord;

}
