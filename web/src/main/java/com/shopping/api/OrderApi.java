package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.alipay.bean.WapPayRequest;
import com.shopping.alipay.service.AlipayApiService;
import com.shopping.alipay.service.AlipayApiServiceImpl;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.order.OrderDeatilService;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.order.PayMoneyService;
import com.shopping.service.order.RmbRechargeService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.IpUtil;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.*;
import com.shopping.vo.user.MyInfoVo;
import com.shopping.wxPay.Pay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(value = "/api/order", tags = "订单操作")
@RestController
@RequestMapping(value = "/api/order")
public class OrderApi {

    Logger logger = LoggerFactory.getLogger(OrderApi.class);
    @Autowired
    private OrderMasterService orderMasterService;

//    @Autowired
//    private RedisUtil redisUtil;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private OrderDeatilService orderDeatilService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private PayMoneyService payMoneyService;

    @ApiOperation(value = "个人中心信息页", notes = "个人中心信息页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/myInfo")
    @UserLoginToken
    public MessageVO<MyInfoVo> myInfo(HttpSession session) {
        MyInfoVo result = new MyInfoVo();
        User user = (User) session.getAttribute(session.getId());
        logger.info("个人信息{}", JSONObject.toJSONString(user));
        List<UserAccountBean> listAccount = userAccountService.selectListByUserId(user.getId());
        result.setRmbAssets(new BigDecimal(0));
        result.setAssets(new BigDecimal(0));
        if (listAccount != null && listAccount.size() > 0) {
            for (UserAccountBean userAccountBean : listAccount) {
                if (AccountTypeEnums.TONGZHENG.getCode().equals(userAccountBean.getAccoutType())) {
                    result.setAssets(userAccountBean.getAmount());
                    EntityWrapper<CurrencyBean> qryCurrencyBeanWrapper = new EntityWrapper<>();
                    qryCurrencyBeanWrapper.eq("id", userAccountBean.getCurrencyId());
                    CurrencyBean currencyBean = currencyService.selectOne(qryCurrencyBeanWrapper);
                    if (currencyBean != null) {
                        result.setCurrencyName(currencyBean.getCurrencyName());
                    }
                } else {
                    result.setRmbAssets(userAccountBean.getAmount());
                }
            }
        }
        result.setUserId(user.getId());
        result.setPhone(user.getPhone());
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "得到充值金额", notes = "得到充值金额", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/getPayMoney")
    public MessageVO<List<PayMoney>> getPayMoney(HttpSession session) {
        EntityWrapper<PayMoney> qryWrapper = new EntityWrapper<>();
        qryWrapper.orderBy("money", true);
        List<PayMoney> list = payMoneyService.selectList(qryWrapper);
        return MessageVO.builder(list)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "通过状态获取订单", notes = "-1:全部 0:待付款 1:已支付 2:已出单 3：交易关闭", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", name = "pageNum", paramType = "query", dataType = "int"),
            @ApiImplicitParam(value = "页面大小", name = "pageSize", paramType = "query", dataType = "int")
    })
    @GetMapping("/getOrderListByState")
    @UserLoginToken
    public MessageVO<BaseListResult<List<OrderVo>>> getOrderListByState(@RequestParam(defaultValue = "1", required = false) int pageNum,
                                                                        @RequestParam(defaultValue = "10", required = false) int pageSize, HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        Page<List<OrderVo>> page = PageHelper.startPage(pageNum, pageSize);
        List<OrderVo> orders = orderMasterService.getOrderListByState(OrderEmums.ORDEER_STATE_ALL.getCode(), user);
        BaseListResult result = new BaseListResult();
        result.setListReult(orders);
        result.setPageNum(pageNum);
        result.setTotal(page.getTotal());
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "查看出单列表", notes = "查看出单列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", name = "pageNum", paramType = "query", dataType = "int"),
            @ApiImplicitParam(value = "页面大小", name = "pageSize", paramType = "query", dataType = "int"),
            @ApiImplicitParam(value = "订单状态:1为未出单，2为已出单", name = "orderStatus", paramType = "query", dataType = "String")
    })
    @GetMapping("/getOrderPayedList")
    public MessageVO<BaseListResult<List<OrderMaster>>> getOrderPayedList(@RequestParam(defaultValue = "1", required = true) int pageNum,
                                                                          @RequestParam(defaultValue = "10", required = true) int pageSize
            , @RequestParam(required = true) String orderStatus) {

        Page<List<OrderMaster>> page = PageHelper.startPage(pageNum, pageSize);
        List<OrderMaster> orders = orderMasterService.getOrderPayedList(orderStatus);
        BaseListResult result = new BaseListResult();
        result.setListReult(orders);
        result.setPageNum(pageNum);
        result.setTotal(page.getTotal());
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }


    @ApiOperation(value = "出单订单详情", notes = "出单订单详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "orderStatus", value = "订单状态：1为已支付2为已出单", required = true, dataType = "String")
    })
    @GetMapping("/getPayedOrderDetail")
    public MessageVO<OrderDetailVo> getPayedOrderDetail(String orderId,String orderStatus) {
        EntityWrapper<OrderMaster> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("master_id", orderId);
        qryWrapper.eq("order_status", orderStatus);
        OrderMaster orderMaster = orderMasterService.selectOne(qryWrapper);
        OrderDetailVo vo = new OrderDetailVo();
        if (orderMaster != null) {
            if (!StringUtils.isEmpty(orderMaster.getCurrencyId())) {
                EntityWrapper<CurrencyBean> qryCurrencyBeanWrapper = new EntityWrapper<>();
                qryCurrencyBeanWrapper.eq("id", orderMaster.getCurrencyId());
                CurrencyBean currencyBean = currencyService.selectOne(qryCurrencyBeanWrapper);
                if (currencyBean != null) {
                    orderMaster.setCurrencyName(currencyBean.getCurrencyName());
                }
            }
            EntityWrapper<OrderDetailBean> qryDetailWrapper = new EntityWrapper<>();
            qryDetailWrapper.eq("master_id", orderId);
            List<OrderDetailBean> listDetails = orderDeatilService.selectList(qryDetailWrapper);
            BeanUtils.copyProperties(orderMaster, vo);
            vo.setOrderDetailBeanList(listDetails);
        }else {
            return MessageVO.builder()
                    .msgCode(MessageEnums.ORDER_OUTED)
                    .build();
        }
        return MessageVO.builder(vo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "出单", notes = "出单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = false, dataType = "String")
    })
    @PostMapping("/outOrder")
    public MessageVO outOrder(String orderId) {
        EntityWrapper<OrderMaster> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("master_id", orderId);
        qryWrapper.eq("order_status", OrderEmums.ORDEER_STATE_WAIT_DELIVER.getCode());
        OrderMaster orderMaster = orderMasterService.selectOne(qryWrapper);
        if (orderMaster == null) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.ORDER_OUTED)
                    .build();
        } else {
            orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_BILL_ISSUED.getCode());
            orderMaster.setModifiedTime(DateUtils.getDBDate());
            orderMasterService.updateById(orderMaster);
        }
        orderMasterService.selectOne(qryWrapper);
        return MessageVO.builder()
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }


    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = false, dataType = "String")
    })
    @PostMapping("/deleteOrder")
    @UserLoginToken
    public MessageVO<RefundVo> deleteOrder(String orderId) {
        EntityWrapper<OrderMaster> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("master_id", orderId);
        qryWrapper.in("order_status", new Object[]{OrderEmums.TRANSACTION_CLOSE.getCode(), OrderEmums.TRANSACTION_FINISH.getCode()});
        OrderMaster orderMaster = orderMasterService.selectOne(qryWrapper);
        if (Objects.nonNull(orderMaster)) {
            orderMaster.setIsDelete("1");//订单删除
            OrderMaster upOrderMaster = new OrderMaster();
            upOrderMaster.setMasterId(orderId);
            upOrderMaster.setVersion(orderMaster.getVersion());
            orderMaster.setVersion(orderMaster.getVersion() + 1);
            orderMaster.setModifiedTime(DateUtils.getDBDate());
            orderMasterService.update(orderMaster, new EntityWrapper<>(upOrderMaster));
            return MessageVO.builder()
                    .msgCode(MessageEnums.DELETE_SUCCESS)
                    .build();
        }
        return MessageVO.builder("订单尚未完结，无法删除！")
                .msgCode(MessageEnums.DELETE_FAIL)
                .build();
    }

    @ApiOperation(value = "订单详情", notes = "订单详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = false, dataType = "String")
    })
    @GetMapping("/getOrderDetail")
    @UserLoginToken
    public MessageVO<OrderDetailVo> getOrderDetail(String orderId, HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        EntityWrapper<OrderMaster> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("master_id", orderId);
        OrderMaster orderMaster = orderMasterService.selectOne(qryWrapper);
        OrderDetailVo vo = new OrderDetailVo();
        if (orderMaster != null) {
            if (!StringUtils.isEmpty(orderMaster.getCurrencyId())) {
                EntityWrapper<CurrencyBean> qryCurrencyBeanWrapper = new EntityWrapper<>();
                qryCurrencyBeanWrapper.eq("id", orderMaster.getCurrencyId());
                CurrencyBean currencyBean = currencyService.selectOne(qryCurrencyBeanWrapper);
                if (currencyBean != null) {
                    orderMaster.setCurrencyName(currencyBean.getCurrencyName());
                }
            }
            EntityWrapper<OrderDetailBean> qryDetailWrapper = new EntityWrapper<>();
            qryDetailWrapper.eq("master_id", orderId);
            List<OrderDetailBean> listDetails = orderDeatilService.selectList(qryDetailWrapper);
            BeanUtils.copyProperties(orderMaster, vo);
            vo.setOrderDetailBeanList(listDetails);
        }
        return MessageVO.builder(vo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

}
