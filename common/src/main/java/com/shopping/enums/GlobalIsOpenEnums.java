package com.shopping.enums;

public enum GlobalIsOpenEnums {

    IS_NOT_OPEN("0","未开启"),
    IS_OPEN("1","开启");

    private final String code;
    private final String desc;

    GlobalIsOpenEnums(String code, String desc) {
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
