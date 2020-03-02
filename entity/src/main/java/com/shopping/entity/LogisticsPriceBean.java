package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "供应商物流价查询实体")
@Data
@TableName("tb_logistics_price")
public class LogisticsPriceBean implements Serializable {

    @TableField(value = "id", exist = true)
    @TableId(value = "id")
    @ApiModelProperty(name = "id", notes = "id", dataType = "String")
    private String id;         //id

    @TableField(value = "supplier_id")
    @ApiModelProperty(name = "supplierId", notes = "供应商id", dataType = "String")
    private String supplierId;        //供应商id

    @TableField(value = "ship_id")
    @ApiModelProperty(name = "shipId", notes = "物流id", dataType = "String")
    private String shipId;       //物流id

    @TableField(value = "price")
    @ApiModelProperty(name = "price", notes = "物流价", dataType = "BigDecimal")
    private BigDecimal price;    //物流价

    @TableField(value = "is_default")
    @ApiModelProperty(name = "isDefault",notes = "是否默认", dataType = "String")
    private String isDefault;   //是否默认 0非 1默认

    @TableField(value = "create_time")
    private Date createTime;    //创建时间

    @TableField(value = "update_time")
    private Date updateTime;    //修改时间
}