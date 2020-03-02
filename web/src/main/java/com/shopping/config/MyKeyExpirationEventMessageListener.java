package com.shopping.config;
import com.shopping.enums.RedisPrefixEmums;
import com.shopping.service.order.OrderMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

public class MyKeyExpirationEventMessageListener extends KeyExpirationEventMessageListener {

    public MyKeyExpirationEventMessageListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }
    Logger logger = LoggerFactory.getLogger(MyKeyExpirationEventMessageListener.class);
    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        String strMsg = new String(message.getBody());
        if (strMsg.contains(RedisPrefixEmums.ORDEER_STATE_WAIT_PAYMENT_PREFIX.getCode())) {
            String[] arry=strMsg.split(":");
            if(arry.length==2) {
                String orderId = arry[1];
                orderMasterService.cancelOrder(orderId, "支付超时系统自动取消");
                logger.info("取消超时订单：{}", orderId);
            }
        }

    }
}