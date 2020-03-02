package com.shopping.service.product;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.ProductInfo;
import com.shopping.entity.ProductPicInfo;
import com.shopping.entity.SupplierInfo;
import com.shopping.enums.SupplierEmums;
import com.shopping.mapper.ProductPicInfoMapper;
import com.shopping.vo.product.ProductInfoVo;
import com.shopping.vo.product.ProductPicInfoVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("productPicInfoService")
public class ProductPicInfoService extends ServiceImpl<ProductPicInfoMapper, ProductPicInfo> {
    public List<ProductPicInfo> getByProductId(String productId, String type) {
        EntityWrapper<ProductPicInfo> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("product_id", productId);
        qryWrapper.eq("pic_status", 1);
        qryWrapper.eq("pic_type", type);
        return baseMapper.selectList(qryWrapper);
    }


    public long getProductCountBySupplierId(String supplierId) {
        return baseMapper.getProductCountBySupplierId(supplierId);
    }
}