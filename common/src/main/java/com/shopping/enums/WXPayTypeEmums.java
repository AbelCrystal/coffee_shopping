package com.shopping.enums;

public enum WXPayTypeEmums {
    /**
     * 广告状态
     */


    ORDER_TYPE("1", "商品订单支付"),
    RECHARGE_TYPE("2", "充值订单支付");
    private final String code;
    private final String desc;

    WXPayTypeEmums(String code, String desc) {
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
