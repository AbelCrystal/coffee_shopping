package com.shopping.enums;

public enum GlobalIsDefaultEnums {

    NO("0","否"),
    YES("1","是");

    private final String code;
    private final String desc;

    GlobalIsDefaultEnums(String code, String desc) {
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
