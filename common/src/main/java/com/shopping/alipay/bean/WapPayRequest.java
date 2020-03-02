package com.shopping.alipay.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.alipay.api.domain.ExtUserInfo;
import com.alipay.api.domain.ExtendParams;
import com.alipay.api.domain.InvoiceInfo;
import com.alipay.api.domain.RoyaltyInfo;
import com.alipay.api.domain.SettleInfo;
import com.alipay.api.domain.SubMerchant;

/**
 * 手机网站支付
 *
 * @desc 外部商户创建订单并支付
 */
public class WapPayRequest extends AlipayBaseBean {

	private static final long serialVersionUID = 1L;

	private String body; // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。


	private String subject; // 商品的标题/交易标题/订单标题/订单关键字等（必填）。


	private String out_trade_no; // 商户订单号（必填）,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复


	private String timeout_express; // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。
									// 该参数数值不接受小数点， 如 1.5h，可转换为 90m。


	private String time_expire; // 绝对超时时间，格式为yyyy-MM-dd HH:mm。


	private String total_amount; // 订单总金额（必填），单位为元，精确到小数点后两位，取值范围[0.01,100000000]


	private String seller_id; // 收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID


	private String auth_token; // 针对用户授权接口，获取用户相关数据时，用于标识用户授权关系


	private String goods_type; // 商品主类型 :0-虚拟类商品,1-实物类商品


	private String passback_params; // 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。


	private String quit_url; // 用户付款中途退出返回商户网站的地址（必选 ？）


	private String product_code; // 销售产品码，商家和支付宝签约的产品码（必选 ？）


	private String promo_params; // 优惠参数,注：仅与支付宝协商后可用


	private RoyaltyInfo royalty_info; // 描述分账信息，json格式，详见分账参数说明


	private ExtendParams extend_params; // 业务扩展参数


	private SubMerchant sub_merchant; // 间连受理商户信息体，当前只对特殊银行机构特定场景下使用此字段


	private String enable_pay_channels; // 可用渠道，用户只能在指定渠道范围内支付,当有多个渠道时用“,”分隔
										// ,注，与disable_pay_channels互斥


	private String disable_pay_channels; // 禁用渠道，用户不可用指定渠道支付,当有多个渠道时用“,”分隔,注，与enable_pay_channels互斥


	private String store_id; // 商户门店编号


	private SettleInfo settle_info; // 描述结算信息


	private InvoiceInfo invoice_info; // 开票信息


	private String specified_channel; // 指定渠道，目前仅支持传入pcredit,若由于用户原因渠道不可用，用户可选择是否用其他渠道支付。注：该参数不可与花呗分期参数同时传入


	private String business_params; // 商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式


	private ExtUserInfo ext_user_info; // 外部指定买家

	/**
	 * 手机网站支付
	 * 
	 * @param outTradeNo 商户订单号（必填）,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
	 * @param totalAmount 订单总金额（必填），单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	 * @param subject 订单标题
	 * @desc 外部商户创建订单并支付
	 */
	public WapPayRequest(String outTradeNo, Double totalAmount, String subject) {
		this(outTradeNo, toMoneyStr(totalAmount), subject);
	}

	/**
	 * 手机网站支付
	 * 
	 * @param outTradeNo 商户订单号（必填）,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
	 * @param totalAmount 订单总金额（必填），单位为元
	 * @param subject 订单标题
	 * @desc 外部商户创建订单并支付
	 */
	public WapPayRequest(String outTradeNo, Integer totalAmount, String subject) {
		this(outTradeNo, totalAmount.toString(), subject);
	}
	
	/**
	 * 手机网站支付
	 * 
	 * @param outTradeNo 商户订单号（必填）,64个字符以内、只能包含字母、数字、下划线；需保证在商户端不重复
	 * @param totalAmount 订单总金额（必填），单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	 * @param subject 订单标题
	 * @desc 外部商户创建订单并支付
	 */
	public WapPayRequest(String outTradeNo, String totalAmount, String subject) {
		this();
		this.total_amount = totalAmount;
		this.subject = subject;
		this.out_trade_no = outTradeNo;
	}

	public WapPayRequest() {
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTimeout_express() {
		return timeout_express;
	}

	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getAuth_token() {
		return auth_token;
	}

	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}

	public String getGoods_type() {
		return goods_type;
	}

	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}

	public String getPassback_params() {
		return passback_params;
	}

	public void setPassback_params(String passback_params) {
		this.passback_params = passback_params;
	}

	public String getQuit_url() {
		return quit_url;
	}

	public void setQuit_url(String quit_url) {
		this.quit_url = quit_url;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	public String getPromo_params() {
		return promo_params;
	}

	public void setPromo_params(String promo_params) {
		this.promo_params = promo_params;
	}

	public RoyaltyInfo getRoyalty_info() {
		return royalty_info;
	}

	public void setRoyalty_info(RoyaltyInfo royalty_info) {
		this.royalty_info = royalty_info;
	}

	public ExtendParams getExtend_params() {
		return extend_params;
	}

	public void setExtend_params(ExtendParams extend_params) {
		this.extend_params = extend_params;
	}

	public SubMerchant getSub_merchant() {
		return sub_merchant;
	}

	public void setSub_merchant(SubMerchant sub_merchant) {
		this.sub_merchant = sub_merchant;
	}

	public String getEnable_pay_channels() {
		return enable_pay_channels;
	}

	public void setEnable_pay_channels(String enable_pay_channels) {
		this.enable_pay_channels = enable_pay_channels;
	}

	public String getDisable_pay_channels() {
		return disable_pay_channels;
	}

	public void setDisable_pay_channels(String disable_pay_channels) {
		this.disable_pay_channels = disable_pay_channels;
	}

	public String getStore_id() {
		return store_id;
	}

	public void setStore_id(String store_id) {
		this.store_id = store_id;
	}

	public SettleInfo getSettle_info() {
		return settle_info;
	}

	public void setSettle_info(SettleInfo settle_info) {
		this.settle_info = settle_info;
	}

	public InvoiceInfo getInvoice_info() {
		return invoice_info;
	}

	public void setInvoice_info(InvoiceInfo invoice_info) {
		this.invoice_info = invoice_info;
	}

	public String getSpecified_channel() {
		return specified_channel;
	}

	public void setSpecified_channel(String specified_channel) {
		this.specified_channel = specified_channel;
	}

	public String getBusiness_params() {
		return business_params;
	}

	public void setBusiness_params(String business_params) {
		this.business_params = business_params;
	}

	public ExtUserInfo getExt_user_info() {
		return ext_user_info;
	}

	public void setExt_user_info(ExtUserInfo ext_user_info) {
		this.ext_user_info = ext_user_info;
	}
}
