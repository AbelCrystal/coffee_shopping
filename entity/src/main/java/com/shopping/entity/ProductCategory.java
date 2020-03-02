package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_product_category")
public class ProductCategory implements Serializable {

    @TableField(value = "category_id",exist = true)
    @TableId(value = "category_id")
    private String categoryId;
    @TableField(value = "category_name",exist = true)
    private String categoryName;
    @TableField(value = "parent_id",exist = true)
    private String parentId;
    @TableField(value = "category_level",exist = true)
    private String categoryLevel;
    @TableField(value = "category_status",exist = true)
    private String categoryStatus;
    @TableField(value = "img_url",exist = true)
    private String imgUrl;
    @TableField(value = "modified_time",exist = true)
    private Date modifiedTime;


}