package com.shopping.vo.product;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "图片详情")
public class ProductPicInfoVo implements Serializable {
    @ApiModelProperty(name = "productPicId",notes = "图片id",dataType ="String")
    @TableField(value = "product_pic_id",exist = true)
    private String productPicId;
    @ApiModelProperty(name = "productId",notes = "商品Id",dataType ="String")
    private String productId;
    @ApiModelProperty(name = "picDesc",notes = "图片描述",dataType ="String")
    private String picDesc;
    @ApiModelProperty(name = "picUrl",notes = "图片URL",dataType ="String")
    private String picUrl;
    @ApiModelProperty(name = "picPath",notes = "服务器物理地址",dataType ="String")
    private String picPath;
    @ApiModelProperty(name = "picType",notes = "图片类型",dataType ="char")
    private String picType;
    @ApiModelProperty(name = "picOrder",notes = "图片排序",dataType ="int")
    private String picOrder;
    @ApiModelProperty(name = "picStatus",notes = "是否有效",dataType ="int")
    private String picStatus;
    @ApiModelProperty(name = "modifiedTime",notes = "最后修改时间",dataType ="Date")
    private Date modifiedTime;

}