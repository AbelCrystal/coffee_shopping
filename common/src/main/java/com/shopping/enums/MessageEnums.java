package com.shopping.enums;

/**
 * @description: 状态统一处理枚举类
 * @author: Abel
 * @create: 2019-05-05
 */
public enum MessageEnums {
    /**
     * 成功消息
     */
    BIGIMAGE("bigImage","bigImage"),
    ENBIGIMAGE ("enBigImage","enBigImage"),
    KBIGIMAGE ("kImage","kImage"),
    KENBIGIMAGE ("kenImage","kenImage"),
    SUCCESS( "200","成功！"),
    LOGIN_SUCCESS("200", "登录成功"),
    LOGOUT_SUCCESS("200", "注销登录"),
    REGISTER_SUCCESS("200", "注册成功"),
    UPDATE_SUCCESS("200", "修改成功"),
    DELETE_SUCCESS("200","删除成功"),
    SMS_CODE_SUCCESS("200", "发送成功！"),
    ADD_SUCCESS("200","添加成功"),
    FEEDBACK_SUCCESS("200","反馈成功"),
    ORDERS_SUCCESS("200","下单成功"),
    PAYMENT_SUCCESS("200","支付成功"),
    VERIFICATION_SUCCESS("200","验证通过"),
    SETTING_SUCCESS("200","设置成功"),
    CANCEL_SUCCESS("200","取消成功"),
    /**
     * 接口出错
     */

    UPDATE_Fail("500", "修改失败"),
    ADD_FAIL("500","添加失败"),
    DELETE_FAIL("500","删除失败"),
    SERVICE_ERROR("403", "服务器出现错误"),
    FAIL( "500","失败！"),
    REGISTER_Fail("500", "注册失败"),
    SMS_CODE_ERROR("500", "短信验证码错误"),
    ORIGIGINAL_ERROR("500", "原密码与旧密码不一致"),
    PASSWORDIDENTICAL_ERROR("500", "新密码与旧密码一致"),
    NOT_LOGIN("501", "没有登录，请登录！"),
    USERFROZEN("501", "你的账户已冻结！"),
    API_ERROR("500", "API出错"),
    USERNAME_OR_PASSWORD_ERROR("500", "账号或密码错误"),
    No_ACCESS("500", "您的账号已冻结，请联系管理员"),
    NO_ACCOUNT_EXIST("500", "账号不存在"),
    NEW_SMS_CODE_ERROR("500", "新手机短信验证码错误"),
    OLD_SMS_CODE_ERROR("500", "验证已超时或没有通过验证"),
    SAME_PHONE("500", "旧手机号和新手机号相同"),
    GOODS_OFFSHELVES("500", "该商品已下架"),
    HAS_PHONE("500", "手机号已存在"),
    DATA_EXCEPTION("500","数据异常"),
    NO_SIMILAR("500","无相似信息"),
    SIGN_FAILR("500","验签失败"),
    ORDERS_FAILR("500","下单失败"),
    INSUFFICIENT_FUNDS("500","账户资金不足"),
    PAYPASSWORD_IS_NULL("500","支付密码为空"),
    PAYPASSWORD_IS_NOT_SETTING("500","支付密码未设置"),
    PAYPASSWORD_ERROR("500","支付密码错误"),
    PAYMENT_FAIL("500","支付失败"),
    NO_INVENTORY("500","库存不足"),
    SETTING_FAILD("500","设置失败"),
    FEE_NOT_ENOUGH("500","手续费不足，无法申请提币!"),
    WITHDRAW_TIMES_REACHED_LIMIT("500","今日提现次数已达上限!"),
    NOT_ENOUGH_COIN("500","账户余额不足"),
    NONSUPPORT_WITHDRAW("500","不支持提现"),
    CURRENCY_INFO_ERROR("500","币种注册信息有误"),
    CREATE_ADDRESS_ERROR("500","生成地址异常"),
    CANCEL_ERROR("500","取消失败"),
    INVITER_ERROR("500","邀请码异常"),
    WXRECHARGE_ERROR("500","微信充值失败"),
    ALIRECHARGE_ERROR("500","支付宝充值失败"),
    ALIWX_AUTH("500","请用微信或支付宝扫码！"),
    ORDER_OUTED("500","该订单已经出单！"),
    ;
    private final String code;
    private final String desc;

    MessageEnums(String code, String desc) {
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