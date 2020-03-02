package com.shopping.alipay.bean;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.alibaba.fastjson.JSONObject;

/**
 * Alipay Base Bean.
 *
 */
public class AlipayBaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return toJson();
	}
	
	public String toJson() {
		return JSONObject.toJSONString(this);
	}
	
	/**
	 * 保留两位小数 
	 */
	public static String toMoneyStr(Double money) {
		if (money != null) {
			DecimalFormat df = new DecimalFormat("#.##");
			return df.format(money);
		} else {
			return null;
		}
	}
	
}
