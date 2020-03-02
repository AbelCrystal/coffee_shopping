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
@TableName("tb_advertisement")
@ApiModel(value = "广告")
public class Advertisement {
    @TableId
    private String id;
    @ApiModelProperty(name = "picUrl", notes = "图片地址", dataType = "String", required = true, allowEmptyValue = false)
    @TableField(value = "pic_url", exist = true)
    private String picUrl;
    @ApiModelProperty(name = "isUpper", notes = "是否上架", dataType = "String", required = true, allowEmptyValue = false)
    @TableField(value = "is_upper", exist = true)
    private String isUpper;
    @ApiModelProperty(name = "remark", notes = "备注", dataType = "String", required = true, allowEmptyValue = false)
    @TableField(value = "remark", exist = true)
    private String remark;
    @ApiModelProperty(name = "jumpUrl", notes = "跳转地址", dataType = "String", required = true, allowEmptyValue = false)
    @TableField(value = "jump_url", exist = true)
    private String jumpUrl;
    @ApiModelProperty(name = "createTime", notes = "创建时间", dataType = "String", required = true, allowEmptyValue = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", exist = true)
    private Date createTime;
    @ApiModelProperty(name = "updateTime", notes = "更新时间", dataType = "String", required = true, allowEmptyValue = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", exist = true)
    private Date updateTime;
}