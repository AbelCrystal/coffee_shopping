package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
@TableName("tb_product_pic_info")
public class ProductPicInfo implements Serializable {
    @TableField(value = "product_pic_id",exist = true)
    @TableId(value = "product_pic_id")
    private String productPicId;
    @TableField(value = "product_id",exist = true)
    private String productId;
    @TableField(value = "pic_desc",exist = true)
    private String picDesc;
    @TableField(value = "pic_url",exist = true)
    private String picUrl;
    @TableField(value = "pic_path",exist = true)
    private String picPath;
    @TableField(value = "pic_type",exist = true)
    private String picType;
    @TableField(value = "pic_order",exist = true)
    private String picOrder;
    @TableField(value = "pic_status",exist = true)
    private String picStatus;
    @TableField(value = "modified_time",exist = true)
    private Date modifiedTime;

}