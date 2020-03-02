package com.shopping.enums;

public enum FeedbackStatusEnums {

    KEO("keo:price", "keo实价"),
    BTC("btc:price", "BTC实价"),
    ETH("eth:price", "ETH实价"),
    ;
    private final String code;
    private final String desc;

    FeedbackStatusEnums(String code, String desc) {
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
