package com.shopping.unit;

import lombok.Data;

@Data
public class TZBMessage {
	// 用户名
	private String ACCESS_KEY;
	// 密码
	private String SECRET_KEY;
	// 钱包IP地址
	private String IP;
	// 端口
	private String PORT;
	// 比特币钱包密码
	private String PASSWORD;
}
