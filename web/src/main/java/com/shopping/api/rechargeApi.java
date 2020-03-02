package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.shopping.alipay.bean.WapPayRequest;
import com.shopping.alipay.service.AlipayApiService;
import com.shopping.alipay.service.AlipayApiServiceImpl;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.RmbRecharge;
import com.shopping.entity.User;
import com.shopping.enums.MessageEnums;
import com.shopping.enums.OrderPayTypeEnums;
import com.shopping.enums.WXPayTypeEmums;
import com.shopping.service.order.RmbRechargeService;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.IpUtil;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.RechargeOrderVo;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@Api(value = "/api/recharge", tags = "支付宝微信充值")
@RestController
@RequestMapping(value = "/api/recharge")
public class rechargeApi {

    Logger logger = LoggerFactory.getLogger(rechargeApi.class);
    @Autowired
    private RmbRechargeService rmbRechargeService;
    @Autowired
    private Pay pay;
    @Value("${pay.aliPayCallback}")
    private String aliPayCallback;
    @Value("${pay.aliPayFinshBack}")
    private String aliPayFinshBack;
    @Value("${pay.aliRechargeCallback}")
    private String aliRechargeCallback;
    @Value("${pay.aliRechageFinshBack}")
    private String aliRechageFinshBack;

    /**
     * @description: 订单支付
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/1 0001 17:27
     */
    @ApiOperation(value = "微信充值", notes = "微信充值", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/wxRecharge")
    @UserLoginToken
    @Transactional
    public MessageVO wxRecharge(@Valid @RequestBody RechargeOrderVo parame,
                                HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        boolean flag = false;
        User user = (User) request.getSession().getAttribute(request.getSession().getId());
        if (StringUtils.isEmpty(user.getId())) {
            throw new RuntimeException("PLEASELOGIN");     //请登录
        }
        logger.info("充值用户信息{}", JSONObject.toJSONString(user));
        logger.info(user.getPhone() + " 用户进入微信充值");
        String orderId = IdWorker.getNewInstance().nextIdToString();
        RmbRecharge rmbRecharge = new RmbRecharge();
        rmbRecharge.setId(orderId);
        rmbRecharge.setRechargeAmount(parame.getRechargeAmount());
        rmbRecharge.setRechargeType(OrderPayTypeEnums.WEIXIN.getCode());
        rmbRecharge.setStatus("0");
        rmbRecharge.setUserId(user.getId());
        rmbRecharge.setCreateTime(DateUtils.getDBDate());
        rmbRecharge.setPhone(user.getPhone());
        flag = rmbRechargeService.insert(rmbRecharge);
        if (flag == false) {
            return MessageVO.builder().msgCode(MessageEnums.WXRECHARGE_ERROR).build();   //订单信息异常
        } else {
            Map<String, String> map = pay.pay(parame.getRechargeAmount(), rmbRecharge.getId(), user.getWxUUId(), IpUtil.getIpAddr(request), "", WXPayTypeEmums.RECHARGE_TYPE.getCode());
            if (map != null) {
                logger.info("微信支付成功返回参数{}",JSONObject.toJSONString(map));
                logger.info("微信充值成功订单号：" + rmbRecharge.getId() + "充值金额" + parame.getRechargeAmount());
                return MessageVO.builder(map).msgCode(MessageEnums.PAYMENT_SUCCESS).build();
            } else {
                return MessageVO.builder().msgCode(MessageEnums.PAYMENT_FAIL).build();
            }
        }


    }

    @ApiOperation(value = "支付宝充值", notes = "支付宝充值", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rechargeMoney", value = "充值定额", required = true, dataType = "String"),
    })
    @PostMapping("/aliRecharge")
    @UserLoginToken
    @Transactional
    public void aliRecharge(double rechargeMoney, HttpServletRequest request, HttpServletResponse response) throws Exception {
        boolean flag = false;
        logger.info("ali start");
        logger.info("rechargeMoney："+rechargeMoney);
        String orderId = IdWorker.getNewInstance().nextIdToString();
        logger.info("支付宝充值订单号" + orderId + "充值金额" + rechargeMoney);
        if (rechargeMoney <= 0) {
            logger.info("金额小于0");
            throw new RuntimeException(MessageEnums.ALIRECHARGE_ERROR.getDesc());
        }
        User user = (User) request.getSession().getAttribute(request.getSession().getId());
        if (StringUtils.isEmpty(user.getId())) {
            logger.info("用户为空");
            throw new RuntimeException("PLEASELOGIN");     //请登录
        }
        logger.info(user.getPhone() + " 用户进入支付宝充值");

        RmbRecharge rmbRecharge = new RmbRecharge();
        rmbRecharge.setId(orderId);
        rmbRecharge.setRechargeAmount(new BigDecimal(rechargeMoney));
        rmbRecharge.setRechargeType(OrderPayTypeEnums.ZHIFUBAO.getCode());
        rmbRecharge.setStatus("0");
        rmbRecharge.setUserId(user.getId());
        rmbRecharge.setCreateTime(DateUtils.getDBDate());
        rmbRecharge.setPhone(user.getPhone());
        flag = rmbRechargeService.insert(rmbRecharge);
        logger.info("支付宝充值订单号" + orderId + "充值金额" + rechargeMoney);
        if (flag) {
            AlipayApiService service = new AlipayApiServiceImpl();
            WapPayRequest wapPayRequest = new WapPayRequest(orderId, rechargeMoney, "景红堂");
            wapPayRequest.setBody("订单号:" + orderId);
            logger.info("支付宝充值订单号" + orderId + "充值金额" + rechargeMoney);
            String notifyUrl = aliRechargeCallback; // 回调地址
            //TODO 待支付成功返回页面
            String returnUrl = aliRechageFinshBack;// 支付完成后跳转地址
            service.wapPay(wapPayRequest, returnUrl, notifyUrl, response);
        } else {
            logger.info("充值异常");
            throw new RuntimeException(MessageEnums.ALIRECHARGE_ERROR.getDesc());

        }
    }

}
