package com.shopping.enums;

public enum AccountTypeEnums {

    TONGZHENG("0", "通证钱包"),
    RENMINGBI("1","人民币钱包")
    ;
    private final String code;
    private final String desc;

    AccountTypeEnums(String code, String desc) {
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
