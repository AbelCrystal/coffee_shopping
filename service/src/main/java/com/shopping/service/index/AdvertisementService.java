package com.shopping.service.index;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.Advertisement;
import com.shopping.entity.Notice;
import com.shopping.enums.AdvertEmums;
import com.shopping.mapper.AdvertisementMapper;
import com.shopping.mapper.NoticeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("advertisementService")
public class AdvertisementService extends ServiceImpl<AdvertisementMapper, Advertisement> {


    public List<Advertisement> selectListByType(String type) {
        EntityWrapper<Advertisement> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("advert_type", type);
        qryWrapper.eq("is_upper",AdvertEmums.ADVERT_UPPER.getCode());
        qryWrapper.orderBy("createTime",false);
        return baseMapper.selectList(qryWrapper);
    }
}
