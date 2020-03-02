package com.shopping.alipay.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 获取访问令牌、用户id请求
 *
 */
public class OauthTokenRequest extends AlipayBaseBean {

	private static final long serialVersionUID = 1L;

	public static final String GRANTTYPE_AUTHORIZATION_CODE = "authorization_code";
	public static final String GRANTTYPE_REFRESH_TOKEN = "refresh_token";


	private String grant_type; // （必填）值为authorization_code时，代表用code换取；值为refresh_token时，代表用refresh_token换取


	private String code; // 授权码，用户对应用授权后得到。


	private String refresh_token; // 刷刷新令牌，上次换取访问令牌时得到。见出参的refresh_token字段

	/**
	 * 获取访问令牌、用户id请求（参数二选一）
	 * 
	 * @param code 通过授权码获取
	 * @param refreshToken 通过刷刷新令牌获取
	 * @desc code/refreshToken必须保证其中一个不能为空
	 */
	public OauthTokenRequest(String code, String refreshToken) {
		this();
		if (code != null) {
			this.code = code;
			this.grant_type = GRANTTYPE_AUTHORIZATION_CODE;
		} else if (refreshToken != null) {
			this.refresh_token = refreshToken;
			this.grant_type = GRANTTYPE_REFRESH_TOKEN;
		} else {
			throw new NullPointerException("code/refreshToken必须保证其中一个不能为空");
		}
	}

	public OauthTokenRequest() {
	}

	public String getGrant_type() {
		return grant_type;
	}

	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
