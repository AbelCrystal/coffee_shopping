package com.shopping.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.shopping.alipay.util.AlipayConfigUtil;
import com.shopping.enums.OrderPayTypeEnums;
import com.shopping.enums.WXPayTypeEmums;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.order.RmbRechargeService;
import com.shopping.wxPay.Pay;
import com.shopping.wxPay.WXPayUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RequestMapping("pay")
@RestController
public class CallbackApi {

    @Autowired
    private Pay pay;
    @Autowired
    private RmbRechargeService rmbRechargeService;
    @Autowired
    private OrderMasterService orderMasterService;
    private Logger logger = LoggerFactory.getLogger(CallbackApi.class);

    @RequestMapping("wxCallback")
    public Object callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream is = request.getInputStream();
        String notifyData = WXPayUtil.inputStream2String(is); // 支付结果通知的xml格式数据
        Map<String, String> notifyMap = WXPayUtil.xmlToMap(notifyData);  // 转换成map
        if (pay.getWxPay().isPayResultNotifySignatureValid(notifyMap)) {
            String id = notifyMap.get("out_trade_no");//订单号
            String type = notifyMap.get("attach");//充值与下单类型
            if (WXPayTypeEmums.RECHARGE_TYPE.getCode().equals(type)) {
                logger.info("微信充值订单:" + id + "进入回调！");
                logger.info("充值回调参数：" + JSONObject.toJSONString(notifyMap));
                rmbRechargeService.recharge(id);
            } else {
                logger.info("微信支付订单:" + id + "进入回调！");
                logger.info("支付回调参数：" + JSONObject.toJSONString(notifyMap));
                orderMasterService.orderPayCallBack(id, OrderPayTypeEnums.WEIXIN.getCode());
            }
            // 签名正确
            // 进行处理。

            // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
            //告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
            is.close();
        }
        return null;
    }

    /**
     * 阿里充值回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("aliRechargeCallback")//
    public void aliRechargeCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入支付宝充值回调");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        logger.info("支付宝充值回调参数" + JSONObject.toJSONString(params));

        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号
        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfigUtil.publicKeyForAlipay, AlipayConfigUtil.charset, AlipayConfigUtil.signType);
        if (verify_result) {//验证成功
            System.out.println("verify_result is true");
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                logger.info("支付宝充值回调订单号：" + out_trade_no);
                rmbRechargeService.recharge(out_trade_no);
                logger.info("支付宝充值回调订单号：" + out_trade_no + "成功！");
                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

        } else {//验证失败
            System.out.println("verify_result is false");
        }
    }

    /**
     * 阿里付款后回调
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("aliPayCallback")//
    public void aliPayCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("进入支付宝订单支付回调");
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        System.out.println("notifyUrl:" + JSONObject.toJSONString(params));
        logger.info("进入支付宝订单支付回调参数" + JSONObject.toJSONString(params));

        // 商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfigUtil.publicKeyForAlipay, AlipayConfigUtil.charset, AlipayConfigUtil.signType);
        if (verify_result) {//验证成功
            System.out.println("verify_result is true");
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——

            if (trade_status.equals("TRADE_FINISHED")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")) {
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序
                logger.info("支付宝支付支付回调订单号：" + out_trade_no);
                orderMasterService.orderPayCallBack(out_trade_no, OrderPayTypeEnums.ZHIFUBAO.getCode());
                logger.info("支付宝支付支付回调订单号：" + "回调成功");
                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

        } else {//验证失败
            System.out.println("verify_result is false");
        }
    }

    @ApiOperation(value = "微信支付充值回调", notes = "微信支付充值回调")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "backType", value = "回调类型", required = true, dataType = "String")
    })
    @GetMapping(value = "/textWXCallBack")
    public void textWXCallBack(String orderId, String backType) {
        try {
            if (backType.equals("0")) {
                orderMasterService.orderPayCallBack(orderId, OrderPayTypeEnums.WEIXIN.getCode());
            } else {
                rmbRechargeService.recharge(orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @ApiOperation(value = "支付宝支付充值回调测试", notes = "支付宝支付充值回调测试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "backType", value = "回调类型", required = true, dataType = "String")
    })
    @GetMapping(value = "/textAliCallBack")
    public void textAliCallBack(String orderId, String backType) {
        try {
            if (backType.equals("0")) {
                orderMasterService.orderPayCallBack(orderId, OrderPayTypeEnums.ZHIFUBAO.getCode());
            } else {
                rmbRechargeService.recharge(orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}