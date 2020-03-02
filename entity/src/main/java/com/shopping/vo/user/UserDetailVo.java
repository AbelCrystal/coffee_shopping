package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "个人信息详情")
public class UserDetailVo {
//    @ApiModelProperty(name = "id",notes = "用户id",dataType = "String",required = false)
//    private String id ;
    @ApiModelProperty(name = "sex", notes = "性别", dataType = "String", required = true, allowEmptyValue = false)
    private String sex;
    @ApiModelProperty(name = "userImgUrl", notes = "头像", dataType = "String", required = true, allowEmptyValue = false)
    private String userImgUrl;
    @ApiModelProperty(name = "birthday", notes = "生日", dataType = "Date", required = true, allowEmptyValue = false)
    private String birthday;
    @ApiModelProperty(name = "profession", notes = "职业", dataType = "String", required = true, allowEmptyValue = false)
    private String profession;

}
