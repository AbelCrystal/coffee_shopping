package com.shopping.service.user;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.mapper.OrderCartMapper;
import com.shopping.redis.RedisUtil;
import com.shopping.service.mq.MQSender;
import com.shopping.service.mq.OrderCardQueueMessage;
import com.shopping.service.mq.OrderCardReturnMessage;
import com.shopping.service.mq.OrdersQueueMessage;
import com.shopping.service.order.LogisticsPriceService;
import com.shopping.service.order.OrderDeatilService;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.product.ProductInfoService;
import com.shopping.unit.BigDecimalUtil;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OrderNum;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.UserParame;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/6/25 0025 17:08
 * @Description: 购物车Service
 */
@Service("orderCartService")
public class OrderCartService extends ServiceImpl<OrderCartMapper, OrderCartBean> {

    Logger logger = LoggerFactory.getLogger(OrderCartService.class);
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private OrderDeatilService orderDeatilService;
    @Autowired
    private OrderCartService orderCartService;
    @Autowired
    private LogisticsPriceService logisticsPriceService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${order.timeOut}")
    private long orderTimeOut;


    @Autowired
    private MQSender sender;

    /**
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 17:08
     */
    public List<OrderCartBean> selectListById(UserParame userParame) {
        try {
            EntityWrapper<OrderCartBean> orderCartWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(userParame.getCartId())) {
                orderCartWrapper.eq("cart_id", userParame.getCartId());
            }
//            if (!Utils.isEmpty(userParame.getId())) {
//                orderCartWrapper.eq("customer_id", userParame.getId());
//            }
            if (!Utils.isEmpty(userParame.getWxUUId())) {
                orderCartWrapper.eq("wx_uuid", userParame.getWxUUId());
            }
            if (!Utils.isEmpty(userParame.getAliUUId())) {
                orderCartWrapper.eq("ali_uuid", userParame.getAliUUId());
            }
            orderCartWrapper.eq("is_delete", GlobalIsDeleteEnums.IS_NORMAL.getCode());
//            orderCartWrapper.orderBy("update_time", false);
            return selectList(orderCartWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 根据id数组获取收藏夹列表
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/27 0027 15:11
     */
    public List<OrderCartBean> selectListByArrayId(String[] cartIdArray) {
        try {
            List<OrderCartBean> orderCartBeanList = new ArrayList<>();
            for (int i = 0; i < cartIdArray.length; i++) {
                OrderCartBean orderCartBean = selectById(cartIdArray[i]);
                orderCartBeanList.add(orderCartBean);
            }
            return orderCartBeanList;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 根据用户id商品id查询购物车信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/4 0004 17:39
     */
    public OrderCartBean selectByProductId(UserParame userParame) {
        try {
            EntityWrapper<OrderCartBean> orderCartWrapper = new EntityWrapper<>();
            if (!StringUtils.isEmpty(userParame.getId())) {
                orderCartWrapper.eq("customer_id", userParame.getId());
            }
            if (!StringUtils.isEmpty(userParame.getAliUUId())) {
                orderCartWrapper.eq("ali_uuid", userParame.getAliUUId());
            }
            if (!StringUtils.isEmpty(userParame.getWxUUId())) {
                orderCartWrapper.eq("wx_uuid", userParame.getWxUUId());
            }
            if (!StringUtils.isEmpty(userParame.getProductAttr())) {
                orderCartWrapper.eq("product_attr", userParame.getProductAttr());
            }
            orderCartWrapper.eq("product_id", userParame.getProductId());
            orderCartWrapper.ne("is_delete", GlobalIsDeleteEnums.IS_DISABLE.getCode());
            List<OrderCartBean> orderCartBeanList = selectList(orderCartWrapper);
            if (orderCartBeanList.size() > 0) {
                return orderCartBeanList.get(0);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 购物车下单
     *
     * @param mm
     * @return
     * @throws IllegalAccessException
     */
    @Transactional
    public void placeOrder(OrderCardQueueMessage mm) throws Exception {

        if (!mm.getOrderId().isEmpty() && !mm.getCartIdArray().isEmpty()) {
            OrderMaster orderMaster = new OrderMaster();
            BeanUtils.copyProperties(mm, orderMaster);
            orderMaster.setMasterId(mm.getOrderId()); //主订单id
            orderMaster.setUserId(mm.getUserId());    //用户id
            orderMaster.setSupplierId(mm.getSupplierId()); //供应商
            orderMaster.setDistrictMoney(new BigDecimal(0));    //优惠金额 默认为0
            orderMaster.setSettlementStatus(OrderSettlementEnums.NOT_SETTLEMENT.getCode()); //未结算
            orderMaster.setTableNo(mm.getTableNo());
            //TODO 积分先默认为0
            orderMaster.setOrderPoint(0);   //积分
            orderMaster.setCreateTime(DateUtils.getDBDate());   //下单时间
            orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode()); //待付款
            orderMaster.setOrdersrc(mm.getOrderSrc());  //订单来源
            orderMaster.setIsDelete(GlobalIsDeleteEnums.IS_NORMAL.getCode());   //正常\
            long dinnerCode = 1;
            if (redisUtil.kayExist(RedisPrefixEmums.ORDEER_DINING_CODE_PREFIX.getCode())) {
                dinnerCode = redisUtil.increment(RedisPrefixEmums.ORDEER_DINING_CODE_PREFIX.getCode(), 1);
            } else {
                dinnerCode = redisUtil.increment(RedisPrefixEmums.ORDEER_DINING_CODE_PREFIX.getCode(), 100);
            }
            orderMaster.setDinnerCode(dinnerCode);
            List<OrderCartBean> orderCartBeans = orderCartService.selectBatchIds(mm.getCartIdArray());
            BigDecimal payMoney = new BigDecimal(0);
            List<OrderDetailBean> orderDetailBeans = new ArrayList<>();
            for (OrderCartBean orderCartBean : orderCartBeans) {
                payMoney = payMoney.add(orderCartBean.getRmbPrice().multiply(new BigDecimal(orderCartBean.getProductCount())));//总支付金额
                EntityWrapper<ProductInfo> qryProductInfoWrapper = new EntityWrapper<>();
                qryProductInfoWrapper.eq("product_id",orderCartBean.getProductId());
                ProductInfo productInfo = productInfoService.selectOne(qryProductInfoWrapper);
                orderMaster.setSupplierName(productInfo.getSupplierName());
                //订单详情
                OrderDetailBean orderDetailBean = new OrderDetailBean();
                orderDetailBean.setDetailId(IdWorker.getNewInstance().nextIdToString());
                orderDetailBean.setProductId(orderCartBean.getProductId());
                orderDetailBean.setProductName(productInfo.getProductName());
                orderDetailBean.setProductImg(productInfo.getMasterPicUrl());
                orderDetailBean.setProductNum(orderCartBean.getProductCount());
                orderDetailBean.setProductAttr(orderCartBean.getProductAttr());
                orderDetailBean.setRmbPrice(orderCartBean.getRmbPrice());
                orderDetailBean.setCreateTime(DateUtils.getDBDate());
                orderDetailBean.setUpdateTime(DateUtils.getDBDate());
                orderDetailBean.setMasterId(orderMaster.getMasterId());
                orderDetailBeans.add(orderDetailBean);
            }
            orderMaster.setTotalMoney(payMoney);     //商品总价cS
            orderMaster.setPaymentMoney(payMoney);  //支付金额
            //提交订单
            orderMasterService.insert(orderMaster);
            orderDeatilService.insertBatch(orderDetailBeans);
            redisUtil.setForTimeMIN(RedisPrefixEmums.ORDEER_STATE_WAIT_PAYMENT_PREFIX.getCode()+ ":" + orderMaster.getMasterId(), orderMaster.getMasterId(), orderTimeOut);

        } else {
            throw new RuntimeException(MessageEnums.ORDERS_FAILR.getDesc());
        }

    }
}
