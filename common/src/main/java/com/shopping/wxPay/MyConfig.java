package com.shopping.wxPay;

import org.springframework.beans.factory.annotation.Value;

import java.io.*;

public class MyConfig extends WXPayConfig {

    private byte[] certData;

    public String getAppID() {
        return "wx6dd8fc91a0cc14b8";
    }

    public String getMchID() {
        return "1278938701";
    }

    public String getKey() {
        return "kasgdfasl465413q23sdf135adfg2345";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
