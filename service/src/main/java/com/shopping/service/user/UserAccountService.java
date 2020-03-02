package com.shopping.service.user;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.*;
import com.shopping.enums.AccountChangesTypeEnums;
import com.shopping.enums.AccountTypeEnums;
import com.shopping.enums.OrderPayTypeEnums;
import com.shopping.mapper.UserAccountMapper;
import com.shopping.service.common.ConstantMap;
import com.shopping.service.money.CurrencyService;
import com.shopping.unit.Constants;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/1 0025 17:08
 * @Description: 账户资产Service
 */
@Service("userAccountService")
public class UserAccountService extends ServiceImpl<UserAccountMapper, UserAccountBean> {

    Logger logger = LoggerFactory.getLogger(UserAccountService.class);

    @Autowired
    private UserAccountDetailService userAccountDetailService;

    @Autowired
    private ConstantMap constantMap;

    @Autowired
    private CurrencyService currencyService;

    /**
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0025 17:08
     */
    public List<UserAccountBean> selectListByUserId(String userId) {
        try {
            EntityWrapper<UserAccountBean> userAccountBeanEntityWrapper = new EntityWrapper<>();
            userAccountBeanEntityWrapper.eq("user_id", userId);
            userAccountBeanEntityWrapper.orderBy("accout_type", false);
            return baseMapper.selectList(userAccountBeanEntityWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 查询用户币种余额
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 18:06
     */
    public UserAccountBean selectListByCurrencyId(String currencyId, String userId, String accountType) {
        try {
            EntityWrapper<UserAccountBean> userAccountBeanEntityWrapper = new EntityWrapper<>();
            if (!StringUtils.isEmpty(currencyId)) {
                userAccountBeanEntityWrapper.eq("currency_id", currencyId);
            }
            userAccountBeanEntityWrapper.eq("user_id", userId);
            userAccountBeanEntityWrapper.eq("accout_type", accountType);
            List<UserAccountBean> userAccountBeans = baseMapper.selectList(userAccountBeanEntityWrapper);
            if (userAccountBeans.size() == 1) {
                return userAccountBeans.get(0);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * @description: 修改账户资金
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 17:20
     */
    @Transactional
    public boolean updateAccount(final String userId, final String currencyId, final BigDecimal withdrawAmount, final String capitalId) throws RuntimeException {
        //可用 = 可用 - 交易金额，冻结 = 冻结 + 交易金额，总金额暂时不改变
        try {
            UserAccountBean userAccountBean = selectListByCurrencyId(currencyId, userId, AccountTypeEnums.TONGZHENG.getCode());
            if (Objects.isNull(userAccountBean)) return false;
            if (userAccountBean.getUsable().compareTo(withdrawAmount) == -1) return false;
            //条件
            UserAccountBean upUserAccountBean = new UserAccountBean();
            upUserAccountBean.setId(userAccountBean.getId());
            upUserAccountBean.setCurrencyId(currencyId);
            upUserAccountBean.setVersion(userAccountBean.getVersion());

            userAccountBean.setUsable(userAccountBean.getUsable().subtract(withdrawAmount));//减可用
            userAccountBean.setFrozen(userAccountBean.getFrozen().add(withdrawAmount));  //加冻结
            userAccountBean.setUpdateTime(DateUtils.getDBDate());
            userAccountBean.setVersion(userAccountBean.getVersion() + 1);//乐观锁

            boolean success = update(userAccountBean, new EntityWrapper<>(upUserAccountBean));
            boolean flag = false;
            if (success) {
                //添加支出记录
                UserAccountDetail userAccountDetail = new UserAccountDetail();
                userAccountDetail.setId(IdWorker.getNewInstance().nextIdToString());
                userAccountDetail.setAmount(withdrawAmount);
                userAccountDetail.setUserId(userId);
                userAccountDetail.setCreateTime(DateUtils.getDBDate());
                userAccountDetail.setUpdateTime(DateUtils.getDBDate());
                userAccountDetail.setChangesType(AccountChangesTypeEnums.FREEZE.getCode());     //变更类型
                userAccountDetail.setCurrencyId(currencyId);
                userAccountDetail.setChangesSourceId(capitalId); //支出单号
//                userAccountDetail.setPayType(OrderPayTypeEnums.TONGZHENG.getCode());  //支付方式 通证支付
                flag = userAccountDetailService.insert(userAccountDetail); //添加记录
            }
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 取消提币回退资金
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/9 0009 16:31
     */
    @Transactional
    public boolean updateCancel(String userId, CapitalOperationBean capitalOperationBean) throws RuntimeException {
        //可用 = 可用 + 交易金额  冻结= 冻结-交易金额
        try {
            UserAccountBean userAccountBean = selectListByCurrencyId(capitalOperationBean.getCurrencyId(), userId, AccountTypeEnums.TONGZHENG.getCode());
            if (Objects.isNull(userAccountBean)) return false;
            if (userAccountBean.getFrozen().compareTo(capitalOperationBean.getAmount()) == -1) return false;

            //条件
            UserAccountBean upUserAccountBean = new UserAccountBean();
            upUserAccountBean.setId(userAccountBean.getId());
            upUserAccountBean.setVersion(userAccountBean.getVersion());
            upUserAccountBean.setCurrencyId(capitalOperationBean.getCurrencyId());

            userAccountBean.setUsable(userAccountBean.getUsable().add(capitalOperationBean.getAmount()));//+可用
            userAccountBean.setFrozen(userAccountBean.getFrozen().subtract(capitalOperationBean.getAmount()));  //-冻结
            userAccountBean.setUpdateTime(DateUtils.getDBDate());
            userAccountBean.setVersion(userAccountBean.getVersion() + 1); //乐观锁

            boolean success = update(userAccountBean, new EntityWrapper<>(upUserAccountBean));
            boolean flag = false;
            if (success) {
                //添加支出记录
                UserAccountDetail userAccountDetail = new UserAccountDetail();
                userAccountDetail.setId(IdWorker.getNewInstance().nextIdToString());
                userAccountDetail.setAmount(capitalOperationBean.getAmount());
                userAccountDetail.setUserId(userId);
                userAccountDetail.setCreateTime(DateUtils.getDBDate());
                userAccountDetail.setUpdateTime(DateUtils.getDBDate());
                userAccountDetail.setChangesType(AccountChangesTypeEnums.UNFREEZE.getCode());     //变更类型
                userAccountDetail.setCurrencyId(capitalOperationBean.getCurrencyId());
                userAccountDetail.setChangesSourceId(capitalOperationBean.getId()); //支出单号
//                userAccountDetail.setPayType(OrderPayTypeEnums.TONGZHENG.getCode());  //支付方式 通证支付
                flag = userAccountDetailService.insert(userAccountDetail); //添加记录
            }
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 增加用户充值金额
     *
     * @param rmbRecharge
     * @throws Exception
     */
    public void addUserRechargeAccount(RmbRecharge rmbRecharge) throws Exception {
        UserAccountBean userAccountBeans = selectListByCurrencyId("", rmbRecharge.getUserId(), AccountTypeEnums.RENMINGBI.getCode());
        if (userAccountBeans == null) {
            userAccountBeans = new UserAccountBean();
            userAccountBeans.setId(IdWorker.getNewInstance().nextIdToString());
            userAccountBeans.setAccoutType(AccountTypeEnums.RENMINGBI.getCode());
            userAccountBeans.setAmount(rmbRecharge.getRechargeAmount());
            userAccountBeans.setUsable(rmbRecharge.getRechargeAmount());
            userAccountBeans.setUserId(rmbRecharge.getUserId());
            userAccountBeans.setPhone(rmbRecharge.getPhone());
            userAccountBeans.setCreateTime(DateUtils.getDBDate());
            this.insert(userAccountBeans);
        } else {
            //条件
            UserAccountBean upUserAccount = new UserAccountBean();
            upUserAccount.setId(userAccountBeans.getId());
            upUserAccount.setVersion(userAccountBeans.getVersion());
            userAccountBeans.setUsable(userAccountBeans.getUsable().add(rmbRecharge.getRechargeAmount()));   //加可用余额
            userAccountBeans.setAmount(userAccountBeans.getAmount().add(rmbRecharge.getRechargeAmount()));   //加可用余额
            userAccountBeans.setUpdateTime(DateUtils.getDBDate());
            userAccountBeans.setVersion(userAccountBeans.getVersion() + 1); //乐观锁
            this.update(userAccountBeans, new EntityWrapper<>(upUserAccount));   //减账户资产
        }
        //添加支出记录
        UserAccountDetail userAccountDetail = new UserAccountDetail();
        userAccountDetail.setId(IdWorker.getNewInstance().nextIdToString());
        userAccountDetail.setAmount(rmbRecharge.getRechargeAmount());
        userAccountDetail.setUserId(rmbRecharge.getUserId());
        userAccountDetail.setCreateTime(DateUtils.getDBDate());
        userAccountDetail.setChangesType(AccountChangesTypeEnums.RECHARGE.getCode());     //变更类型
        userAccountDetail.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
        userAccountDetail.setChangesSourceId(rmbRecharge.getId()); //订单SN编号
        userAccountDetail.setPayType(rmbRecharge.getRechargeType());  //支付方式
        String remark = rmbRecharge.getRechargeType().equals("1") ? "支付宝" : "微信";
        userAccountDetail.setRemark("用户" + remark + "充值订单：" + rmbRecharge.getId());
        userAccountDetail.setPhone(rmbRecharge.getPhone());
        this.userAccountDetailService.insert(userAccountDetail);
    }

    /**
     * 用户余额通证支付操作用户金额
     *
     * @param orderMaster
     * @param userAccountBeans
     * @param payType
     * @param user
     */
    public void orderPayUserAccount(OrderMaster orderMaster, UserAccountBean userAccountBeans, String payType, User user) throws RuntimeException {
        //条件
        UserAccountBean upUserAccount = new UserAccountBean();
        upUserAccount.setId(userAccountBeans.getId());
        upUserAccount.setVersion(userAccountBeans.getVersion());
        //操作记录
        userAccountBeans.setUsable(userAccountBeans.getUsable().subtract(orderMaster.getPaymentMoney()));   //减可用
        userAccountBeans.setAmount(userAccountBeans.getAmount().subtract(orderMaster.getPaymentMoney()));   //减总额
        userAccountBeans.setUpdateTime(DateUtils.getDBDate());
        userAccountBeans.setVersion(userAccountBeans.getVersion() + 1); //乐观锁
       this.update(userAccountBeans, new EntityWrapper<>(upUserAccount));   //减账户资产
        //添加支出记录
        UserAccountDetail userAccountDetail = new UserAccountDetail();
        userAccountDetail.setId(IdWorker.getNewInstance().nextIdToString());
        userAccountDetail.setUserId(userAccountBeans.getUserId());
        userAccountDetail.setAmount(orderMaster.getPaymentMoney());
        userAccountDetail.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
        userAccountDetail.setCreateTime(DateUtils.getDBDate());
        userAccountDetail.setChangesType(AccountChangesTypeEnums.EXPEND.getCode());     //变更类型
        userAccountDetail.setCurrencyId(userAccountBeans.getCurrencyId());
        userAccountDetail.setChangesSourceId(orderMaster.getMasterId()); //订单SN编号
        userAccountDetail.setPayType(payType);  //支付方式
        userAccountDetail.setPhone(user.getPhone());
        userAccountDetail.setRemark("用户" +OrderPayTypeEnums.USERBALANCE.getDesc() + "支付订单");
        userAccountDetailService.insert(userAccountDetail); //添加记录
    }

    public void orderPayUserCurrencyAccount(OrderMaster orderMaster, UserAccountBean userAccountBeans, User user) throws RuntimeException {
        //条件
        UserAccountBean upUserAccount = new UserAccountBean();
        upUserAccount.setId(userAccountBeans.getId());
        upUserAccount.setVersion(userAccountBeans.getVersion());
        //操作记录
        userAccountBeans.setUsable(userAccountBeans.getUsable().subtract(orderMaster.getPaycoinAmount()));   //减可用
        userAccountBeans.setAmount(userAccountBeans.getAmount().subtract(orderMaster.getPaycoinAmount()));   //减总额
        userAccountBeans.setUpdateTime(DateUtils.getDBDate());
        userAccountBeans.setVersion(userAccountBeans.getVersion() + 1); //乐观锁
        this.update(userAccountBeans, new EntityWrapper<>(upUserAccount));   //减账户资产
        //添加支出记录
        UserAccountDetail userAccountDetail = new UserAccountDetail();
        userAccountDetail.setId(IdWorker.getNewInstance().nextIdToString());
        userAccountDetail.setAmount(orderMaster.getPaycoinAmount());
        userAccountDetail.setAccountType(AccountTypeEnums.TONGZHENG.getCode());
        userAccountDetail.setUserId(userAccountBeans.getUserId());
        userAccountDetail.setCreateTime(DateUtils.getDBDate());
        userAccountDetail.setChangesType(AccountChangesTypeEnums.EXPEND.getCode());     //变更类型
        userAccountDetail.setCurrencyId(userAccountBeans.getCurrencyId());
        userAccountDetail.setChangesSourceId(orderMaster.getMasterId()); //订单SN编号
        userAccountDetail.setPayType(orderMaster.getPayType());  //支付方式
        userAccountDetail.setPhone(user.getPhone());
        userAccountDetail.setRemark("用户" + user.getPhone() + "支付订单:"+orderMaster.getPaycoinAmount());
        userAccountDetailService.insert(userAccountDetail); //添加记录

    }
}
