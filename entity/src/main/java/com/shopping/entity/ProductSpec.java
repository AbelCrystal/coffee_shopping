package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *@description: 平台资产表
 *@author:Darryl_Tang
 *@Time:2019/7/21 0025 17:08
 *
 */

@Data
@TableName("tb_product_spec")
public class ProductSpec implements Serializable {
    @TableId(value = "id")
    private String id;
    @TableField(value = "product_id",exist = true)
    private String productId;        //商品Id
    @TableField(value = "spec_name", exist = true)
    private String specName;    //属性代码
    @TableField(value = "spec_value", exist = true)
    private String specValue;    //属性名称
    @TableField(value = "rmb_price", exist = true)
    private String rmbPrice;    //属性名称
    @TableField(value = "create_time", exist = true)
    private Date createTime;    //修改删除时间


}