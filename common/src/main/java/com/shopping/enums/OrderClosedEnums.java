package com.shopping.enums;

public enum OrderClosedEnums {

    ORDER_NOT_FINISH("0", "未完结"),
    ORDER_IS_FINISH("1","已完结")
    ;
    private final String code;
    private final String desc;

    OrderClosedEnums(String code, String desc) {
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
