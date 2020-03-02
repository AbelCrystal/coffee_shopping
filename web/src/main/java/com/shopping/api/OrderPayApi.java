package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shopping.alipay.bean.WapPayRequest;
import com.shopping.alipay.service.AlipayApiService;
import com.shopping.alipay.service.AlipayApiServiceImpl;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.CurrencyBean;
import com.shopping.entity.OrderMaster;
import com.shopping.entity.User;
import com.shopping.entity.UserAccountBean;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.IpUtil;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.PaymentOfOrderVo;
import com.shopping.vo.order.VirtualCurrencyVo;
import com.shopping.vo.order.WXPayVo;
import com.shopping.wxPay.Pay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(value = "/api/orderPay", tags = "订单支付")
@RestController
@RequestMapping(value = "/api/orderPay")
public class OrderPayApi {

    Logger logger = LoggerFactory.getLogger(OrderPayApi.class);
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private UserAccountService userAccountService;
    @Value("${pay.aliPayCallback}")
    private String aliPayCallback;
    @Value("${pay.aliPayFinshBack}")
    private String aliPayFinshBack;

    @Autowired
    private Pay pay;
    @Autowired
    private CurrencyService currencyService;

    /**
     * 余额支付
     *
     * @param parame
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "余额支付", notes = "余额支付", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/paymentOfOrder")
    @UserLoginToken
    public MessageVO paymentOfOrder(@Valid @RequestBody PaymentOfOrderVo parame,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute(request.getSession().getId());
        BigDecimal payment = new BigDecimal(0);
        if (StringUtils.isEmpty(user.getId())) {
            throw new RuntimeException("PLEASELOGIN");
        }
        EntityWrapper<OrderMaster> qryOrderMasterBeanWrapper = new EntityWrapper<>();
        qryOrderMasterBeanWrapper.eq("master_id", parame.getOrderId());
        OrderMaster orderMaster = orderMasterService.selectOne(qryOrderMasterBeanWrapper);   //查询订单
        if (null == orderMaster) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        if (!orderMaster.getOrderStatus().equals(OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode())) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        payment = orderMaster.getPaymentMoney();
        UserAccountBean userAccountBeans = userAccountService.selectListByCurrencyId("", user.getId(), AccountTypeEnums.RENMINGBI.getCode());   //查询用户币种信息
        if (null == userAccountBeans || userAccountBeans.getUsable().compareTo(payment) < 0) {
            return MessageVO.builder().msgCode(MessageEnums.INSUFFICIENT_FUNDS).build();        //资金不足
        }
        orderMasterService.updateOrderMasterByPay(orderMaster, userAccountBeans, OrderPayTypeEnums.USERBALANCE.getCode(), user);  //支付修改信息
        orderMasterService.clearOrderCard(user);//清空购物车
        return MessageVO.builder().msgCode(MessageEnums.PAYMENT_SUCCESS).build();

    }

    /**
     * 余额支付
     *
     * @param parame
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "虚拟币支付", notes = "虚拟币支付", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/virtualCurrencyPayOrder")
    @UserLoginToken
    public MessageVO virtualCurrencyPayOrder(@Valid @RequestBody VirtualCurrencyVo parame,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute(request.getSession().getId());
        BigDecimal payment = new BigDecimal(0);
        if (StringUtils.isEmpty(user.getId())) {
            throw new RuntimeException("PLEASELOGIN");
        }
        EntityWrapper<OrderMaster> qryOrderMasterBeanWrapper = new EntityWrapper<>();
        qryOrderMasterBeanWrapper.eq("master_id", parame.getOrderId());
        OrderMaster orderMaster = orderMasterService.selectOne(qryOrderMasterBeanWrapper);   //查询订单
        if (null == orderMaster) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        if (!orderMaster.getOrderStatus().equals(OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode())) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        UserAccountBean userAccountBeans = userAccountService.selectListByCurrencyId(parame.getCurrencyId(), user.getId(), AccountTypeEnums.TONGZHENG.getCode());   //查询用户币种信息
        if ( userAccountBeans==null) {
            return MessageVO.builder().msgCode(MessageEnums.INSUFFICIENT_FUNDS).build();
        }
        String code=orderMasterService.updateOrderMasterByCurrencyPay(orderMaster, userAccountBeans, user);  //支付修改信息
        if(MessageEnums.FAIL.getCode().equals(code)){
            return MessageVO.builder().msgCode(MessageEnums.INSUFFICIENT_FUNDS).build();
        }
        orderMasterService.clearOrderCard(user);//清空购物车
        return MessageVO.builder().msgCode(MessageEnums.PAYMENT_SUCCESS).build();

    }

    /**
     * @description: 订单支付
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 17:27
     */
    @ApiOperation(value = "微信支付", notes = "微信支付", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/wxPay")
    @UserLoginToken
    public MessageVO<Map<String, String>> wxPay(@Valid @RequestBody WXPayVo parame,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        User user = (User) request.getSession().getAttribute(request.getSession().getId());
        EntityWrapper<OrderMaster> qryOrderMasterBeanWrapper = new EntityWrapper<>();
        qryOrderMasterBeanWrapper.eq("master_id", parame.getOrderId());
        OrderMaster orderMaster = orderMasterService.selectOne(qryOrderMasterBeanWrapper);   //查询订单
        if (null == orderMaster) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        logger.info("订单:" + parame.getOrderId() + "进入微信支付");
        if (!orderMaster.getOrderStatus().equals(OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode())) {
            return MessageVO.builder().msgCode(MessageEnums.DATA_EXCEPTION).build();   //订单信息异常
        }
        Map<String, String> map = pay.pay(orderMaster.getPaymentMoney(), orderMaster.getMasterId(), user.getWxUUId(), IpUtil.getIpAddr(request), orderMaster.getTableNo(), WXPayTypeEmums.ORDER_TYPE.getCode());
        if (map != null) {
            logger.info("微信支付成功返回参数：{}", JSONObject.toJSONString(map));
            return MessageVO.builder(map).msgCode(MessageEnums.PAYMENT_SUCCESS).build();
        } else {
            return MessageVO.builder().msgCode(MessageEnums.PAYMENT_FAIL).build();
        }

    }

    @ApiOperation(value = "支付宝支付", notes = "支付宝支付", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true, dataType = "String"),
    })
    @PostMapping("/aliPay")
    @UserLoginToken
    public void aliPay(String orderId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EntityWrapper<OrderMaster> qryOrderMasterBeanWrapper = new EntityWrapper<>();
        qryOrderMasterBeanWrapper.eq("master_id", orderId);
        OrderMaster orderMaster = orderMasterService.selectOne(qryOrderMasterBeanWrapper);   //查询订单
        if (null == orderMaster) {
            throw new RuntimeException(MessageEnums.DATA_EXCEPTION.getCode());   //订单信息异常
        }
        if (!orderMaster.getOrderStatus().equals(OrderEmums.ORDEER_STATE_WAIT_PAYMENT.getCode())) {
            throw new RuntimeException(MessageEnums.DATA_EXCEPTION.getCode());   //订单信息异常
        }
        logger.info("订单:" + orderId + "---》进入支付宝支付");
        AlipayApiService service = new AlipayApiServiceImpl();
        WapPayRequest wapPayRequest = new WapPayRequest(orderMaster.getMasterId(), orderMaster.getPaymentMoney().doubleValue(), "景红堂");
        wapPayRequest.setBody("订单号:" + orderMaster.getMasterId());
        String notifyUrl = aliPayCallback; // 回调地址
        //TODO 待支付成功返回页面
        String returnUrl = aliPayFinshBack;// 支付完成后跳转地址
        service.wapPay(wapPayRequest, returnUrl, notifyUrl, response);

    }

    @ApiOperation(value = "虚拟币币种列表", notes = "虚拟币币种列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/getCurrencyList")
    public MessageVO<List<CurrencyBean>> getCurrencyList() {
        EntityWrapper<CurrencyBean> currencyBeanEntityWrapper = new EntityWrapper<>();
        List<CurrencyBean> currencyBeanList = currencyService.selectList(currencyBeanEntityWrapper);
        return MessageVO.builder(currencyBeanList)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

}
