package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_refund_record")
@ApiModel(value = "退款审核记录")
public class OrderRefundRecord {
    @TableId
    @ApiModelProperty(name = "id", notes = "id", dataType = "String")
    private String id;
    @TableField(value = "refund_id", exist = true)
    @ApiModelProperty(name = "refundId", notes = "退款Id", dataType = "String")
    private String refundId;
    @TableField(value = "auditor_id", exist = true)
    @ApiModelProperty(name = "auditorId", notes = "审核人", dataType = "String")
    private String auditorId;
    @TableField(value = "auditor_state", exist = true)
    @ApiModelProperty(name = "auditorState", notes = "审核状态：退款状态0申请，1申请审核，2售后收货，3进行退款，4处理完成", dataType = "String")
    private String auditorState;
    @TableField(value = "state_des", exist = true)
    @ApiModelProperty(name = "stateDes", notes = "审核状态说明", dataType = "String")
    private String stateDes;
    @TableField(value = "auditor_opinion", exist = true)
    @ApiModelProperty(name = "auditorOpinion", notes = "审核意见", dataType = "String")
    private String auditorOpinion;
    @TableField(value = "refund_type", exist = true)
    @ApiModelProperty(name = "refundType", notes = "退款类型", dataType = "String")
    private String refundType;
    @TableField(value = "create_time", exist = true)
    @ApiModelProperty(name = "createTime", notes = "审核时间", dataType = "String")
    private Date createTime;


}