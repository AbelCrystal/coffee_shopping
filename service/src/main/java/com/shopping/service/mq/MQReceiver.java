package com.shopping.service.mq;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.order.LogisticsPriceService;
import com.shopping.service.order.OrderDeatilService;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.product.ProductInfoService;
import com.shopping.service.user.OrderCartService;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OrderNum;
import io.netty.channel.unix.IovArray;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderDeatilService orderDeatilService;
    @Autowired
    private OrderCartService orderCartService;

    @RabbitListener(queues = MQConfig.ORDERS_QUEUE)
    public void receive(OrdersQueueMessage mm) {
        log.info("receive message:" + RedisUtil.beanToString(mm));
        try {
            User user = mm.getUser();
            OrderMaster orderMaster = mm.getOrderMaster();   //主订单
            Set<OrderDetailBean> orderDetailBeanSet = mm.getOrderDetailBeanSet();   //详情订单集合
            Set<OrderCartBean> orderCartBeanSet = mm.getOrderCartBeanSet(); //购物车集合

            for (OrderDetailBean orderDetailBean : orderDetailBeanSet) {
                EntityWrapper<ProductInfo> qryProductInfoWrapper = new EntityWrapper<>();
                qryProductInfoWrapper.eq("product_id", orderDetailBean.getProductId());
                ProductInfo productInfo = productInfoService.selectOne(qryProductInfoWrapper);
                long inventory = productInfo.getInventory();
                if (inventory < orderDetailBean.getProductNum()) {
                    throw new RuntimeException(MessageEnums.NO_INVENTORY.getDesc()); //库存小于购买数量
                }
                OrderDetailBean orderDetail = orderDeatilService.getOrderByMasterIdDetailId(orderMaster.getMasterId(), orderDetailBean.getDetailId());
                if (Objects.nonNull(orderDetail))
                    throw new RuntimeException(MessageEnums.ORDERS_FAILR.getDesc());   //存在该订单
                OrderMaster order = orderMasterService.getOrderByUserId(user.getId(), orderMaster.getMasterId());
                if (Objects.nonNull(order)) throw new RuntimeException(MessageEnums.ORDERS_FAILR.getDesc()); //该用户存在该订单
            }

            //减库存 下订单
            orderMasterService.addProductOrder(orderMaster, orderDetailBeanSet, orderCartBeanSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @RabbitListener(queues = MQConfig.ORDERCAR_QUEUE)
    public void receive0rderCard(OrderCardQueueMessage mm) {

        log.info("receive0rderCard message:" + JSONObject.toJSONString(mm));
        try {
            orderCartService.placeOrder(mm);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
