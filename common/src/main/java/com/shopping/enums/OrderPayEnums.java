package com.shopping.enums;

public enum OrderPayEnums {

    ORDER_NOT_PAID("0", "未支付"),
    ORDER_IS_PAID("1","已支付")
    ;
    private final String code;
    private final String desc;

    OrderPayEnums(String code, String desc) {
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
