package com.shopping.enums;

public enum PictureStatusEnums {

    PIC_VALID("0", "有效"),
    PIC_INVALID("1","无效")
    ;
    private final String code;
    private final String desc;

    PictureStatusEnums(String code, String desc) {
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
