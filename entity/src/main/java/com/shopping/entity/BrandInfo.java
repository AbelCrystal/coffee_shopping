package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tb_product_info")
public class BrandInfo implements Serializable {
    @TableField(value = "brand_id",exist = true)
    @TableId(value = "brand_id")
    private String brandId;//'品牌ID',
    @TableField(value = "brand_name",exist = true)
    private String brandName;//'品牌名称',
    @TableField(value = "telephone",exist = true)
    private String telephone;//'联系电话',
    @TableField(value = "brand_web",exist = true)
    private String brandWeb;// '品牌网络',
    @TableField(value = "brand_logo",exist = true)
    private String brandLogo;// '品牌logo URL',
    @TableField(value = "brand_desc",exist = true)
    private String brandDesc;// '品牌描述',
    @TableField(value = "brand_status",exist = true)
    private String brandStatus;// '品牌状态,0禁用,1启用',
    @TableField(value = "brand_order",exist = true)
    private String brandTrder;// '排序',
    @TableField(value = "modified_time",exist = true)
    private Date modifiedTime;// '最后修改时间',

}