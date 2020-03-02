package com.shopping.enums;

public enum CurrencyTypeEnues {
    KEO("KEO", "KEO"),
    BTC("BTC", "BTC"),
    ETH("ETH", "ETH"),
    ;
    private final String code;
    private final String desc;

    CurrencyTypeEnues(String code, String desc) {
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
