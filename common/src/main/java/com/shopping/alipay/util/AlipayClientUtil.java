package com.shopping.alipay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * Client Util
 * 
 * @author zhuowei.luo
 * @date 2018/7/9
 */
public class AlipayClientUtil {

	private static AlipayClient client;

	static {
		init();
	}

	public static void reInit() {
		init();
	}
	
	/**
	 * 获取默认 AlipayClient
	 */
	public static AlipayClient getDefaultAlipayClient() {
		if (client == null) {
			init();
		}
		return client;
	}

	private static void init() {
		client = new DefaultAlipayClient(AlipayConfigUtil.serverUrl, AlipayConfigUtil.appId, AlipayConfigUtil.privateKeyForPkcs8, AlipayConfigUtil.format,
				AlipayConfigUtil.charset, AlipayConfigUtil.publicKeyForAlipay, AlipayConfigUtil.signType);
	}

}
