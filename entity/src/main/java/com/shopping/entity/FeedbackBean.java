package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 16:23
 * @Description: 意见反馈
 */

@Data
@TableName("tb_feedback")
public class FeedbackBean {
    @TableId(value = "id")
    private String feedbackId;       //反馈id

    @TableField(value = "feedback_user_id", exist = true)
    private String feedbackUserId;       //反馈人id

    @TableField(value = "feedback_type",exist = true)
    private String feedbackType;     //反馈类型 0 请选择反馈类型 1商品相关 2物流状况 3客户服务 4优惠活动 5产品体验 6产品功能 7其他 默认 0

    @TableField(value = "feedback_content", exist = true)
    private String feedbackContent;      //反馈内容

    @TableField(value = "receive_mode", exist = true)
    private String receiveMode;      //接收方式

    @TableField(value = "reply_user_id", exist = true)
    private String replyUserId;     //回复人id

    @TableField(value = "reply_content", exist = true)
    private String replyContent;   //回复内容

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //反馈时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //回复删除修改时间

    @TableField(value = "status",exist = true)
    private String  status;     //状态 0 未读 1已读 2未回复 3已回复

    @TableField(value = "is_delete",exist = true)
    private String isDelete;    //是否删除 0正常 1删除

}