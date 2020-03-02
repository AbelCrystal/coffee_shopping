package com.shopping.enums;

public enum UserInfoPicEmums {
    /**
     * 图片有效，无效
     */
    PIC_EFFECTIVE("0", "有效"),
    PIC_INVALID("1", "无效");
    private final String code;
    private final String desc;

    UserInfoPicEmums(String code, String desc) {
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
