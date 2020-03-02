package com.shopping.vo.supplier;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商户信息")
public class SupplierInfoVo {
    @ApiModelProperty(name = "id",notes = "id",dataType ="String")
    private String id;
    @ApiModelProperty(name = "supplierName",notes = "商户名称",dataType ="String")
    private String supplierName;
    @ApiModelProperty(name = "supplierExplain",notes = "商户描述",dataType ="String")
    private String supplierExplain;
    @ApiModelProperty(name = "supplierLogUrl",notes = "logo图片地址",dataType ="String")
    private String supplierLogUrl;
    @ApiModelProperty(name = "productCount",notes = "商品数量",dataType ="long")
    private long productCount;

}