package com.shopping.enums;

public enum  SmsStatus {
    /**
     * 成功消息
     */
    UN_SENDED("-1", "未发送"),
    SENDED("1", "已发送"),
    UN_SMS_ABEL("-1", "失效"),
    SMS_ABEL("1", "有效");

    private final String code;
    private final String desc;

    SmsStatus(String code, String desc) {
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
