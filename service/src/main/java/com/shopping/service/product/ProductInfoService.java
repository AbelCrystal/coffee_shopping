package com.shopping.service.product;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.CurrencyBean;
import com.shopping.entity.ProductInfo;
import com.shopping.enums.ProductAuditEnums;
import com.shopping.enums.ProductPublishEnums;
import com.shopping.mapper.ProductInfoMapper;
import com.shopping.vo.product.ProductInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("productInfoService")
public class ProductInfoService  extends ServiceImpl<ProductInfoMapper, ProductInfo> {
    Logger logger = LoggerFactory.getLogger(ProductInfoService.class);

    public List<ProductInfoVo> selectListByPage() {
        return baseMapper.selectListByPage();
    }

    public List<ProductInfo> getProduceInfoList(String oneCategoryId){

        return baseMapper.getProduceInfoList(oneCategoryId);
    }

    /**
     *
     *
     * @description: 根据商品id 查询商品对象
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 14:29
     */
    public ProductInfo selectById(String productId){
        EntityWrapper<ProductInfo> productInfoEntityWrapper = new EntityWrapper<>();
        productInfoEntityWrapper.eq("product_id",productId);
        productInfoEntityWrapper.eq("publish_status", ProductPublishEnums.PUTAWAY.getCode());
        productInfoEntityWrapper.eq("audit_status", ProductAuditEnums.CHECKED.getCode());
        productInfoEntityWrapper.ge("inventory", 1);
        List<ProductInfo> productInfoList =baseMapper.selectList(productInfoEntityWrapper);
        if (productInfoList.size() > 0) {
            return productInfoList.get(0);
        }
        return null;
    }
    public ProductInfo getProductInfoById(String productId){
        EntityWrapper<ProductInfo> productInfoEntityWrapper = new EntityWrapper<>();
        productInfoEntityWrapper.eq("product_id",productId);
//        productInfoEntityWrapper.eq("publish_status", ProductPublishEnums.PUTAWAY.getCode());
//        productInfoEntityWrapper.eq("audit_status", ProductAuditEnums.CHECKED.getCode());
//        productInfoEntityWrapper.ge("inventory", 1);
        List<ProductInfo> productInfoList =baseMapper.selectList(productInfoEntityWrapper);
        if (productInfoList.size() > 0) {
            return productInfoList.get(0);
        }
        return null;
    }

    /**
     *
     *
     * @description: 根据id数组获取商品列表
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/3 0027 21:40
     */
    public List<ProductInfo> selectListByArrayId(String[] productArray) {
        try {
            List<ProductInfo> productInfoList = new ArrayList<>();
            for (int i = 0; i < productArray.length; i++) {
                EntityWrapper<ProductInfo> productInfoWrapper = new EntityWrapper<>();
                productInfoWrapper.eq("product_id", productArray[i]);
                ProductInfo productInfo = this.selectOne(productInfoWrapper);
                if (Objects.isNull(productInfo)) continue;
                productInfoList.add(productInfo);
            }
            return productInfoList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}