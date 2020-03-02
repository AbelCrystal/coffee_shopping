package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_shipping_info")
@ApiModel(value = "公告")
public class ShippingInfo {

    @TableId(value = "ship_id")
    @TableField(value = "ship_id",exist = true)
    private String shipId;
    @TableField(value = "ship_com",exist = true)
    private String shipCom;
    @TableField(value = "ship_no",exist = true)
    private String shipNo;

}