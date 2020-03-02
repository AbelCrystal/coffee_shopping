package com.shopping.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/6/25 0025 19:07
 * @Description: 收藏夹
 */

@Data
@TableName("tb_collection")
public class CollectionBean implements Serializable {

    @TableId(value = "collection_id")
    private String collectionId;        //收藏夹id

    @TableField(value = "user_id")
    private String userId;          //用户id

    @TableField(value = "product_id")
    private String productId;       //商品id

    @TableField(value = "create_time")
    private Date createTime;    //收藏时间

    @TableField(value = "update_time")
    private Date updateTime;    //删除时间

    @TableField(value = "is_delete",exist = true)
    private String isDelete;        //是否删除  0正常  1删除
}
