package com.shopping.enums;

public enum CapitalOperationTypeEnums {

    COIN_IN("0", "充币"),
    COIN_OUT("1","提币")
    ;
    private final String code;
    private final String desc;

    CapitalOperationTypeEnums(String code, String desc) {
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
