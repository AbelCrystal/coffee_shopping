package com.shopping.alipay.service;

import java.io.UnsupportedEncodingException;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.*;
import com.shopping.alipay.bean.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 阿里支付服务
 * 
 * @author zhuowei.luo
 * @date 2018/7/9
 */
public interface AlipayApiService {


	/**
	 * 支付（手机网站）
	 * @param request 请求参数
	 * @param returnUrl 支付完成后 跳转的页面
	 * @param notifyUrl 支付异步请求接口
	 * @param response
	 * @throws AlipayApiException
	 */

	public void wapPay(WapPayRequest request, String returnUrl, String notifyUrl, HttpServletResponse response) throws AlipayApiException;

	public AlipayTradeWapPayResponse phonePay(WapPayRequest request, String returnUrl, String notifyUrl) throws AlipayApiException;
	/**
	 * 交易查询
	 * 
	 * @desc 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑
	 */
	public AlipayTradeQueryResponse query(QueryRequest request) throws AlipayApiException;
	

	
	/**
	 * 获取授权URL
	 *  @param state 自定义参数
	 * @param redirectUri 重定向url
	 * @param scope 授权范围，默认auth_base
	 */
	public String getOauthUrl(String state,String redirectUri, OauthScopeEnum scope) throws UnsupportedEncodingException;
	
	/**
	 * 根据授权码获取访问令牌、用户id
	 * 
	 * @return alipayUserId 用户id（交易创建请求中的buyerId），accessToken 访问令牌，refreshToken 刷新令牌
	 */
	public AlipaySystemOauthTokenResponse oauthToken(OauthTokenRequest request) throws AlipayApiException;


	/**
	 * 交易关闭
	 *
	 * @desc 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭；<br>
	 * 	               商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；<br>
	 * 	               系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
	 */
	public AlipayTradeCloseResponse close(CloseRequest request) throws AlipayApiException;


}
