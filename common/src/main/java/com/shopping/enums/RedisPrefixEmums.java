package com.shopping.enums;

public enum RedisPrefixEmums {
    /**
     * 短信类型
     */
    LOGIN("LOGIN:SMS", "登录短信发送前缀"),
    PAY("PAY:SMS", "忘记登录密码前缀"),
    SETPAYPASSWORD("SETPAYPASSWORD:SMS", "设置交易密码前缀"),
    UPDATEPHONE("UPDATEPHONE:SMS", "修改手机号前缀"),
    WITHDRAWCOIN("WITHDRAWCOIN:SMS","提币发送前缀"),
    /**
     * 订单
     */
    ORDEER_STATE_WAIT_PAYMENT_PREFIX("ORDEER_WAIT_PAYMENT", "待付款订单状态记录"),
    ORDEER_DINING_CODE_PREFIX("ORDEER_DINING_CODE", "取餐码");
    private final String code;
    private final String desc;

    RedisPrefixEmums(String code, String desc) {
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
