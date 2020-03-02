package com.shopping.service.money;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.ChargeAddressBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.ChargeAddressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 17:37
 * @Description: 充币地址Service
 */
@Service("chargeAddressService")
public class ChargeAddressService extends ServiceImpl<ChargeAddressMapper,ChargeAddressBean> {

    Logger logger = LoggerFactory.getLogger(ChargeAddressService.class);
    /**
     *
     *
     * @description: 查询用户充币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/2 0002 20:42
     */
    public ChargeAddressBean selectListById(String userId,String currencyId) {
        try {
            EntityWrapper<ChargeAddressBean> capitalOperationBeanEntityWrapper = new EntityWrapper<>();
            capitalOperationBeanEntityWrapper.eq("user_id",userId);
            capitalOperationBeanEntityWrapper.eq("currency_id",currencyId);
            capitalOperationBeanEntityWrapper.eq("is_delete", GlobalIsDeleteEnums.IS_NORMAL.getCode());
            List<ChargeAddressBean> list = selectList(capitalOperationBeanEntityWrapper);
            if (list.size() == 1) {
                return list.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
