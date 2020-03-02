package com.shopping.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.PayMoney;
import com.shopping.entity.ProductAttribute;
import com.shopping.mapper.PayMoneyMapper;
import com.shopping.mapper.ProductAttributeMapper;
import org.springframework.stereotype.Service;

@Service("payMoneyService")
public class PayMoneyService extends ServiceImpl<PayMoneyMapper, PayMoney> {
}
