package com.shopping.enums;

public enum SmsTypeEmums {
    /**
     * 短信类型
     */
    LOGIN("0", "登录短信"),
    PAY("1", "支付");
    private final String code;
    private final String desc;

    SmsTypeEmums(String code, String desc) {
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
