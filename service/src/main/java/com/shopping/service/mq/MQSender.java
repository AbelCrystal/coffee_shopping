package com.shopping.service.mq;

import com.alibaba.fastjson.JSONObject;
import com.shopping.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
    AmqpTemplate amqpTemplate ;
	
	public void sendOrdersMessage(OrdersQueueMessage mm) {
		String msg = RedisUtil.beanToString(mm);
		log.info("send messageOrder:"+msg);
        amqpTemplate.convertAndSend(MQConfig.ORDERS_QUEUE, mm);
	}
	public void sendOrderCardMessage(OrderCardQueueMessage mm) {
//		String msg = RedisUtil.beanToString(mm);
		log.info("send messageOrderCard:"+ JSONObject.toJSONString(mm));
		 amqpTemplate.convertAndSend(MQConfig.ORDERCAR_QUEUE, mm);
	}
}
