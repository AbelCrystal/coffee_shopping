package com.shopping.enums;

public enum MessageTypeEnums {

    SYSTEM_MESSAGE("0","系统消息"),
    TRADE_MESSAGE("1","交易物流消息"),
    CUSTOMER_MESSAGE("2","客服消息");

    private final String code;
    private final String desc;

    MessageTypeEnums(String code, String desc) {
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
