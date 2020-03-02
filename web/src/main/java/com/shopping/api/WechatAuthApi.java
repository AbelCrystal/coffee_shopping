package com.shopping.api;

import com.shopping.wechat.Consts;
import com.shopping.wechat.WechatUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;


@Controller
@Api(value = "/wechat", tags = "微信跳转认证")
@RequestMapping("/wechat")
public class WechatAuthApi {
    public static final Logger log = LoggerFactory.getLogger(WechatAuthApi.class);

    @Value("${pay.authRedirectPage}")
    private String authRedirectPage;

    /**
     * 初始化
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 微信会在配置的回调地址上加上signature,nonce,timestamp,echostr4个参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        log.info("微信传递的签名参数  signature：{} timestamp：{} nonce:{} echostr:{}", signature, timestamp, nonce, echostr);
        // 1).排序
        String sortString = WechatUserUtil.sort(Consts.SELF_CODE_WX_TOKEN, timestamp, nonce);
        // 2).加密
        String mytoken = WechatUserUtil.sha1(sortString);
        // 3).校验签名
        if (!StringUtils.isEmpty(mytoken) && mytoken.equals(signature)) {
            log.info("微信签名校验通过。");
            response.getWriter().println(echostr);
        } else {
            log.warn("微信签名校验失败。");
        }
    }

    /**
     * 跳转微信认证
     *
     * @param request
     * @param response
     * @param redirects
     * @return
     */
    @ApiOperation(value = "用户微信认证跳转", notes = "用户微信认证跳转", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableNo", value = "桌号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "supperlierId", value = "门店", required = false, dataType = "String"),
    })
    @RequestMapping(path = "/oauth", method = RequestMethod.GET)
    public ModelAndView oauth(String tableNo, String supperlierId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirects) {
        ModelAndView model = new ModelAndView();
        log.info("进入微信认证tableNo:{}  supperlierId:{}", tableNo, supperlierId);

        try {
            String bindUrl = URLEncoder.encode("http://" + Consts.DOMAIN + "/wechat/bindUser", Consts.ENCODING);
            //snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
            // snsapi_userinfo （弹出授权页面，可通过openid拿到昵）
            String tableInfo = tableNo + "@@@" + supperlierId;
            String wechatAuthUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=BIND_URL&response_type=code&scope=snsapi_base&state=TABLEIFNO&connect_redirect=1#wechat_redirect".replaceAll("APPID", Consts.AppId).replaceAll("BIND_URL", bindUrl).replaceAll("TABLEIFNO", tableInfo);
            model.setViewName("redirect:" + wechatAuthUrl);
            return model;
        } catch (Exception e) {
            log.error("跳转微信授权失败：", e);
        }
        return model;
    }

    /**
     * 微信回调
     *
     * @param code
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(path = "/bindUser", method = RequestMethod.GET)
    public ModelAndView bindUser(String code, String state, HttpServletRequest request, HttpServletResponse response) {

        log.info("参数{}", state);
        ModelAndView model = new ModelAndView();
        try {
            if (!StringUtils.isEmpty(code)) {
                log.info("获取到的 wechat code:{}", code);

            } else {
                log.info("未获取到来自  wechat 端的 code");
            }
        } catch (Exception e) {
            log.error("微信回调异常{}", e.getMessage());
        }
        String openid = WechatUserUtil.exchangeCode2OpenId(code);
        String[] strArry = state.split("@@@");

        String redirect = authRedirectPage+"?tableNo="+strArry[0]+"&supperlierId="+strArry[1]+"&openId="+openid;
        model.setViewName("redirect:" + redirect);
        return model;
    }

}
