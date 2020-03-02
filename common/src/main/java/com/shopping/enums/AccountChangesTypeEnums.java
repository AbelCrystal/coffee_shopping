package com.shopping.enums;

public enum AccountChangesTypeEnums {

    EXPEND("0", "支出"),
    INCOME("1","收入"),
    RECHARGE("2","充值"),
    WITHDRAW("3","提现"),
    FREEZE("4","冻结"),
    UNFREEZE("5","解冻"),
    SHHARE_PROFIT("6","分润")
    ;
    private final String code;
    private final String desc;

    AccountChangesTypeEnums(String code, String desc) {
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
