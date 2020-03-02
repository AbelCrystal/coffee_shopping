package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户收货地址查询实体")
public class UserAddressVo implements Serializable {

    @ApiModelProperty(name="addressId",notes = "收货地址id",dataType = "String",required = true,allowEmptyValue = false)
    private String addressId;

    @ApiModelProperty(name="userName",notes="收件人姓名",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="收件人不能为空")
    private String userName;

    @ApiModelProperty(name="userPhone",notes = "收件人联系方式",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="收件人联系方式不能为空")
    //@Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$")
    private String userPhone;

    @ApiModelProperty(name="postcode",notes = "邮政编码",dataType = "String",required = false,allowEmptyValue = false)
    private String postcode;

    @ApiModelProperty(name="province",notes = "省份",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="省份不能为空")
    private String province;

    @ApiModelProperty(name="city",notes = "市",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="城市不能为空")
    private String city;

    @ApiModelProperty(name="district",notes = "区",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="区不能为空")
    private String district;

    @ApiModelProperty(name="detaildAddress",notes = "详细地址",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="详细地址不能为空")
    private String detaildAddress;

    @ApiModelProperty(name="isDefault",notes = "是否为默认地址 0否 1是 ",dataType = "String",required = true,allowEmptyValue = false)
    private String isDefault;
}
