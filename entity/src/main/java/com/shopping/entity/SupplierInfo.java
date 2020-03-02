package com.shopping.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("tb_supplier_info")
public class SupplierInfo {
    @TableId(value = "id")
    private String id;
    @TableField(value = "juridical_name",exist = true)
    private String juridicalName;
    @TableField(value = "juridical_cardno",exist = true)
    private String juridicalCardno;
    @TableField(value = "corporate_name",exist = true)
    private String corporateName;
    @TableField(value = "credit_code",exist = true)
    private String creditCode;
    @TableField(value = "supplier_name",exist = true)
    private String supplierName;
    @TableField(value = "supplier_phone",exist = true)
    private String operatorPhone;
    @TableField(value = "supplier_address",exist = true)
    private String supplierAddress;
    @TableField(value = "supplier_explain",exist = true)
    private String supplierExplain;
    @TableField(value = "supplier_log_url",exist = true)
    private String supplierLogUrl;
    @TableField(value = "supplier_status",exist = true)
    private String supplierStatus;
    @TableField(value = "zip_code",exist = true)
    private String zipCode;
    @TableField(value = "contacts",exist = true)
    private String contacts;
    @TableField(value = "refund_phone",exist = true)
    private String refundPhone;
    @TableField(value = "receiving_address",exist = true)
    private String receivingAddress;
    @TableField(value = "update_time",exist = true)
    private Date updateTime;
    @TableField(value = "create_time",exist = true)
    private Date createTime;
    @TableField(value = "is_delete",exist = true)
    private String isDelete;


}