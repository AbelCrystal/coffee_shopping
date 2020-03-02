package com.shopping.unit;

import java.util.Random;
import java.util.UUID;


/**
 * 
 * 生成订单号工具类
 * 
 * @author admin
 *
 */
public class OrderNum {

	/**
	 * 
	 * 
	 * 核心方法
	 * 
	 * @param num
	 *            生成几位数的订单号
	 * 
	 * @return 订单号
	 */
	public static String nextNum(int num) {
		String str = "";
		if (num < 12 || num > 32) {
			return "订单号生成不能小于12位,或者大于32位！";
		}
		if (num >= 12 && num <= 15) {
			StringBuffer sbf = new StringBuffer();
			sbf.append(getOrderIdByUUId());// 10
			int sum = num - 10;
			sbf.append(getRandom(sum, System.currentTimeMillis()));
			str = sbf.toString();
		} else if (num >= 16 && num < 18) {
			StringBuffer sbf = new StringBuffer();
			sbf.append(DateUtils.getFormatDate("yyMMdd"));
			sbf.append(getOrderIdByUUId());// 10
			int sum = num - 16;
			sbf.append(getRandom(sum, System.currentTimeMillis()));
			str = sbf.toString();
		} else if (num >= 18 && num < 20) {
			StringBuffer sbf = new StringBuffer();
			sbf.append(DateUtils.getFormatDate("yyMMddHH"));
			sbf.append(getOrderIdByUUId());// 10
			int sum = num - 18;
			sbf.append(getRandom(sum, System.currentTimeMillis()));
			str = sbf.toString();
		} else if (num >= 20 && num <24) {
			StringBuffer sbf = new StringBuffer();
			sbf.append(DateUtils.getFormatDate("yyMMddHHmm"));
			sbf.append(getOrderIdByUUId());// 10
			int sum = num - 20;
			sbf.append(getRandom(sum, System.currentTimeMillis()));
			str = sbf.toString();
		}else if(num>=24 && num<32){
			StringBuffer sbf = new StringBuffer();
			sbf.append(DateUtils.getFormatDate("yyyyMMddHHmmss"));
			sbf.append(getOrderIdByUUId());
			int sum = num - 24;
			sbf.append(getRandom(sum, System.currentTimeMillis()));
			str = sbf.toString();
		}
		return str;
	};

	/**
	 * 
	 * @return 返回十位数的唯一编码
	 */
	public static String getOrderIdByUUId() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 10 代表长度为10
		// d 代表参数为正数型
		return String.format("%010d", hashCodeV);
	}

	/**
	 * 生成n位随机数
	 * 
	 * @param n
	 * @param seel
	 * @return
	 */
	public static String getRandom(int n, Long seel) {
		StringBuffer sb = new StringBuffer();// 装载生成的随机数
		Random r = null;
		if (seel != null) {
			r = new Random(seel);
		} else {
			r = new Random();
		}
		for (int i = 0; i < n; i++) {
			sb.append("0123456789".charAt(r.nextInt("0123456789".length())));
		}

		return sb.toString();
	}
}
