package com.shopping.service.money;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.WithdrawFeesBean;
import com.shopping.mapper.WithdrawFeesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 17:37
 * @Description: 提币手续费Service
 */
@Service("withdrawFeesService")
public class WithdrawFeesService extends ServiceImpl<WithdrawFeesMapper, WithdrawFeesBean> {

    Logger logger = LoggerFactory.getLogger(WithdrawFeesService.class);

    /**
     * @description: 通过币种id 查询提币手续费
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 18:30
     */
    public WithdrawFeesBean findByCoin(String currencyId) {
        try {
            EntityWrapper<WithdrawFeesBean> withdrawFeesWrapper = new EntityWrapper<>();
            withdrawFeesWrapper.eq("currency_id", currencyId);
            List<WithdrawFeesBean> list = selectList(withdrawFeesWrapper);
            if (list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
