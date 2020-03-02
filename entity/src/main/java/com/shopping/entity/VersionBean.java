package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_version")
public class VersionBean {
    @TableId(value = "version_id")
    private String versionId;       //版本id

    @TableField(value = "version_title", exist = true)
    private String versionTitle;       //版本号

    @TableField(value = "content",exist = true)
    private String content;     //版本更新公告内容

    @TableField(value = "create_admin_name", exist = true)
    private String createAdminName;      //发布管理员姓名

    @TableField(value = "update_admin_name", exist = true)
    private String updateAdminName;      //修改管理员姓名

    @TableField(value = "create_time", exist = true)
    private Date createTime;    //发布时间

    @TableField(value = "update_time", exist = true)
    private Date updateTime;    //删除修改时间

    @TableField(value = "is_delete",exist = true)
    private String isDelete;    //是否删除 0正常 1删除

}