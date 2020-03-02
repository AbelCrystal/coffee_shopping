package com.shopping.alipay.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.shopping.alipay.bean.*;
import com.shopping.alipay.util.AlipayClientUtil;
import com.shopping.alipay.util.AlipayConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;

import javax.servlet.http.HttpServletResponse;

/**
 * 阿里支付服务
 *
 * @author zhuowei.luo
 * @date 2018/7/9
 */
public class AlipayApiServiceImpl implements AlipayApiService {

    private static final Logger logger = LoggerFactory.getLogger(AlipayApiServiceImpl.class);
    private AlipayClient client = AlipayClientUtil.getDefaultAlipayClient();


    @Override
    public void wapPay(WapPayRequest request, String returnUrl, String notifyUrl, HttpServletResponse response) throws AlipayApiException {
        String form = "";
        String json = null;
        try {
            // 调用SDK生成表单
            AlipayTradeWapPayRequest req = new AlipayTradeWapPayRequest();
            req.setNotifyUrl(notifyUrl);
            req.setReturnUrl(returnUrl);
            json = request.toJson();
            req.setBizContent(json);

            form = client.pageExecute(req).getBody();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public AlipayTradeWapPayResponse phonePay(WapPayRequest request, String returnUrl, String notifyUrl) throws AlipayApiException {
        String json = null;
        try {
            // 调用SDK生成表单
            AlipayTradeWapPayRequest req = new AlipayTradeWapPayRequest();
            req.setNotifyUrl(notifyUrl);
            req.setReturnUrl(returnUrl);
            json = request.toJson();
            req.setBizContent(json);
            AlipayTradeWapPayResponse alipayTradeWapPayResponse = client.pageExecute(req);
            return alipayTradeWapPayResponse;
        } catch (AlipayApiException e) {
            logger.error("alipay.phonePay，请求异常！req={}", json, e);
            throw e;
        }
    }

    @Override
    public AlipayTradeQueryResponse query(QueryRequest request) throws AlipayApiException {
        String json = null;
        try {
            json = request.toJson();
            AlipayTradeQueryRequest req = new AlipayTradeQueryRequest();
            req.setBizContent(json);
            AlipayTradeQueryResponse resp = client.execute(req);
            if (resp != null && resp.isSuccess()) {
                logger.info("alipay.query，执行请求！req={}, resp={}", json, JSONObject.toJSONString(resp));
            } else {
                logger.error("alipay.query，请求失败！req={}, resp={}", json, JSONObject.toJSONString(resp));
            }

            return resp;
        } catch (AlipayApiException e) {
            logger.error("alipay.query，请求异常！req={}", json, e);
            throw e;
        }
    }


    @Override
    public String getOauthUrl(String state,String redirectUri, OauthScopeEnum scope) throws UnsupportedEncodingException {
        try {
            if (scope == null) {
                scope = OauthScopeEnum.AUTH_BASE;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(AlipayConfigUtil.oauthUrl);
            sb.append("?app_id=").append(AlipayConfigUtil.appId);
            sb.append("&scope=").append(scope.getScope());
            sb.append("&state=").append(URLEncoder.encode(state, AlipayConfigUtil.charset));
            sb.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, AlipayConfigUtil.charset));
            logger.info("alipay.getOauthUrl，redirectUri={}，url={} ", redirectUri, sb.toString());
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            logger.error("alipay.getOauthUrl，redirectUri={}", redirectUri, e);
            throw e;
        }
    }

    /**
     * 获取用户授权信息
     *
     * @param request
     * @return
     * @throws AlipayApiException
     */
    @Override
    public AlipaySystemOauthTokenResponse oauthToken(OauthTokenRequest request) throws AlipayApiException {
        String json = null;
        try {
            json = request.toJson();
            AlipaySystemOauthTokenRequest req = new AlipaySystemOauthTokenRequest();
            req.setGrantType(request.getGrant_type());
            req.setCode(request.getCode());
            req.setRefreshToken(request.getRefresh_token());
            AlipaySystemOauthTokenResponse resp = client.execute(req);
            if (resp != null && resp.isSuccess()) {
//                logger.info("oauthToken，执行请求！req={}, resp={}", json, JSONObject.toJSONString(resp));
            } else {
                logger.error("oauthToken，请求失败！req={}, resp={}", json, JSONObject.toJSONString(resp));
            }
            return resp;
        } catch (AlipayApiException e) {
            logger.error("oauthToken，请求异常！req={}", json, e);
            throw e;
        }
    }


    @Override
    public AlipayTradeCloseResponse close(CloseRequest request) throws AlipayApiException {
        String json = null;
        try {
            json = request.toJson();
            AlipayTradeCloseRequest req = new AlipayTradeCloseRequest();
            req.setBizContent(json);
            AlipayTradeCloseResponse resp = client.execute(req);
            if (resp != null && resp.isSuccess()) {
                logger.info("alipay.close，执行请求！req={}, resp={}", json, JSONObject.toJSONString(resp));
            } else {
                logger.error("alipay.close，请求失败！req={}, resp={}", json, JSONObject.toJSONString(resp));
            }
            return resp;
        } catch (AlipayApiException e) {
            logger.error("alipay.close，请求异常！req={}", json, e);
            throw e;
        }
    }

}
