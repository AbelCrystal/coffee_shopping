package com.shopping.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shopping.entity.ProductInfo;
import com.shopping.entity.ProductPicInfo;
import org.apache.ibatis.annotations.Select;

public interface ProductPicInfoMapper extends BaseMapper<ProductPicInfo> {

    @Select(" SELECT count(1) FROM tb_product_info WHERE supplier_id=#{supplierId} and inventory>0 and publish_status='1' and audit_status='1'")
    long getProductCountBySupplierId(String supplierId);
}