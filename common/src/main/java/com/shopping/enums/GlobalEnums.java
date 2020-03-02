package com.shopping.enums;

public enum GlobalEnums {

    NORMAL("0","正常"),
    DISABLE("1","禁用");

    private final String code;
    private final String desc;

    GlobalEnums(String code, String desc) {
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
