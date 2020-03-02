package com.shopping.enums;

public enum OrderSettlementEnums {

    NOT_SETTLEMENT("0", "未结算"),
    SETTLEMENTED("1","已结算")
    ;
    private final String code;
    private final String desc;

    OrderSettlementEnums(String code, String desc) {
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
