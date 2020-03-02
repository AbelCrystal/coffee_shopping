package com.shopping.service.product;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.ProductCategory;
import com.shopping.entity.ProductPicInfo;
import com.shopping.entity.SupplierInfo;
import com.shopping.mapper.ProductCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @date 2019-05-30 20:52
 */
@Service("productCategoryService")
public class ProductCategoryService extends ServiceImpl<ProductCategoryMapper, ProductCategory> {

    /**
     * 查询一级分类
     * @return
     */
    public List<ProductCategory> findFirstLevel(){
        EntityWrapper<ProductCategory> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("category_level", 1);
        qryWrapper.eq("category_status", 1);
        qryWrapper.orderBy("sort",true);
        return baseMapper.selectList(qryWrapper);
    }

    /**
     * 根据父id查询下面所有分类
     * @param parentId
     * @return
     */
    public List<ProductCategory> findTwoLevelByParentId(String parentId){
        EntityWrapper<ProductCategory> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("parent_id", parentId);
        qryWrapper.eq("category_status", 1);

        return baseMapper.selectList(qryWrapper);
    }

    /**
     * 根据filter 条件查询
     * @param filter
     * @return
     */
    public List<ProductCategory> getProductCategoryList(String filter){

        return baseMapper.selectList(new EntityWrapper<ProductCategory>().addFilter(filter));
    }
}
