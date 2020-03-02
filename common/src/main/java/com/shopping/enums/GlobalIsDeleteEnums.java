package com.shopping.enums;

public enum GlobalIsDeleteEnums {

    IS_NORMAL("0","正常"),
    IS_DISABLE("1","无效");

    private final String code;
    private final String desc;

    GlobalIsDeleteEnums(String code, String desc) {
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
