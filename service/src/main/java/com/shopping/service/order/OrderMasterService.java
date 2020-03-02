package com.shopping.service.order;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.mapper.OrderMasterMapper;
import com.shopping.redis.RedisUtil;
import com.shopping.service.admin.AdminAccountService;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.product.ProductInfoService;
import com.shopping.service.user.OrderCartService;
import com.shopping.service.user.UserAccountDetailService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.BigDecimalUtil;
import com.shopping.unit.DateUtils;
import com.shopping.unit.MathUtils;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("orderMasterService")
public class OrderMasterService extends ServiceImpl<OrderMasterMapper, OrderMaster> {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderCartService orderCartService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderDeatilService orderDeatilService;
    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private AdminAccountService adminAccountService;
    @Value("${order.timeOut}")
    private long orderTimeOut;

    public List<OrderVo> getOrderListByState(String orderState, User user) {

        List<OrderVo> resultList = new ArrayList<>();
        resultList = baseMapper.selectListbByType(user);
        for (OrderVo orderVo :
                resultList) {
            EntityWrapper<OrderDetailBean> orderDetailBeanEntityWrapper = new EntityWrapper<>();
            orderDetailBeanEntityWrapper.eq("master_id", orderVo.getMasterId());
            List<OrderDetailBean> oderDetails = orderDeatilService.selectList(orderDetailBeanEntityWrapper);
            orderVo.setOrderDetails(oderDetails);
        }
        return resultList;
    }

    @Transactional
    public boolean cancelOrder(String orderId, String cancelReason) throws RuntimeException {
        boolean flag = false;
        EntityWrapper<OrderMaster> orderMasterWrapper = new EntityWrapper<>();
        orderMasterWrapper.eq("master_id", orderId);
        orderMasterWrapper.eq("order_status", OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode());
        OrderMaster order = this.selectOne(orderMasterWrapper);
        if (order != null) {
            order.setMasterId(orderId);
            order.setOrderStatus(OrderEmums.ORDEER_STATE_TRANSACTION_CLOSE.getCode());
            order.setCancelReason(cancelReason);
            order.setModifiedTime(DateUtils.getDBDate());
            User user = new User();
            user.setId(order.getUserId());
            user.setWxUUId(order.getWxUUId());
            user.setAliUUId(order.getAliUUId());
            this.clearOrderCard(user);//清空购物车
            baseMapper.delete(orderMasterWrapper);
        }

        flag = true;
        return flag;

    }

    /**
     * @description: 根据用户id及主订单id 查询订单信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 11:45
     */
    public OrderMaster getOrderByUserId(String userId, String masterId) {
        EntityWrapper<OrderMaster> orderMasterEntityWrapper = new EntityWrapper<>();
        orderMasterEntityWrapper.eq("user_id", userId);
        orderMasterEntityWrapper.eq("master_id", masterId);
        orderMasterEntityWrapper.eq("is_delete", GlobalIsDeleteEnums.IS_NORMAL.getCode());
        List<OrderMaster> orderMasters = selectList(orderMasterEntityWrapper);
        if (orderMasters.size() > 0) {
            return orderMasters.get(0);
        }
        return null;
    }

    /**
     * @description: 减库存，下订单
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/5 0005 11:11
     */
    @Transactional
    public OrderMaster addProductOrder(OrderMaster orderMaster, Set<OrderDetailBean> orderDetailBeanSet, Set<OrderCartBean> orderCartBeanSet) throws RuntimeException {
        for (OrderDetailBean detailBean : orderDetailBeanSet) {
            orderDeatilService.insert(detailBean); //增加详情订单
        }
        insert(orderMaster); //增加主订单
        //存Redis
        redisUtil.setForTimeMIN(RedisPrefixEmums.ORDEER_STATE_WAIT_PAYMENT_PREFIX.getCode() + ":" + orderMaster.getMasterId(), orderMaster.getMasterId(), orderTimeOut);
        return selectById(orderMaster.getMasterId());
    }

    /**
     * @description:
     * @param: 支付修改订单及账户通证状态
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 19:58
     */
    @Transactional
    public boolean updateOrderMasterByPay(OrderMaster orderMaster, UserAccountBean userAccountBeans, String payType, User user) throws Exception {
        //操作用户金额
        userAccountService.orderPayUserAccount(orderMaster, userAccountBeans, payType, user);
        //添加平台收益
        adminAccountService.updateAdminAccount(orderMaster, payType, userAccountBeans.getCurrencyId());
        //修改订单状态
        orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_WAIT_DELIVER.getCode());    //已支付
        orderMaster.setPayTime(DateUtils.getDBDate()); //支付时间
        orderMaster.setModifiedTime(DateUtils.getDBDate());    //最后修改时间
        orderMaster.setPayType(payType);
        orderMaster.setUserId(user.getId());
        orderMaster.setAliUUId(user.getAliUUId());
        orderMaster.setWxUUId(user.getWxUUId());
        orderMasterService.updateById(orderMaster);
        EntityWrapper<OrderDetailBean> orderMasterEntityWrapper = new EntityWrapper<>();
        orderMasterEntityWrapper.eq("master_id", orderMaster.getMasterId());
        List<OrderDetailBean> orderDetailBeanList = orderDeatilService.selectList(orderMasterEntityWrapper);
        for (OrderDetailBean orderDetailBean :
                orderDetailBeanList) {
            ProductInfo productInfo = productInfoService.getProductInfoById(orderDetailBean.getProductId());
            productInfo.setSalesVolume(productInfo.getSalesVolume() + orderDetailBean.getProductNum());//添加商品销量
            productInfo.setModifiedTime(DateUtils.getDBDate());
            productInfoService.updateById(productInfo);

        }
        return true;
    }

    @Transactional
    public void orderPayCallBack(String orderId, String payType) throws Exception {
        EntityWrapper<OrderMaster> orderMasterEntityWrapper = new EntityWrapper<>();
        orderMasterEntityWrapper.eq("master_id", orderId);
        orderMasterEntityWrapper.eq("order_status", OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode());
        OrderMaster orderMaster = orderMasterService.selectOne(orderMasterEntityWrapper);
        if (orderMaster == null) {
            throw new RuntimeException("订单重复回调或下单失败：" + orderId);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_WAIT_DELIVER.getCode());    //已支付
        orderMaster.setPayTime(DateUtils.getDBDate()); //支付时间
        orderMaster.setPayType(payType);
        orderMaster.setModifiedTime(DateUtils.getDBDate());    //最后修改时间
        //修改订单状态
        orderMasterService.updateById(orderMaster);
        //添加平台金额
        adminAccountService.addPayAccountRMB(orderMaster);
        User user = new User();
        user.setAliUUId(orderMaster.getAliUUId());
        user.setWxUUId(orderMaster.getWxUUId());
        user.setId(orderMaster.getUserId());
        EntityWrapper<OrderDetailBean> orderDetailBeanEntityWrapper = new EntityWrapper<>();
        orderDetailBeanEntityWrapper.eq("master_id", orderMaster.getMasterId());
        List<OrderDetailBean> orderDetailBeanList = orderDeatilService.selectList(orderDetailBeanEntityWrapper);
        for (OrderDetailBean orderDetailBean :
                orderDetailBeanList) {
            ProductInfo productInfo = productInfoService.getProductInfoById(orderDetailBean.getProductId());
            productInfo.setSalesVolume(productInfo.getSalesVolume() + orderDetailBean.getProductNum());//添加商品销量
            productInfo.setModifiedTime(DateUtils.getDBDate());
            productInfoService.updateById(productInfo);

        }
        orderMasterService.clearOrderCard(user);//清空购物车
    }

    public void clearOrderCard(User user) {
        if (user != null) {


            EntityWrapper<OrderCartBean> orderCartBeanEntityWrapper = new EntityWrapper<>();
//            if (!StringUtils.isEmpty(user.getId())) {
//                orderCartBeanEntityWrapper.eq("customer_id", user.getId());
//            }
            if (!StringUtils.isEmpty(user.getWxUUId())) {
                orderCartBeanEntityWrapper.eq("wx_uuid", user.getWxUUId());
            }
            if (!StringUtils.isEmpty(user.getAliUUId())) {
                orderCartBeanEntityWrapper.eq("ali_uuid", user.getAliUUId());
            }
            orderCartService.delete(orderCartBeanEntityWrapper);
        }

    }

    public List<OrderMaster> getOrderPayedList(String orderStatus) {
        EntityWrapper<OrderMaster> orderMasterEntityWrapper = new EntityWrapper<>();
        orderMasterEntityWrapper.eq("order_status", orderStatus);
        if (OrderEmums.ORDEER_STATE_WAIT_DELIVER.getCode().equals(orderStatus)) {
            orderMasterEntityWrapper.orderBy("pay_time", true);
        } else {
            orderMasterEntityWrapper.orderBy("modified_time", false);
        }


        List<OrderMaster> orderMasters = this.selectList(orderMasterEntityWrapper);
        return orderMasters;
    }

    public String updateOrderMasterByCurrencyPay(OrderMaster orderMaster, UserAccountBean userAccountBeans, User user) throws RuntimeException {

        BigDecimal payCoinRatio = new BigDecimal(0);
        //修改订单状态
        EntityWrapper<CurrencyBean> currencyBeanEntityWrapper = new EntityWrapper<>();
        currencyBeanEntityWrapper.eq("id", userAccountBeans.getCurrencyId());
        CurrencyBean currencyBean = currencyService.selectOne(currencyBeanEntityWrapper);
        if (currencyBean == null) {
            throw new RuntimeException("虚拟币支付没有此币种");
        }
        if (CurrencyTypeEnues.BTC.getCode().equals(currencyBean.getCurrencyName())) {
            payCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.BTC.getCode()));
            orderMaster.setPayType(OrderPayTypeEnums.BTC.getCode());
        } else if (CurrencyTypeEnues.ETH.getCode().equals(currencyBean.getCurrencyName())) {
            payCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.ETH.getCode()));
            orderMaster.setPayType(OrderPayTypeEnums.ETH.getCode());
        } else {
            payCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.KEO.getCode()));
            orderMaster.setPayType(OrderPayTypeEnums.KEO.getCode());
        }
        BigDecimal paycoin =MathUtils.divide8(orderMaster.getPaymentMoney(), payCoinRatio);
        if ( userAccountBeans.getUsable().compareTo(paycoin) < 0) {
            return MessageEnums.INSUFFICIENT_FUNDS.getCode();        //资金不足
        }
        //人民币和币的比例
        BigDecimal payScale= MathUtils.divide8(new BigDecimal(1), payCoinRatio);
        orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_WAIT_DELIVER.getCode());    //已支付
        orderMaster.setPayTime(DateUtils.getDBDate()); //支付时间
        orderMaster.setModifiedTime(DateUtils.getDBDate());    //最后修改时间
        orderMaster.setUserId(user.getId());
        orderMaster.setAliUUId(user.getAliUUId());
        orderMaster.setWxUUId(user.getWxUUId());
        orderMaster.setPaycoinAmount(paycoin);
        orderMaster.setPaycoinRatio(payScale);
        orderMaster.setCurrencyId(currencyBean.getId());
        orderMaster.setCurrencyName(currencyBean.getCurrencyName());
        EntityWrapper<OrderDetailBean> orderDetailBeanEntityWrapper = new EntityWrapper<>();
        orderDetailBeanEntityWrapper.eq("master_id", orderMaster.getMasterId());
        List<OrderDetailBean> orderDetailBeanList = orderDeatilService.selectList(orderDetailBeanEntityWrapper);
        if (orderDetailBeanList != null && orderDetailBeanList.size() > 0) {
            for (OrderDetailBean orderDetailBean :
                    orderDetailBeanList) {
                //修改支付币种
                orderDetailBean.setCurrencyId(currencyBean.getId());
                orderDetailBean.setCoinPrice(orderDetailBean.getRmbPrice().multiply(payScale));
                orderDetailBean.setCoinRmbRatio(payScale);
                orderDetailBean.setUpdateTime(DateUtils.getDBDate());
                //添加商品销量
                ProductInfo productInfo = productInfoService.getProductInfoById(orderDetailBean.getProductId());
                productInfo.setSalesVolume(productInfo.getSalesVolume() + orderDetailBean.getProductNum());//添加商品销量
                productInfo.setModifiedTime(DateUtils.getDBDate());
                productInfoService.updateById(productInfo);
            }
        }
        //修改订单详情
        orderDeatilService.updateBatchById(orderDetailBeanList);
        //修改主订单
        orderMasterService.updateById(orderMaster);
        //操作用户金额
        userAccountService.orderPayUserCurrencyAccount(orderMaster, userAccountBeans, user);
        //添加平台收益
        adminAccountService.updateAdminCurrencyAccount(orderMaster, userAccountBeans);
        return MessageEnums.SUCCESS.getCode();

    }
}
