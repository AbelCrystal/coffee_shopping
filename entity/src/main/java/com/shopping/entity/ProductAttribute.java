package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 *@description: 平台资产表
 *@author:Darryl_Tang
 *@Time:2019/7/21 0025 17:08
 *
 */

@Data
@TableName("tb_product_attribute")
public class ProductAttribute implements Serializable {
    @TableId(value = "id")
    private String id;
    @TableField(value = "product_id",exist = true)
    private String productId;        //商品Id
    @TableField(value = "attr_name", exist = true)
    private String attrName;    //属性名称
    @TableField(value = "attr_value", exist = true)
    private String attrValue;    //属性值
    @TableField(value = "create_time", exist = true)
    private Date createTime;    //修改删除时间
    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //修改删除时间

}