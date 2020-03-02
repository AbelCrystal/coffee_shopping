package com.shopping.vo.product;

import com.shopping.entity.ProductInfo;
import com.shopping.vo.supplier.SupplierInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "商户信息")
public class PorductBySupplierVo implements Serializable {
    @ApiModelProperty(name = "supplierInfoVo", notes = "供应商信息", dataType = "String")
    private SupplierInfoVo supplierInfoVo;
    @ApiModelProperty(name = "productList", notes = "商品列表", dataType = "List")
    private List<ProductInfo> productList;
    @ApiModelProperty(name = "total", notes = "总笔数", dataType = "long")
    private long total;
    @ApiModelProperty(name = "pageNum", notes = "第几页", dataType = "int")
    private int pageNum;
}
