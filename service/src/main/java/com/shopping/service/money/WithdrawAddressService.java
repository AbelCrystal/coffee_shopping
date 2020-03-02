package com.shopping.service.money;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.WithdrawAddressBean;
import com.shopping.enums.GlobalIsDefaultEnums;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.WithdrawAddressMapper;
import com.shopping.unit.DateUtils;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 17:37
 * @Description: 提币地址Service
 */
@Service("withdrawAddressService")
public class WithdrawAddressService extends ServiceImpl<WithdrawAddressMapper, WithdrawAddressBean> {

    Logger logger = LoggerFactory.getLogger(WithdrawAddressService.class);


    /**
     *
     *
     * @description: 查询提币地址列表
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 13:48
     */
    public List<WithdrawAddressBean> selectListById(String withdrawId,String user_id,String currencyId) {
        try {
            EntityWrapper<WithdrawAddressBean> withdrawAddressWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(withdrawId)) {
                withdrawAddressWrapper.eq("withdraw_id", withdrawId);
            } else {
                withdrawAddressWrapper.eq("currency_id", currencyId);
            }
            withdrawAddressWrapper.eq("user_id", user_id);
            withdrawAddressWrapper.ne("is_delete", GlobalIsDeleteEnums.IS_DISABLE.getCode());
            withdrawAddressWrapper.orderBy("is_default", false);
            return selectList(withdrawAddressWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description:添加提币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 11:28
     */
    public boolean addWithdrawAddress(WithdrawAddressBean withdrawAddressBean) {
        try {
            if (withdrawAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {
                //需要设置默认
                updateOriginalInfo(withdrawAddressBean.getUserId(),withdrawAddressBean.getCurrencyId());
            }
            return insert(withdrawAddressBean); //然后再设置默认地址
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 将原来的设置为非默认
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 11:44
     */
    private void updateOriginalInfo(String userId,String currencyId) {
        try {
            EntityWrapper<WithdrawAddressBean> withdrawAddressWrapper = new EntityWrapper<>();
            withdrawAddressWrapper.eq("user_id",userId);
            withdrawAddressWrapper.eq("currency_id",currencyId);
            withdrawAddressWrapper.eq("is_default", GlobalIsDefaultEnums.YES.getCode());
            withdrawAddressWrapper.eq("is_delete", GlobalIsDeleteEnums.IS_NORMAL.getCode());
            List<WithdrawAddressBean> list = selectList(withdrawAddressWrapper);
            if (list.size() > 0) {
                for (WithdrawAddressBean withdrawAddressBean : list) {
                    withdrawAddressBean.setIsDefault(GlobalIsDefaultEnums.NO.getCode());   //将原来的设置为非默认
                    withdrawAddressBean.setUpdateTime(DateUtils.getDBDate());
                    updateById(withdrawAddressBean);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 修改提币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 17:02
     */
    @Transactional
    public boolean updateWithdrawAddress(WithdrawAddressBean withdrawAddressBean) throws RuntimeException{
        try {
            if (withdrawAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {
                updateOriginalInfo(withdrawAddressBean.getUserId(),withdrawAddressBean.getCurrencyId());    //将原来的全置非默认
            }
            return updateById(withdrawAddressBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 设置默认提币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 17:34
     */
    @Transactional
    public boolean updateDefaultWithdrawAddress(String withdrawId, String uid) throws RuntimeException{
        try {
            WithdrawAddressBean withdrawAddressBean = selectById(withdrawId);
            if (withdrawAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {    //如果原来是 则改为非
                withdrawAddressBean.setIsDefault(GlobalIsDefaultEnums.NO.getCode());
                withdrawAddressBean.setUpdateTime(DateUtils.getDBDate());
                return updateById(withdrawAddressBean);
            } else {
                updateOriginalInfo(uid,withdrawAddressBean.getCurrencyId());    //将原来的设为非默认
                withdrawAddressBean.setIsDefault(GlobalIsDefaultEnums.YES.getCode());
                withdrawAddressBean.setUpdateTime(DateUtils.getDBDate());
                return updateById(withdrawAddressBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
