package com.shopping.wxPay;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class Pay {
    @Value("${pay.wxPayCallBack}")
    private String wxPayCallBack;
    private WXPay wxPay;
    Logger logger = LoggerFactory.getLogger(Pay.class);

    public Pay() {
        try {
            MyConfig config = new MyConfig();
            this.wxPay = new WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param amout   金额
     * @param tradeID 订单ID
     * @param openid  微信UUID
     * @param IP      IP
     * @param tableID 桌号
     * @param type    类型
     * @return
     */
    public Map<String, String> pay(BigDecimal amout, String tradeID, String openid, String IP, String tableID, String type) {
        Map<String, String> data = new HashMap();
        try {
            data.put("body", "熊宝呗");
            data.put("out_trade_no", tradeID);
            if (!StringUtils.isEmpty(tableID)) {
                data.put("device_info", tableID);
            }
            data.put("fee_type", "CNY");
            data.put("total_fee", amout.multiply(new BigDecimal("100")).setScale(0).toString());
            data.put("trade_type", "JSAPI");
            data.put("spbill_create_ip", IP);
            data.put("notify_url", wxPayCallBack);
            data.put("openid", openid);
            data.put("attach", type);
            logger.info("微信支付参数{}",JSONObject.toJSONString(data));
            return wxPay.unifiedOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String queryOrder(String tradeID) {
        try {
            Map<String, String> data = new HashMap();
            data.put("out_trade_no", tradeID);
            Map<String, String> resp = wxPay.orderQuery(data);
            if (resp.get("result_code").equals("SUCCESS")) {
                return resp.get("trade_state");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WXPay getWxPay() {
        return wxPay;
    }

}
