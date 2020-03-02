package com.shopping.enums;

public enum UserRedisKeyPrefixEnum {

	IS_USERFROZENKEY("ISUSERFROZEN","用户冻结");

	private final String code;
	private final String desc;

	UserRedisKeyPrefixEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
