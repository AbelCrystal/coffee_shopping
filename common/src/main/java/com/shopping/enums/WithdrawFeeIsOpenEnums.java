package com.shopping.enums;

public enum WithdrawFeeIsOpenEnums {

    FEE_NOT_OPEN("0", "未开启"),
    FEE_IS_OPEN("1","已开启")
    ;
    private final String code;
    private final String desc;

    WithdrawFeeIsOpenEnums(String code, String desc) {
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
