package com.shopping.service.money;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.CurrencyBean;
import com.shopping.enums.GlobalEnums;
import com.shopping.mapper.CurrencyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 17:37
 * @Description: 币种类型Service
 */
@Service("currencyService")
public class CurrencyService extends ServiceImpl<CurrencyMapper, CurrencyBean> {

    Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    /**
     *
     *
     * @description: 根据币种名称查询币种信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/2 0002 20:42
     */
    public CurrencyBean selectListByName(String currencyName) {
        try {
            EntityWrapper<CurrencyBean> currencyWrapper = new EntityWrapper<>();
            currencyWrapper.eq("currency_name",currencyName);
            currencyWrapper.eq("is_charge", GlobalEnums.NORMAL.getCode());
            currencyWrapper.eq("status",GlobalEnums.NORMAL.getCode());
            List<CurrencyBean> currencyBeans = selectList(currencyWrapper);
            if (currencyBeans.size() == 1) {
                return currencyBeans.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
