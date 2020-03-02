package com.shopping.vo.product;

import lombok.Data;

/**
 * 查询商品参数
 */
@Data
public class ProductInfoPram {
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 品牌Id
     */
    private String   brandId;
    /**
     * 一级分类
     */
    private String oneCategoryId;
    /**
     * 二级分类
     */
    private String twoCategoryId;
    /**
     * 三级分类
     */
    private String threeCategoryId;
    /**
     * 供应商Id
     */
    private String supplierId;
    /**
     * 排序字段
     */
    private String sortField;
    /**
     * 排序方式
     */
    private String sortType;
}
