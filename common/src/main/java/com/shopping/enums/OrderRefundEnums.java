package com.shopping.enums;

public enum OrderRefundEnums {

    ORDER_NOT_REFUND("0", "未退款"),
    ORDER_IS_REFUND("1","已退款")
    ;
    private final String code;
    private final String desc;

    OrderRefundEnums(String code, String desc) {
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
