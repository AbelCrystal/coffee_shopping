package com.shopping.enums;

public enum PictureSourceEnums {

    REFUND("0", "退货"),
    FEEDBACK("1","意见反馈"),
    REFUND_MONEY("2","退款")
    ;
    private final String code;
    private final String desc;

    PictureSourceEnums(String code, String desc) {
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
