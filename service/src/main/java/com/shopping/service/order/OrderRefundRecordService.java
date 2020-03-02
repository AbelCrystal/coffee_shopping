package com.shopping.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.OrderRefundRecord;
import com.shopping.mapper.OrderRefundRecordMapper;
import org.springframework.stereotype.Service;

@Service("orderRefundRecordService")
public class OrderRefundRecordService extends ServiceImpl<OrderRefundRecordMapper, OrderRefundRecord> {


}
