package com.shopping.enums;

public enum OrderPayTypeEnums {

    KEO("0", "KEO"),
    ZHIFUBAO("1","支付宝"),
    WEIXIN("2","微信"),
    USERBALANCE("3","用户余额"),
    BTC("4","比特币"),
    ETH("5","以太坊");
    private final String code;
    private final String desc;

    OrderPayTypeEnums(String code, String desc) {
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
