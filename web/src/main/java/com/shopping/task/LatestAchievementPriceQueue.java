package com.shopping.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shopping.enums.FeedbackStatusEnums;
import com.shopping.redis.RedisUtil;
import com.shopping.unit.HttpClientUtil;
import com.shopping.unit.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class LatestAchievementPriceQueue {

    @Autowired
    private RedisUtil redisUtil;

    @Scheduled(cron = "*/30 * * * * ?")
    public void work() {
        synchronized (this) {
            try {
                String resUsdt = HttpClientUtils.get("https://api.huobi.pro/market/tickers");
                JSONObject json = JSONObject.parseObject(resUsdt);
                JSONArray jsonArray = json.getJSONArray("data");
                BigDecimal btcusdtPrice=null;
                BigDecimal ethusdtPrice=null;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String currency = jsonObject.getString("symbol");
                    if ("btcusdt".equals(currency)) {
                        btcusdtPrice=new BigDecimal(jsonObject.getString("close"));

                    } else if ("ethusdt".equals(currency)) {
                        ethusdtPrice=new BigDecimal(jsonObject.getString("close"));
                    } else {
                        continue;
                    }
                }

                String keoUsdt = HttpClientUtils.get("https://openapi.wbfex.com/open/api/get_ticker?symbol=keousdt");
                BigDecimal usdtPrice = JSONObject.parseObject(keoUsdt).getJSONObject("data").getBigDecimal("last");
                BigDecimal lastKeoPrice=new BigDecimal(0);
                BigDecimal lastBtcPrice=new BigDecimal(0);
                BigDecimal lastEthPrice=new BigDecimal(0);
                if (usdtPrice != null&&btcusdtPrice!=null&&ethusdtPrice!=null) {
                    String tickerUsdt = HttpClientUtils.get("http://api.zb.cn/data/v1/ticker?market=usdt_qc");
                    BigDecimal tickerPrice = JSONObject.parseObject(tickerUsdt).getJSONObject("ticker").getBigDecimal("last");
                    lastKeoPrice = usdtPrice.multiply(tickerPrice);
                    lastBtcPrice = btcusdtPrice.multiply(tickerPrice);
                    lastEthPrice = ethusdtPrice.multiply(tickerPrice);
                }

                redisUtil.set(FeedbackStatusEnums.KEO.getCode(), lastKeoPrice.toString());
                redisUtil.set(FeedbackStatusEnums.ETH.getCode(), lastEthPrice.toString());
                redisUtil.set(FeedbackStatusEnums.BTC.getCode(), lastBtcPrice.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
