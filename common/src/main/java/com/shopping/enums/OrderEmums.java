package com.shopping.enums;

public enum OrderEmums {
//   0:待付款 1:已支付 2:已出单 3：交易关闭
    ORDEER_STATE_ALL("-1", "所有订单"),
    ORDEER_STATE_WAIT_PAYMENT("0", "待付款"),
    ORDEER_STATE_WAIT_DELIVER("1", "已支付"),
    ORDEER_STATE_BILL_ISSUED("2", "已出单"),
    ORDEER_STATE_TRANSACTION_CLOSE("3", "交易关闭"),


    ORDEER_STATE_RECEIVED("2", "待收货"),
    ORDEER_STATE_APPLICATION_REFUND ("3", "申请退款"),
    ORDEER_STATE_APPLICATION_REFUNDALL ("4", "申请退货退款"),
    ORDEER_STATE_PARTIAL_RETURN ("5", "部分退货中"),
    ORDEER_STATE_COMPLETED("6", "已完成"),
    TRANSACTION_CLOSE("7", "交易关闭"),
    TRANSACTION_FINISH("8", "交易成功"),
    ;
    private final String code;
    private final String desc;

    OrderEmums(String code, String desc) {
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
