package com.shopping.enums;

public enum NoticeEmums {
    /**
     * 短信类型
     */
    INDEX_NOTICE("1", "首页公告");

    private final String code;
    private final String desc;

    NoticeEmums(String code, String desc) {
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
