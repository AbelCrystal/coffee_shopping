package com.shopping.service.product;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.ProductAttribute;
import com.shopping.entity.ProductSpec;
import com.shopping.mapper.ProductAttributeMapper;
import com.shopping.mapper.ProductSpecMapper;
import org.springframework.stereotype.Service;

@Service("productSpecService")
public class ProductSpecService extends ServiceImpl<ProductSpecMapper, ProductSpec> {
}
