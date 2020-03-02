package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "版本更新公告实体")
public class VersionVo implements Serializable {

    @ApiModelProperty(name="versionId",notes = "版本更新id",dataType = "String",required = true,allowEmptyValue = false)
    private String versionId;

    @ApiModelProperty(name="versionTitle",notes="版本更新公告标题",dataType = "String",required = true,allowEmptyValue = false)
    private String versionTitle;

    @ApiModelProperty(name="content",notes = "版本更新内容",dataType = "String",required = true,allowEmptyValue = false)
    private String content;
    @ApiModelProperty(name="createTime",notes = "创建时间",dataType = "String",required = true,allowEmptyValue = false)
    private Date createTime;    //发布时间
}
