package com.shopping.alipay.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 交易关闭请求
 */
public class CloseRequest extends AlipayBaseBean {

	private static final long serialVersionUID = 1L;


	private String out_trade_no; // 订单支付时传入的商户订单号,和支付宝交易号不能同时为空

	private String trade_no; // 支付宝交易号，和商户订单号不能同时为空

	private String operator_id; // 卖家端自定义的的操作员 ID
	
	/**
	 * 交易关闭请求
	 * 
	 * @param outTradeNo 商户订单号，不能同时与tradeNo为空（参数二选一）
	 * @param tradeNo 支付宝交易号，不能同时与outTradeNo为空（参数二选一）
	 * @param operatorId 卖家端自定义的的操作员 ID
	 */
	public CloseRequest(String outTradeNo, String tradeNo, String operatorId) {
		this();
		this.out_trade_no = outTradeNo;
		this.trade_no = tradeNo;
		this.operator_id = operatorId;
	}
	
	public CloseRequest() {
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

	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
}
