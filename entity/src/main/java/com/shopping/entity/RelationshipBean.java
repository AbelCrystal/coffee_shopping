package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;

import java.util.Date;


/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/30
 * @Description: 推荐关系
 */


@Data
@TableName("tb_relationship_log")
public class RelationshipBean {
    @TableId
    private String id;  //币id

    @TableField(value = "parent_invite_id")
    private String parentInviteId;      //邀请码

    @TableField(value = "child_invite_id")
    private String childInviteId;    //被邀请人邀请码

    @TableField(value = "parent_child_tree")
    private String parentChildTree;     //推荐关系树

    @TableField(value = "parent_reward_coin")
    private String parentRewardCoin;   //父级奖励币种id

    @TableField(value = "parent_reward_num")
    private String parentRewardNum;    //父级奖励币种数量

    @TableField(value = "child_reward_coin")
    private String childRewardCoin;   //子级奖励币种id

    @TableField(value = "child_reward_num")
    private String childRewardNum;    //子级奖励币种数量

    @TableField(value = "remark")
    private String remark;      //备注

    @TableField(value = "create_time")
    private Date createTime;    //创建时间

    @TableField(value = "update_time")
    private Date updateTime;  //修改时间

    @TableField(value = "level")
    private Integer level;  //级别

    @TableField(value = "version")
    @Version
    private Integer version;    //版本

}