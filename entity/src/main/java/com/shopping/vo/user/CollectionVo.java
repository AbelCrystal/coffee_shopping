package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 收藏夹
 *@author:Darryl_Tang
 *@Time:2019/6/25 20:51
 *
 */
@ApiModel(value = "收藏夹查询实体")
@Data
public class CollectionVo implements Serializable {

    @ApiModelProperty(name="collectionId",notes = "收藏夹id",dataType = "String",required = true,allowEmptyValue = false)
    private String collectionId;

    @ApiModelProperty(name="userId",notes="用户id",dataType = "String",required = true,allowEmptyValue = false)
    private String userId;

    @ApiModelProperty(name="productId",notes = "商品id",dataType = "String",required = true,allowEmptyValue = false)
    private String productId;

    @ApiModelProperty(name="isDelete",notes = "是否删除",dataType = "String",required = true,allowEmptyValue = false)
    private String isDelete;

    @ApiModelProperty(name="createTime",notes = "收藏时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date createTime;

    @ApiModelProperty(name="updateTime",notes = "删除时间",dataType = "Date",required = true,allowEmptyValue = false)
    private Date updateTime;
}
