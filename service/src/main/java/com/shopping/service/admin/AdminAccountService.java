package com.shopping.service.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.*;
import com.shopping.enums.AccountChangesTypeEnums;
import com.shopping.enums.AccountTypeEnums;
import com.shopping.enums.OrderPayTypeEnums;
import com.shopping.mapper.AdminAccountMapper;
import com.shopping.mapper.UserAccountMapper;
import com.shopping.service.common.ConstantMap;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.user.UserAccountDetailService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.Constants;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import org.apache.commons.lang3.StringUtils;
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
 * @Date: 2019/7/21 0021 17:08
 * @Description: 平台资产Service
 */
@Service("adminAccountService")
public class AdminAccountService extends ServiceImpl<AdminAccountMapper, AdminAccountBean> {

    Logger logger = LoggerFactory.getLogger(AdminAccountService.class);


    @Autowired
    private ConstantMap constantMap;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AdminAccountDetailService adminAccountDetailService;
    @Autowired
    private UserAccountService userAccountService;

    /**
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/21 0021 17:08
     */
    public List<AdminAccountBean> selectListByAdminId(String adminId) {
        try {
            EntityWrapper<AdminAccountBean> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("admin_id", adminId);
            entityWrapper.orderBy("update_time", false);
            return baseMapper.selectList(entityWrapper);
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
     * @time: 2019/7/21 0021 18:06
     */
    public AdminAccountBean selectListByCurrencyId(String currencyId, String adminId, String accountType) {
        try {
            EntityWrapper<AdminAccountBean> entityWrapper = new EntityWrapper<>();
            if (StringUtils.isNotBlank(currencyId)) {
                entityWrapper.eq("currency_id", currencyId);
            }
            entityWrapper.eq("admin_id", adminId);
            entityWrapper.eq("account_type", accountType);
            List<AdminAccountBean> accountBeans = baseMapper.selectList(entityWrapper);
            if (accountBeans.size() == 1) {
                return accountBeans.get(0);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * @description: 给平台增加资金
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/21 0021 18:48
     */

    public void updateAdminAccount(OrderMaster orderMaster, String payType, String currencyId) throws RuntimeException {
        //给平台账户加资金
        String adminId = constantMap.getString(Constants.ADMIN_ID);
        AdminAccountBean adminAccount = null;
        adminAccount = selectListByCurrencyId("", adminId, AccountTypeEnums.RENMINGBI.getCode());
        //增加通证币余额
        if (adminAccount != null) {
            AdminAccountBean up = new AdminAccountBean();
            up.setId(adminAccount.getId());
            up.setVersion(adminAccount.getVersion());
            adminAccount.setUsable(adminAccount.getUsable().add(orderMaster.getPaymentMoney()));   //加可用
            adminAccount.setAmount(adminAccount.getAmount().add(orderMaster.getPaymentMoney()));   //加总额
            adminAccount.setUpdateTime(DateUtils.getDBDate());
            adminAccount.setVersion(adminAccount.getVersion() + 1);
            update(adminAccount, new EntityWrapper<>(up));    //修改平台资金
        } else {
            AdminAccountBean adminAddAccount = new AdminAccountBean();
            adminAddAccount.setId(IdWorker.getNewInstance().nextIdToString());
            adminAddAccount.setUsable(orderMaster.getPaymentMoney());   //加可用
            adminAddAccount.setAmount(orderMaster.getPaymentMoney());   //加总额
            adminAddAccount.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
            adminAddAccount.setAdminId(adminId);
            adminAddAccount.setCreateTime(DateUtils.getDBDate());
            this.insert(adminAddAccount);//添加金额到平台
        }
        //添加平台记录
        AdminAccountDetailBean adminDetail = new AdminAccountDetailBean();
        adminDetail.setId(IdWorker.getNewInstance().nextIdToString());
        adminDetail.setAdminId(adminId);
        adminDetail.setAmount(orderMaster.getPaymentMoney());
        adminDetail.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
        adminDetail.setCreateTime(DateUtils.getDBDate());
        adminDetail.setUpdateTime(DateUtils.getDBDate());
        adminDetail.setChangesType(AccountChangesTypeEnums.INCOME.getCode());     //变更类型
        adminDetail.setCurrencyId(currencyId);
        adminDetail.setChangesUserId(orderMaster.getUserId()); //来源用户id
        adminDetail.setChangesSourceId(orderMaster.getMasterId());  //来源订单id
        adminDetail.setPayType(payType);  //支付方式
        adminDetail.setRemark("用户：" + orderMaster.getUserId() + " 订单：" + orderMaster.getMasterId() + " 支付：" + orderMaster.getPaymentMoney());
        adminAccountDetailService.insert(adminDetail); //添加记录
    }

    /**
     * 微信支付宝回调
     *
     * @param orderMaster
     * @throws Exception
     */
    @Transactional
    public void addPayAccountRMB(OrderMaster orderMaster) throws Exception {
        logger.info("回调订单添加平台资金账户：{}", JSONObject.toJSON(orderMaster));
        String adminId = constantMap.getString(Constants.ADMIN_ID);
        AdminAccountBean adminAccount = selectListByCurrencyId("", adminId, AccountTypeEnums.RENMINGBI.getCode());

        if (adminAccount == null) {
            AdminAccountBean adminAddAccount = new AdminAccountBean();
            adminAddAccount.setId(IdWorker.getNewInstance().nextIdToString());
            adminAddAccount.setUsable(orderMaster.getPaymentMoney());   //加可用
            adminAddAccount.setAmount(orderMaster.getPaymentMoney());   //加总额
            adminAddAccount.setAdminId(adminId);
            adminAddAccount.setCreateTime(DateUtils.getDBDate());
            adminAddAccount.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
            this.insert(adminAddAccount);//添加金额到平台

        } else {
            AdminAccountBean up = new AdminAccountBean();
            up.setId(adminAccount.getId());
            up.setVersion(adminAccount.getVersion());
            adminAccount.setUsable(adminAccount.getUsable().add(orderMaster.getPaymentMoney()));   //加可用
            adminAccount.setAmount(adminAccount.getAmount().add(orderMaster.getPaymentMoney()));   //加总额
            adminAccount.setUpdateTime(DateUtils.getDBDate());
            adminAccount.setVersion(adminAccount.getVersion() + 1);
            update(adminAccount, new EntityWrapper<>(up));    //修改平台资金
        }
        //添加平台记录
        AdminAccountDetailBean adminDetail = new AdminAccountDetailBean();
        adminDetail.setId(IdWorker.getNewInstance().nextIdToString());
        adminDetail.setAmount(orderMaster.getPaymentMoney());
        adminDetail.setAdminId(adminId);
        adminDetail.setCreateTime(DateUtils.getDBDate());
        adminDetail.setUpdateTime(DateUtils.getDBDate());
        adminDetail.setChangesType(AccountChangesTypeEnums.INCOME.getCode());     //变更类型
        adminDetail.setChangesUserId(orderMaster.getUserId()); //来源用户id
        adminDetail.setChangesSourceId(orderMaster.getMasterId());  //来源订单id
        adminDetail.setPayType(orderMaster.getPayType());  //支付方式
        adminDetail.setAccountType(AccountTypeEnums.RENMINGBI.getCode());
        adminDetail.setRemark("支付订单：" + orderMaster.getMasterId() + " 订单：" + orderMaster.getUserId() + " 支付：" + orderMaster.getPaymentMoney());
        adminAccountDetailService.insert(adminDetail); //添加记录
        logger.info("回调订单添加平台资金账户成功：{}", JSONObject.toJSON(orderMaster));
    }

    /**
     * 虚拟币支付操作平台钱包
     *
     * @param orderMaster
     * @param userAccountBeans
     */
    public void updateAdminCurrencyAccount(OrderMaster orderMaster, UserAccountBean userAccountBeans) {
        //给平台账户加资金
        String adminId = constantMap.getString(Constants.ADMIN_ID);
        AdminAccountBean adminAccount = selectListByCurrencyId(orderMaster.getCurrencyId(), adminId, AccountTypeEnums.TONGZHENG.getCode());

        //增加通证币余额
        if (adminAccount != null) {
            AdminAccountBean up = new AdminAccountBean();
            up.setId(adminAccount.getId());
            up.setVersion(adminAccount.getVersion());
            adminAccount.setUsable(adminAccount.getUsable().add(orderMaster.getPaycoinAmount()));   //加可用
            adminAccount.setAmount(adminAccount.getAmount().add(orderMaster.getPaycoinAmount()));   //加总额
            adminAccount.setUpdateTime(DateUtils.getDBDate());
            adminAccount.setVersion(adminAccount.getVersion() + 1);
            update(adminAccount, new EntityWrapper<>(up));    //修改平台资金
        } else {
            AdminAccountBean adminAddAccount = new AdminAccountBean();
            adminAddAccount.setId(IdWorker.getNewInstance().nextIdToString());
            adminAddAccount.setUsable(orderMaster.getPaycoinAmount());   //加可用
            adminAddAccount.setAmount(orderMaster.getPaycoinAmount());   //加总额
            adminAddAccount.setAccountType(AccountTypeEnums.TONGZHENG.getCode());
            adminAddAccount.setCurrencyId(orderMaster.getCurrencyId());
            adminAddAccount.setAdminId(adminId);
            adminAddAccount.setCreateTime(DateUtils.getDBDate());
            this.insert(adminAddAccount);//添加金额到平台
        }
        //添加平台记录
        AdminAccountDetailBean adminDetail = new AdminAccountDetailBean();
        adminDetail.setId(IdWorker.getNewInstance().nextIdToString());
        adminDetail.setAdminId(adminId);
        adminDetail.setCreateTime(DateUtils.getDBDate());
        adminDetail.setAmount(orderMaster.getPaycoinAmount());
        adminDetail.setAccountType(AccountTypeEnums.TONGZHENG.getCode());
        adminDetail.setChangesType(AccountChangesTypeEnums.INCOME.getCode());     //变更类型
        adminDetail.setCurrencyId(orderMaster.getCurrencyId());
        adminDetail.setChangesUserId(orderMaster.getUserId()); //来源用户id
        adminDetail.setChangesSourceId(orderMaster.getMasterId());  //来源订单id
        adminDetail.setPayType(orderMaster.getPayType());  //支付方式
        adminDetail.setRemark("用户：" + orderMaster.getUserId() + " 订单：" + orderMaster.getMasterId() + " 支付：" + orderMaster.getPaycoinAmount());
        adminAccountDetailService.insert(adminDetail); //添加记录
    }
}
