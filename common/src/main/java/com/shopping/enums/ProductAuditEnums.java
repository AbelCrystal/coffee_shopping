package com.shopping.enums;

public enum ProductAuditEnums {

    UNCHECKED("0","未审核"),
    CHECKED("1", "已审核")
    ;
    private final String code;
    private final String desc;

    ProductAuditEnums(String code, String desc) {
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
