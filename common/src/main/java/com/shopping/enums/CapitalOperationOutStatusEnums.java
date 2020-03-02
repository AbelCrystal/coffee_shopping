package com.shopping.enums;

public enum CapitalOperationOutStatusEnums {

    //0等待提现 1正在处理 2提现中 3提现成功 4提现失败 5用户撤销
    WAIT_FOR_OPERATION("0", "等待提现"),
    OPERATION_LOCK("1","正在处理"),
    OPERATING("2","提现中"),
    OPERATION_SUCCESS("3","提现成功"),
    OPERATION_FAILED("4","提现失败"),
    OPERATION_CANCEL("5","用户撤销")
    ;
    private final String code;
    private final String desc;

    CapitalOperationOutStatusEnums(String code, String desc) {
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
