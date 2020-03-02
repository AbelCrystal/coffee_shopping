package com.shopping.enums;

public enum ProductPublishEnums {

    UNSHELVE("0","下架"),
    PUTAWAY("1", "上架")
    ;
    private final String code;
    private final String desc;

    ProductPublishEnums(String code, String desc) {
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
