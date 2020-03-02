package com.shopping.enums;

public enum SupplierEmums {

    AUDIT_APPLY("0", "申请"),
    AUDIT_SUCCESS("1", "申请通过"),
    AUDIT_FAILED("2", "申请不通过"),
    UPPER_SHELF("0", "上架"),
    LOWER_SHELF("1", "下架");

    private final String code;
    private final String desc;

    SupplierEmums(String code, String desc) {
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
