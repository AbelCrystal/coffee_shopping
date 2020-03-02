package com.shopping.unit;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TZBUtils {
    private static final Logger log = LoggerFactory
            .getLogger(TZBUtils.class);

    private String ACCESS_KEY = null;
    private String SECRET_KEY = null;
    private String IP = null;
    private String PORT = null;
    private String PASSWORD = null;


    public TZBUtils(TZBMessage btcMessage) {
        this.ACCESS_KEY = btcMessage.getACCESS_KEY();
        this.SECRET_KEY = btcMessage.getSECRET_KEY();
        this.IP = btcMessage.getIP();
        this.PORT = btcMessage.getPORT();
        this.PASSWORD = btcMessage.getPASSWORD();
    }

    private String getSignature(String data, String key) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(),
                "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return bytArrayToHex(rawHmac);
    }

    private String bytArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    private void authenticator() {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ACCESS_KEY, SECRET_KEY.toCharArray());
            }
        });
    }

    public double getBalance() throws Exception {
        JSONObject s = JSONObject.parseObject(main("getbalance", "[]"));
        Double res = 0d;
        if (isRight(s)) res = s.getDouble("result");
        return res;
    }

    public String getAddress(String userId) throws Exception {
        JSONObject s = JSONObject.parseObject(main("getnewaddress", "[\"" + userId + "\"]"));
        return isRight(s) ? s.getString("result") : null;
    }

    public List<TZBInfo> listTx(int count, int from) throws Exception {
        JSONObject s = JSONObject.parseObject(main("listtransactions", "[\"*\"," + count + "," + from + "]"));
        List<TZBInfo> all = new ArrayList<>();
        if (isRight(s)) {
            JSONArray txs = s.getJSONArray("result");
            if (!CollectionUtils.isEmpty(txs)) {
                txs.forEach(tx -> {
                    JSONObject transaction = JSONObject.parseObject(tx.toString());
                    if ("receive".equals(transaction.getString("category"))) {
                        TZBInfo info = new TZBInfo();
                        info.setAddress(transaction.getString("address"));
                        info.setTxid(transaction.getString("txid"));
                        info.setAmount(transaction.getDoubleValue("amount"));
                        info.setTime(new Date(transaction.getLong("time") * 1000));
                        all.add(info);
                    }
                });
            }
        }
        Collections.reverse(all);
        return all;
    }

    public JSONObject getTransaction(String xid, String name) throws Exception {
        boolean isBtc = name.toUpperCase().equals("BTC") || name.toUpperCase().equals("LTC") || name.toUpperCase().equals("DOGE") || name.toUpperCase().equals("BCH");
        return JSONObject.parseObject(isBtc ? main("gettransaction", "[\"" + xid + "\"]") : main("gettransaction", "[\"" + xid + "\", 1]"));
    }

    public TZBInfo getTxValue(String xid) throws Exception {
        JSONObject s = JSONObject.parseObject(main("gettransaction", "[\"" + xid + "\"]"));
        TZBInfo tzbInfo = null;
        if (isRight(s)) {
            tzbInfo = new TZBInfo();
            JSONObject obj = s.getJSONObject("result");
            tzbInfo.setConfirmations(Integer.parseInt(obj.getOrDefault("confirmations", 0).toString()));
        }
        return tzbInfo;
    }

    public String sendToAddressValue(String address, double amount, String comment) throws Exception {
        if (!StringUtils.isEmpty(PASSWORD)) {
            JSONObject json = JSONObject.parseObject(main("walletpassphrase", "[\"" + PASSWORD + "\"," + 30 + "]"));
            if (!StringUtils.isEmpty(json.getString("error"))) {
                log.error("error pass: {}", PASSWORD);
                throw new Exception("钱包解锁失败");
            }
        }
        Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
        Matcher m = p.matcher(address);
        String newAddress = m.replaceAll("");
        String s = main("sendtoaddress", "[\"" + newAddress + "\"," + amount + "," + "\"" + comment + "\"]");
        if (!StringUtils.isEmpty(PASSWORD)) main("walletlock", "[]");
        JSONObject json = JSONObject.parseObject(s);
        return isRight(json) ? json.getString("result") : null;
    }

    private boolean isRight(JSONObject json) {
        return Objects.nonNull(json) && json.containsKey("result")
                && Objects.nonNull(json.get("result"));
    }

    private String main(String method, String condition) throws Exception {
        String result;
        String tonce = "" + (System.currentTimeMillis() * 1000);
        authenticator();

        String params = "tonce=" + tonce + "&accesskey="
                + ACCESS_KEY
                + "&requestmethod=post&id=1&method=" + method + "&params=" + condition;

        String hash = getSignature(params, SECRET_KEY);

        String url = "http://" + ACCESS_KEY + ":" + SECRET_KEY + "@" + IP + ":" + PORT;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        //连接时间
        con.setConnectTimeout(45000);
        //响应时间
        con.setReadTimeout(45000);
        String userpass = ACCESS_KEY + ":" + hash;
        String basicAuth = "Basic "
                + DatatypeConverter.printBase64Binary(userpass.getBytes());

        con.setRequestMethod("POST");
        con.setRequestProperty("Json-Rpc-Tonce", tonce);
        con.setRequestProperty("params", params);
        con.setRequestProperty("Authorization", basicAuth);

        String postdata = "{\"method\":\"" + method + "\", \"params\":" + condition + ", \"id\": 1}";

        con.setDoOutput(true);
        try {
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postdata);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            log.error("url:" + url + "错误信息：" + e.toString());
            return "{\"result\":null,\"error\": \"connection error\",\"id\":1}";
        }
        int responseCode = con.getResponseCode();
        if (responseCode != 200) {
            log.error("connection error, code is: {}, url is : {}, params is {}", responseCode, url, params);
            return "{\"result\":null,\"error\":" + responseCode + ",\"id\":1}";
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        inputLine = in.readLine();
        response.append(inputLine);
        in.close();
        result = response.toString();
        System.out.println(result);
        return result;
    }
}