package com.shopping.unit;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.annotation.ThreadSafe;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public class HttpClientUtils {

//    private static HttpClientBuilder httpClientBuilder;

    private static CloseableHttpClient httpClient;

    private static RequestConfig requestConfig;

    private static final String DEFAULT_CHARSET = "utf-8";

    private static final int TIMEOUT = 60000;

    static {
        Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
                .register("easy", new EasySpecProvider())
                .build();
        CookieStore cookieStore = new BasicCookieStore();
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(TIMEOUT)
                .setConnectTimeout(TIMEOUT)
                .setConnectionRequestTimeout(TIMEOUT)
                .setCookieSpec("easy")
                .build();
        httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .setDefaultCookieSpecRegistry(r)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * 发起Get请求
     *
     * @param url
     * @return 响应文本
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发起Get请求
     *
     * @param url
     * @param map
     * @return 响应文本
     */
    public static String get(String url, Map<String, String> map) {
        return get(url, map, DEFAULT_CHARSET);
    }

    /**
     * 发起Get请求
     *
     * @param url
     * @param map
     * @param charset
     * @return 响应文本
     */
    public static String get(String url, Map<String, String> map, String charset) {
        String queryString = buildQueryString(map, charset);
        HttpGet httpGet = new HttpGet(url + queryString);
//        httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
//        httpGet.addHeader("Cookie", "XXSESSIONID=d3f1d866-b022-42ea-acd5-44d9855f26f9");
        httpGet.setConfig(requestConfig);
        return execute(httpGet, charset);
    }

    /**
     * 发起Post请求
     *
     * @param url
     * @return 响应文本
     */
    public static String post(String url) {
        return post(url, new HashMap<>());
    }

    /**
     * 发起Post请求
     *
     * @param url
     * @param map
     * @return 响应文本
     */
    public static String post(String url, Map<String, String> map) {
        return post(url, map, DEFAULT_CHARSET);
    }

    public static String post(String url, JSONArray map) {
        return post(url, map, DEFAULT_CHARSET);
    }

    /**
     * 发起Post请求
     *
     * @param url
     * @param map
     * @param charset
     * @return 响应文本
     */
    public static String post(String url, Map<String, String> map, String charset) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
        httpPost.setEntity(buildPostParams(map, charset));
        return execute(httpPost, charset);
    }

    public static String post(String url, JSONArray map, String charset) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.2) AppleWebKit/525.13 (KHTML, like Gecko) Chrome/0.2.149.27 Safari/525.13");
        httpPost.setEntity(new StringEntity(map.toString(), Charset.forName("UTF-8")));
        return execute(httpPost, charset);
    }

    private static String execute(HttpRequestBase request, String charset) {
        // 统一执行请求，释放资源
        String result = null;
        try {
            HttpResponse response = httpClient.execute(request);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                result = EntityUtils.toString(resEntity, charset);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (request != null) {
                request.releaseConnection();
            }
        }
        return result;
    }

    private static String buildQueryString(Map<String, String> map, String charset) {
        // 构建Get请求参数
        if (map != null && map.size() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            map.forEach((key, val) -> {
                try {
                    String value = URLEncoder.encode(val, charset);
                    sb.append(key + "=" + value + "&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            });
            return sb.substring(0, sb.length() - 1);
        }
        return StringUtils.EMPTY;
    }

    private static HttpEntity buildPostParams(Map<String, String> map, String charset) {
        // 构建Post请求参数
        UrlEncodedFormEntity entity = null;
        if (map != null && map.size() > 0) {
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            map.forEach((key, val) -> {
                list.add(new BasicNameValuePair(key, val));
            });
            try {
                entity = new UrlEncodedFormEntity(list, charset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return entity;
    }

}

class EasyCookieSpec extends DefaultCookieSpec {
    @Override
    public void validate(Cookie arg0, CookieOrigin arg1) {
        //allow all cookies
    }
}

class EasySpecProvider implements CookieSpecProvider {
    @Override
    public CookieSpec create(HttpContext context) {
        return new EasyCookieSpec();
    }
}