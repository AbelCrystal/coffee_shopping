package com.shopping.wechat;

public class Consts {
	//AppId
    public static final String AppId = "wx6dd8fc91a0cc14b8";
    //AppSecret
    public static final String AppSecret = "295fcb79e965ab62f1bd3f20306c308d";
    //Access token
    public static final String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ AppId + "&secret=" + AppSecret;
    //域名
    public static final String DOMAIN = "shop.h5yun.com";
    //个人
    public static final String SELF_CODE_WX_TOKEN = "wechat";
    //character
    public static final String ENCODING = "utf-8";
    //wechat session user
    public static final String WECHAT_USER_INFO = "WECHAT_USER_INFO";
    
    
}
