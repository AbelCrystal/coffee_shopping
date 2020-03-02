package com.shopping.service.user;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.UserAddressBean;
import com.shopping.enums.GlobalIsDefaultEnums;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.UserAddressMapper;
import com.shopping.unit.DateUtils;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/6/21 0025 17:08
 * @Description: 收货地址Service
 */
@Service("userAddressService")
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddressBean> {

    Logger logger = LoggerFactory.getLogger(UserAddressService.class);
    /**
     * @description:查询用户收货地址列表或详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/21 0021 15:16
     */
    public List<UserAddressBean> selectListById(String addressId, String user_id) {
        try {
            EntityWrapper<UserAddressBean> addressWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(addressId)) {
                addressWrapper.eq("address_id", addressId);
            }
            addressWrapper.eq("user_id", user_id);
            addressWrapper.ne("is_delete", GlobalIsDeleteEnums.IS_DISABLE.getCode());
            addressWrapper.orderBy("is_default", false);
            return selectList(addressWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 增加用户收货地址并设置默认
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/3 0003 21:09
     */
    @Transactional
    public void addAddress(UserAddressBean userAddressBean) throws RuntimeException{
        try {
            if (userAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {
                //需要设置默认
                updateOriginalInfo(userAddressBean.getUserId());
            }
            insert(userAddressBean); //然后再设置默认地址
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 设置默认收货地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/3 0003 21:19
     */
    @Transactional
    public boolean updateDefaultAddress(String addressId, String uid) throws RuntimeException{
        try {
            UserAddressBean userAddressBean = selectById(addressId);
            if (userAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {    //如果原来是 则改为非
                userAddressBean.setIsDefault(GlobalIsDefaultEnums.NO.getCode());
                userAddressBean.setUpdateTime(DateUtils.getDBDate());
                return updateById(userAddressBean);
            } else {
                updateOriginalInfo(uid);    //将原来的设为非默认
                userAddressBean.setIsDefault(GlobalIsDefaultEnums.YES.getCode());
                userAddressBean.setUpdateTime(DateUtils.getDBDate());
                return updateById(userAddressBean);
            }
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
     * @time: 2019/7/3 0003 21:25
     */
    private void updateOriginalInfo(String userId) {
        try {
            EntityWrapper<UserAddressBean> addressWrapper = new EntityWrapper<>();
            addressWrapper.eq("user_id",userId);
            addressWrapper.eq("is_default", GlobalIsDefaultEnums.YES.getCode());
            addressWrapper.ne("is_delete", GlobalIsDeleteEnums.IS_DISABLE.getCode());
            List<UserAddressBean> list = selectList(addressWrapper);
            if (list.size() > 0) {
                for (UserAddressBean userAddress : list) {
                    userAddress.setIsDefault(GlobalIsDefaultEnums.NO.getCode());   //将原来的设置为非默认
                    userAddress.setUpdateTime(DateUtils.getDBDate());
                    updateById(userAddress);
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
     * @description:修改收货地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 15:38
     */
    @Transactional
    public boolean updateAddress(UserAddressBean userAddressBean) throws RuntimeException{
        try {
           if (userAddressBean.getIsDefault().equals(GlobalIsDefaultEnums.YES.getCode())) {
               updateOriginalInfo(userAddressBean.getUserId());    //将原来的设为非默认
           }
           return updateById(userAddressBean);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
