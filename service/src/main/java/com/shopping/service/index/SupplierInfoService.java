package com.shopping.service.index;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.Advertisement;
import com.shopping.entity.Notice;
import com.shopping.entity.ProductInfo;
import com.shopping.entity.SupplierInfo;
import com.shopping.enums.SupplierEmums;
import com.shopping.mapper.AdvertisementMapper;
import com.shopping.mapper.SupplierInfoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("supplierInfoService")
public class SupplierInfoService extends ServiceImpl<SupplierInfoMapper, SupplierInfo> {

    /**
     * 根据供应商状态查询
     * @return
     */
    public List<SupplierInfo> selectListByName() {
        EntityWrapper<SupplierInfo> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("supplier_status", SupplierEmums.UPPER_SHELF.getCode());
        return baseMapper.selectList(qryWrapper);
    }

    /**
     * 根据条件查询供应商
     * @param filter
     * @param sortField
     * @param isAsc
     * @return
     */
    public List<SupplierInfo> getSupplierInfo(String filter, String sortField, boolean isAsc){
        return baseMapper.selectList(new EntityWrapper<SupplierInfo>().addFilter(filter).orderBy(sortField,isAsc));
    }
}
