package com.shopping.enums;

public enum RefundEmums {

    APPLICATION_REFUND("0", "申请"),
    AUDIT_REFUND("1", "申请审核"),
    AFTER_SALES_RECEIPT("2", "售后收货"),
    REFUNDING("3", "进行退款"),
    COMPLETE_REFUNDING("4", "处理完成"),
    RETURN_GOODS("1", "退货退款"),
    REFUND("0", "退款"),
    ;
    private final String code;
    private final String desc;

    RefundEmums(String code, String desc) {
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
