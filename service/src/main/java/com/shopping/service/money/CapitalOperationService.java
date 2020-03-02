package com.shopping.service.money;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.CapitalOperationBean;
import com.shopping.entity.User;
import com.shopping.entity.UserAccountBean;
import com.shopping.entity.WithdrawFeesBean;
import com.shopping.enums.CapitalOperationOutStatusEnums;
import com.shopping.enums.CapitalOperationTypeEnums;
import com.shopping.mapper.CapitalOperationMapper;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.BigDecimalUtil;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2 0002 17:37
 * @Description: 充提币记录Service
 */

@Service("capitalOperationService")
public class CapitalOperationService extends ServiceImpl<CapitalOperationMapper, CapitalOperationBean> {

    Logger logger = LoggerFactory.getLogger(CapitalOperationService.class);

    @Autowired
    private UserAccountService userAccountService;
    /**
     *
     *
     * @description: 根据条件查询充提币记录
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/2 0002 20:42
     */
    public List<CapitalOperationBean> selectListByFilter(String filter) {
        try {
            EntityWrapper<CapitalOperationBean> capitalOperationBeanEntityWrapper = new EntityWrapper<>();
            capitalOperationBeanEntityWrapper.addFilter(filter);
            capitalOperationBeanEntityWrapper.orderBy("update_time",false);
            return selectList(capitalOperationBeanEntityWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 统计今日提现次数
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 11:01
     */
    public int getTodayCoinWithdrawTimes (User user) {
        try {
            EntityWrapper<CapitalOperationBean> capitalOperationBeanEntityWrapper = new EntityWrapper<>();
            capitalOperationBeanEntityWrapper.eq("user_id",user.getId());
            capitalOperationBeanEntityWrapper.gt("create_time",new Timestamp(DateUtils.getTimesmorning()));
            capitalOperationBeanEntityWrapper.eq("type", CapitalOperationTypeEnums.COIN_OUT.getCode());
            capitalOperationBeanEntityWrapper.ne("status", CapitalOperationOutStatusEnums.OPERATION_CANCEL.getCode());
            return selectCount(capitalOperationBeanEntityWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 提币
     * @param:
     * @return: 
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 17:02
     */
    @Transactional
    public boolean updateWithdrawCoin(boolean feeFlag,WithdrawFeesBean withdrawFeesBean,String address, String currencyId, BigDecimal withdrawAmount, User user) throws RuntimeException{
        CapitalOperationBean capitalOperationBean = new CapitalOperationBean();
        capitalOperationBean.setId(IdWorker.getNewInstance().nextIdToString());
        Assert.isTrue(userAccountService.updateAccount(user.getId(),currencyId,withdrawAmount,capitalOperationBean.getId()),"账户资金不足");
        capitalOperationBean.setAddress(address);
        capitalOperationBean.setAmount(withdrawAmount);
        capitalOperationBean.setCurrencyId(currencyId);
        capitalOperationBean.setType(CapitalOperationTypeEnums.COIN_OUT.getCode());
        capitalOperationBean.setStatus(CapitalOperationOutStatusEnums.WAIT_FOR_OPERATION.getCode());
        if (feeFlag) {
            capitalOperationBean.setFeeCoinId(currencyId);
            capitalOperationBean.setFees(withdrawFeesBean.getWithdraw());
        }
        capitalOperationBean.setUserId(user.getId());
        capitalOperationBean.setCreateTime(DateUtils.getDBDate());
        capitalOperationBean.setUpdateTime(DateUtils.getDBDate());
        return insert(capitalOperationBean);
    }

    /**
     *
     *
     * @description: 取消订单 只有为 0 等待审核中才能取消订单
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/9 0009 15:41
     */
    @Transactional
    public boolean updateWithdrawStatus(CapitalOperationBean capitalOperationBean) throws RuntimeException{
        try {
            //条件
            CapitalOperationBean upCapital = new CapitalOperationBean();
            upCapital.setId(capitalOperationBean.getId());
            upCapital.setVersion(capitalOperationBean.getVersion());

            capitalOperationBean.setStatus(CapitalOperationOutStatusEnums.OPERATION_CANCEL.getCode());
            capitalOperationBean.setUpdateTime(DateUtils.getDBDate());
            capitalOperationBean.setVersion(capitalOperationBean.getVersion() + 1); //乐观锁
            Assert.isTrue(userAccountService.updateCancel(capitalOperationBean.getUserId(),capitalOperationBean),"账户冻结资金异常");

            return update(capitalOperationBean,new EntityWrapper<>(upCapital));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
