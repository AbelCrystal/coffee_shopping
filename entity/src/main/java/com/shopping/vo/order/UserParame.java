package com.shopping.vo.order;

import lombok.Data;

@Data
public class UserParame {
    /**
     * 用户Id
     */
    private String id;
    /**
     * 微信用户Id
     */
    private String wxUUId;
    /**
     * 支付宝用户Id
     */
    private String aliUUId;
    /**
     * 商品Id
     */
    private  String productId;
    /**
     * 购物车ID
     */
    private  String cartId;
    /**
     * 商品属性
     */
    private  String productAttr;
}
