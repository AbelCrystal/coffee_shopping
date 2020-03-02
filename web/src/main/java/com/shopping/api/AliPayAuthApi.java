package com.shopping.api;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.shopping.alipay.bean.OauthScopeEnum;
import com.shopping.alipay.bean.OauthTokenRequest;
import com.shopping.alipay.service.AlipayApiService;
import com.shopping.alipay.service.AlipayApiServiceImpl;
import com.shopping.wechat.Consts;
import com.shopping.wechat.WechatUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Api(value = "/aliPay", tags = "支付宝跳转认证")
@RequestMapping("/aliPay")
public class AliPayAuthApi {
    public static final Logger log = LoggerFactory.getLogger(AliPayAuthApi.class);
    @Value("${pay.authRedirectPage}")
    private String authRedirectPage;
    /**
     * 跳转支付宝认证
     *
     * @param request
     * @param response
     * @param redirects
     * @return
     */
    @ApiOperation(value = "用户支付宝认证跳转", notes = "用户支付宝认证跳转", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tableNo", value = "桌号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "supperlierId", value = "门店", required = false, dataType = "String"),
    })
    @RequestMapping(path = "/AlipayOauth", method = RequestMethod.GET)
    public ModelAndView oauth(String tableNo, String supperlierId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirects) {
        ModelAndView model = new ModelAndView();
        String tableInfo = tableNo + "@@@" + supperlierId;
        try {
            String state=tableInfo;
            String bindUrl ="http://" + Consts.DOMAIN + "/aliPay/bindUser";
            AlipayApiService service = new AlipayApiServiceImpl();
            String aliAuthStr = service.getOauthUrl(state,bindUrl, OauthScopeEnum.AUTH_BASE);
            model.setViewName("redirect:" + aliAuthStr);
            return model;
        } catch (Exception e) {
            log.error("跳转支付宝失败：", e);
        }
        return model;
    }

    @RequestMapping(path = "/bindUser", method = RequestMethod.GET)
    public ModelAndView bindUser( HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        String state=request.getParameter("state");//获取的自定义参数
        String[] strArry = state.split("@@@");
        OauthTokenRequest oauthRequest = new OauthTokenRequest();
        oauthRequest.setCode(request.getParameter("auth_code"));
        oauthRequest.setGrant_type(OauthTokenRequest.GRANTTYPE_AUTHORIZATION_CODE);
        AlipayApiService service = new AlipayApiServiceImpl();
        AlipaySystemOauthTokenResponse response = null;
        try {
            response = service.oauthToken(oauthRequest);
        String aliUUID=response.getUserId();
        log.info("自定义参数state:"+state);
        log.info("用户UUID："+response.getUserId());
        System.out.println("userId:" + response.getUserId());
        String redirect = authRedirectPage+"?tableNo="+strArry[0]+"&supperlierId="+strArry[1]+"&openId="+response.getUserId();
        model.setViewName("redirect:" + redirect);
        } catch (AlipayApiException e) {
            log.info("支付宝认证失败");
            e.printStackTrace();
        }
        return model;
    }
}
