package com.shopping.enums;

public enum MessageStatusEnums {

    NO_READ("0","未读"),
    READ("1","已读");

    private final String code;
    private final String desc;

    MessageStatusEnums(String code, String desc) {
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
