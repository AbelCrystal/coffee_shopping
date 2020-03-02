package com.shopping.enums;

public enum AdvertEmums {
    /**
     * 广告状态
     */
    ADVERT_UPPER("1", "上架"),
    ADVERT_LOWER("0", "下架"),

    /**
     * 广告类型
     */
    ADVERT_APP_INDEX("0", "APP首页"),
    ADVERT_PC_INDEX("1", "PC首页"),
    ADVERT_APP_START("2", "APP起始页");
    private final String code;
    private final String desc;

    AdvertEmums(String code, String desc) {
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
