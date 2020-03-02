package com.shopping.service.product;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.ProductAttribute;
import com.shopping.mapper.ProductAttributeMapper;
import org.springframework.stereotype.Service;

@Service("productAttributeService")
public class ProductAttributeService extends ServiceImpl<ProductAttributeMapper, ProductAttribute> {
}
