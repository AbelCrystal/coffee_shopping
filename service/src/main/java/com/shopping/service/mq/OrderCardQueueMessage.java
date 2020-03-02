package com.shopping.service.mq;

import com.shopping.entity.OrderCartBean;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class OrderCardQueueMessage implements Serializable {
    /**
     * 用户Id
     */
    private  String userId;
    /**
     * 微信用户Id
     */
    private String wxUUId;
    /**
     * 支付宝用户Id
     */
    private String aliUUId;
    /**
     * 购物车Id列表
     */
    private List<String>cartIdArray;
    /**
     * 订单来源
     */
    private String orderSrc;
    /**
     * 订单Id
     */
    private  String orderId;
    /**
     * 供应商Id
     */
    private  String supplierId;
    /**
     * 桌号
     */
    private  String tableNo;
}
