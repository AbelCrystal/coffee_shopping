package com.shopping.enums;

public enum OrderSrcEnums {

    ORDER_FOR_ANDROID("0", "安卓APP"),
    ORDER_FOR_IOS("1","苹果APP"),
    ORDER_FOR_WEB("2","商城"),
    ORDER_FOR_WEIXIN("3","微信")
    ;
    private final String code;
    private final String desc;

    OrderSrcEnums(String code, String desc) {
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
