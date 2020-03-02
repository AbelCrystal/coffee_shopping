package com.shopping.alipay.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易查询
 *
 */
public class QueryRequest extends AlipayBaseBean {

	private static final long serialVersionUID = 1L;


	private String out_trade_no; // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空

	private String trade_no; // 支付宝交易号，和商户订单号不能同时为空

	/**
	 * 交易查询（参数二选一）
	 * 
	 * @param outTradeNo 商户订单号
	 * @param tradeNo 支付宝交易号
	 */
	public QueryRequest(String outTradeNo, String tradeNo) {
		this();
		this.out_trade_no = outTradeNo;
		this.trade_no = tradeNo;
	}
	
	public QueryRequest() {
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
}
