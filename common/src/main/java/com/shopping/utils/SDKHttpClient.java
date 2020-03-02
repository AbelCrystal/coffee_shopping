package com.shopping.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author yj
 */

@Service
public class SDKHttpClient {
    private static final Logger log = LoggerFactory.getLogger(SDKHttpClient.class);
    private final String DEFAULT_CHARSET = "UTF-8";

    @Autowired
    private CloseableHttpClient httpclient;

    public String get(String url) {
        return get(url, null);
    }

    public String get(String url, Map<String, Object> map) {
        String queryString = buildQueryString(map);
        HttpGet httpGet = new HttpGet(url + queryString);
//        httpGet.addHeader("CLOUD-EXCHANGE", "B31C870B8B");
        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
        return execute(httpGet, url);
    }

    public String postJson(String url, String raw) {
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new StringEntity(raw, DEFAULT_CHARSET));
        httppost.addHeader("Content-Type", "application/json");
        return execute(httppost, url);
    }

    private String execute(HttpRequestBase request, String url){
        String result = null;
        try {
            CloseableHttpResponse response = httpclient.execute(request);
            if (null != response) {
                try {
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                        result = null;
                    }
                } finally {
                    response.close();
                }
            } else {
                log.info("【SDKHttpClient】｜ POST URL:[{}],响应结果为空!", url);
            }
        } catch (Exception e) {
            log.error("【SDKHttpClient】｜POST URL:[{}] 出现异常[{}]!", url, e.getStackTrace());
        } finally {
            try {
                if (null != request) {
                    request.releaseConnection();
                }
            } catch (Exception e) {
                log.error("【SDKHttpClient】｜POST URL:[{}] 关闭httpclient.close()异常[{}]!", url, e.getStackTrace());
            }
        }
        return result;
    }

    private String buildQueryString(Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            map.forEach((key, val) -> {
                try {
                    String value = URLEncoder.encode(val.toString(), DEFAULT_CHARSET);
                    sb.append(key + "=" + value + "&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            return sb.substring(0, sb.length() - 1);
        }
        return "";
    }

}
