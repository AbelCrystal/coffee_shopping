package com.shopping.unit;

public interface Constants {

    //币种名称
    String CurrencyName = "currency_name";

    //提现手续费是否开启
    String WithdrawFeeIsOpen = "withdraw_fee_is_open";

    //最大提现额度
    String MaxWithdrawNum = "max_withdraw_num";

    //每天提现次数限制
    String DAY_DRAW_COIN_TIMES = "day_draw_coin_times";

    //平台账户
    String ADMIN_ID = "admin_id";
    //验证码过期时间
    int CAPTCHA_TIME_OUT = 30 * 60 * 1000;
}
