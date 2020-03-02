package com.shopping.unit;

import lombok.Data;

import java.util.Date;

@Data
public class TZBInfo {
	private String account;// 帐户，USERID
	private String address;// 充向地址
	private String category;// 类型，receive OR SEND
	private double amount;// 数量
	private int confirmations;// 确认数
	private String txid;// 交易ID
	private Date time;// 时间
	private String comment;// 备注

	@Override
	public String toString() {
		return "TZBInfo{" +
				"account='" + account + '\'' +
				", address='" + address + '\'' +
				", category='" + category + '\'' +
				", amount=" + amount +
				", confirmations=" + confirmations +
				", txid='" + txid + '\'' +
				", time=" + time +
				", comment='" + comment + '\'' +
				'}';
	}
}
