package com.shopping.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.sms.MessageTemplateService;
import com.shopping.service.sms.SMSService;
import com.shopping.service.user.*;
import com.shopping.unit.*;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.OrderVo;
import com.shopping.vo.sms.request.SmsSendRequest;
import com.shopping.vo.sms.resqonse.SmsSendResponse;
import com.shopping.vo.user.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author abel
 * @date 2019-05-30 20:45
 */
@Api(value = "/api/user", tags = "用户操作功能")
@RestController
@RequestMapping(value = "/api/user")
public class UserApi {
    Logger logger = LoggerFactory.getLogger(UserApi.class);
    @Autowired
    private UserService userService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Value("${smsconfig.smsurl}")
    private String sumUrl;
    @Value("${smsconfig.account}")
    private String account;
    @Value("${smsconfig.password}")
    private String password;
    @Value("${smsconfig.timeout}")
    private long smsTimeout;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserAccountDetailService userAccountDetailService;

    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageVO<UserLoginVo> login(@RequestBody UserLoginVo userLoginVo, HttpServletRequest request) {
        String smscode = redisUtil.get(RedisPrefixEmums.LOGIN.getCode() + ":" + userLoginVo.getPhone());
        if (!userLoginVo.getSmsCode().equals(smscode)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.SMS_CODE_ERROR)
                    .build();
        }
        User userSesson = (User) request.getSession().getAttribute(request.getSession().getId());
        User user = userService.findByPhone(userLoginVo.getPhone());
        if (user == null) {
            user = new User();
            user.setId(IdWorker.getNewInstance().nextIdToString());
            user.setPhone(userLoginVo.getPhone());
            user.setCreateTime(DateUtils.getDBDate());
            logger.info("用户注册信息{}", JSONObject.toJSON(user));
            userService.registUser(user);

        }
        if (userSesson != null) {
            user.setAliUUId(userSesson.getAliUUId());
            user.setWxUUId(userSesson.getWxUUId());
            user.setTableNo(userSesson.getTableNo());
            user.setSupplierId(userSesson.getSupplierId());
        }
        userService.insertOrUpdate(user);
        //登陆LOG记录
        UserLoginLog userLoginLog = new UserLoginLog();
        userLoginLog.setLoginId(IdWorker.getNewInstance().nextIdToString());
        userLoginLog.setLoginIp(IpUtil.getIpAddr(request));
        userLoginLog.setLoginTime(DateUtils.getDBDate());
        userLoginLogService.insert(userLoginLog);//登陆日志
        request.getSession().setAttribute(request.getSession().getId(), user);
        return MessageVO.builder()
                .msgCode(MessageEnums.LOGIN_SUCCESS)
                .build();
    }

    @PostMapping("/sendSms")
    @ApiOperation(value = "发送短信", notes = "发送短信接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "smsType", value = "短信类型,0为登录短信,1支付短信", required = true, dataType = "String")
    })
    public MessageVO sendSms(String phone, String smsType, HttpServletRequest request) {

        String verifyCode = MessageCode.randomCode();
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("verifyCode", verifyCode);
        // 获取短信模板内容
        MessageTemplate messageTemplate = null;
        String msg = "";
        String redisPrefix = "";
        switch (smsType) {
            case "0":
                messageTemplate = messageTemplateService.findBySmsType(SmsTypeEmums.LOGIN.getCode());
                msg = MessageFormat.format(messageTemplate.getTemplate(), phone, verifyCode);
                redisPrefix = RedisPrefixEmums.LOGIN.getCode();
                break;
            case "1":
                messageTemplate = messageTemplateService.findBySmsType(SmsTypeEmums.PAY.getCode());
                msg = MessageFormat.format(messageTemplate.getTemplate(), verifyCode);
                redisPrefix = RedisPrefixEmums.PAY.getCode();
                break;
            default:
                msg = MessageFormat.format(messageTemplate.getTemplate(), phone, verifyCode);
        }

        int flag = saveSmsRecord(redisPrefix, smsType, phone, msg, verifyCode);
        if (flag > 0) {
            return MessageVO.builder(map)
                    .msgCode(MessageEnums.SMS_CODE_SUCCESS)
                    .build();
        } else {
            return MessageVO.builder()
                    .msgCode(MessageEnums.FAIL)
                    .build();
        }

    }

    private int saveSmsRecord(String redisPrefix, String smsType, String phone, String msg, String verifyCode) {
        //状态报告
        String report = "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone, report);

        String requestJson = JSON.toJSONString(smsSingleRequest);
        logger.info("短信验证请求: " + requestJson);
        String response = ChuangLanSmsUtil.sendSmsByPost(sumUrl, requestJson);
        logger.info("短信验证响应 :" + response);
        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
        SmsRecord smsRecord = new SmsRecord();
        String code = smsSingleResponse.getCode();
        if ("0".equals(code)) {
            redisUtil.setForTimeCustom(redisPrefix + ":" + phone, verifyCode, smsTimeout, TimeUnit.SECONDS);
            smsRecord.setId(IdWorker.getNewInstance().nextIdToString());
            smsRecord.setSended(SmsStatus.SENDED.getCode());
            smsRecord.setMobileNumber(phone);
            smsRecord.setSmsContent(msg);
            smsRecord.setSmsType(smsType);
            smsRecord.setDeadTime(DateUtils.addSeconds(DateUtils.getDBDate(), (int) smsTimeout));
            smsRecord.setCreateTime(DateUtils.getDBDate());
            smsService.insert(smsRecord);
            return 1;

        } else {
            smsRecord.setId(IdWorker.getNewInstance().nextIdToString());
            smsRecord.setSended(SmsStatus.UN_SENDED.getCode());
            smsRecord.setMobileNumber(phone);
            smsRecord.setSmsContent(msg);
            smsRecord.setSmsType(smsType);
            smsRecord.setCreateTime(DateUtils.getDBDate());
            smsRecord.setDeadTime(DateUtils.addSeconds(DateUtils.getDBDate(), (int) smsTimeout));
            smsService.insert(smsRecord);
            return 0;
        }
    }

    @ApiOperation(value = "验证是否登录", notes = "验证是否登录", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @UserLoginToken
    @GetMapping("/getMessage")
    public MessageVO<User> getMessage(HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        return MessageVO.builder(user)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "验证微信支付宝", notes = "验证微信支付宝", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String"),
            @ApiImplicitParam(name = "tableNo", value = "桌号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "supplierId", value = "商户Id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oauthType", value = "认证属性：1支付宝,2微信", required = true, dataType = "String"),
    })
    @GetMapping("/oauthSystem")
    public MessageVO<User> oauthSystem(String openId, String tableNo, String supplierId, String oauthType, HttpSession session) {
        if (StringUtils.isEmpty(openId)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.FAIL)
                    .build();
        }
        if (StringUtils.isEmpty(tableNo)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.FAIL)
                    .build();
        }
        if (StringUtils.isEmpty(supplierId)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.FAIL)
                    .build();
        }
        if (StringUtils.isEmpty(oauthType)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.PAYMENT_FAIL)
                    .build();
        }
        User user = new User();
        if (OrderPayTypeEnums.ZHIFUBAO.getCode().equals(oauthType)) {
            user.setAliUUId(openId);
        }
        if (OrderPayTypeEnums.WEIXIN.getCode().equals(oauthType)) {
            user.setWxUUId(openId);
        }

        User userAuth = userService.selectbyUUid(user);
        if (userAuth != null) {
            user.setPhone(userAuth.getPhone());
            user.setId(userAuth.getId());
        }
        user.setSupplierId(supplierId);
        user.setTableNo(tableNo);
        session.setAttribute(session.getId(), user);
        return MessageVO.builder()
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "登出", notes = "系统登出")
    @UserLoginToken
    @PostMapping("/logout")
    public MessageVO userLogout(HttpSession session) {
        User userSesson = (User) session.getAttribute(session.getId());
        userSesson.setPhone("");
        userSesson.setId("");
        session.setAttribute(session.getId(), userSesson);
        return MessageVO.builder()
                .msgCode(MessageEnums.LOGOUT_SUCCESS)
                .build();
    }

    @ApiOperation(value = "修改密码", notes = "修改密码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @UserLoginToken
    @PostMapping("/modifyPassWord")
    public MessageVO modifyPassWord(@RequestBody UserInfo user, HttpServletRequest request) {
        User userInfo = userService.findByPhone(user.getPhone());
        if (!MD5.MD(user.getOldPassWord()).equals(userInfo.getPassword())) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.ORIGIGINAL_ERROR)
                    .build();
        }
        if (MD5.MD(user.getNewPassWord()).equals(userInfo.getPassword())) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.PASSWORDIDENTICAL_ERROR)
                    .build();
        }
        userInfo.setPassword(MD5.MD(user.getNewPassWord()));
        boolean flag = userService.updateById(userInfo);
        if (!flag) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.REGISTER_Fail)
                    .build();
        }
        request.getSession().removeAttribute(request.getSession().getId());
        return MessageVO.builder()
                .msgCode(MessageEnums.UPDATE_SUCCESS)
                .build();
    }


    @ApiOperation(value = "验证手机验证码", notes = "验证手机验证码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @UserLoginToken
    @PostMapping("/verifyUpdatePhoneCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "验证码类型1：修改手机号验证码 2：修改支付密码验证码", required = true, dataType = "int")
    })
    public MessageVO verifyUpdatePhoneCode(String code, String phone, int type) {
        String key = RedisPrefixEmums.UPDATEPHONE.getCode();
        if (type == 1) {
            key = RedisPrefixEmums.UPDATEPHONE.getCode();
        } else if (type == 2) {
            key = RedisPrefixEmums.SETPAYPASSWORD.getCode();
        }
        String smscode = redisUtil.get(key + ":" + phone);
        if (smscode == null || !smscode.equals(code)) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.SMS_CODE_ERROR)
                    .build();
        }
        //标记设置通过
        redisUtil.setForTimeMIN(key + "verify:" + phone, "yes", 30);
        return MessageVO.builder()
                .msgCode(MessageEnums.VERIFICATION_SUCCESS)
                .build();
    }


    @ApiOperation(value = "个人信息查询", notes = "个人信息查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @UserLoginToken
    @PostMapping("/getUserDetail")
    public MessageVO<UserDetailVo> getUserDetail(HttpSession session) {
        String userId = ((User) session.getAttribute(session.getId())).getId();
        User userInfo = userService.findById(userId);
        UserDetailVo userDetailVo = new UserDetailVo();
        if (userInfo.getBirthday() != null) {
            userDetailVo.setBirthday(DateUtils.getFormatTime(userInfo.getBirthday(), "yyyy-MM-dd"));
        }
        userDetailVo.setProfession(userInfo.getProfession());
        userDetailVo.setUserImgUrl(userInfo.getUserImgUrl());
        userDetailVo.setSex(userInfo.getSex());
        return MessageVO.builder(userDetailVo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }


    @ApiOperation(value = "个人信息修改", notes = "个人信息修改", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @UserLoginToken
    @PostMapping("/modifyUserDetail")
    public MessageVO<UserDetailVo> modifyUserDetail(@RequestBody UserDetailVo userDetailVo, HttpSession session) {
        String userId = ((User) session.getAttribute(session.getId())).getId();
        User userInfo = userService.findById(userId);
        if (userDetailVo.getBirthday() != null) {
            userInfo.setBirthday(DateUtils.getDate(userDetailVo.getBirthday(), "yyyy-MM-dd"));
        }
        userInfo.setSex(userDetailVo.getSex());
        userInfo.setUserImgUrl(userDetailVo.getUserImgUrl());
        userInfo.setProfession(userDetailVo.getProfession());
        userInfo.setUpdateTime(DateUtils.getDBDate());
        int i = userService.updateUser(userInfo);

        if (i > 0) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.UPDATE_SUCCESS)
                    .build();
        } else {
            return MessageVO.builder()
                    .msgCode(MessageEnums.UPDATE_Fail)
                    .build();
        }

    }

    @ApiOperation(value = "用户钱包", notes = "用户钱包", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/userAccount")
    @UserLoginToken
    public MessageVO<MyAccountVo> userAccount(HttpSession session) throws RuntimeException {

        User user = (User) session.getAttribute(session.getId());
        if (com.alibaba.druid.util.StringUtils.isEmpty(user.getId())) {
            logger.info("用户为空");
            throw new RuntimeException("PLEASELOGIN");     //请登录
        }
        logger.info("个人信息{}", JSONObject.toJSONString(user));
        List<UserAccountBean> listAccount = userAccountService.selectListByUserId(user.getId());
        BigDecimal btclPrice = new BigDecimal(0);
        BigDecimal rmbPrice = new BigDecimal(0);
        if (listAccount != null && listAccount.size() > 0) {
            for (UserAccountBean userAccountBean :
                    listAccount) {
                //修改订单状态
                if (userAccountBean.getAccoutType().equals(AccountTypeEnums.TONGZHENG.getCode())) {
                    EntityWrapper<CurrencyBean> currencyBeanEntityWrapper = new EntityWrapper<>();
                    currencyBeanEntityWrapper.eq("id", userAccountBean.getCurrencyId());
                    CurrencyBean currencyBean = currencyService.selectOne(currencyBeanEntityWrapper);
                    if (currencyBean != null) {
                        if (CurrencyTypeEnues.BTC.getCode().equals(currencyBean.getCurrencyName())) {
                            btclPrice = MathUtils.add(btclPrice, userAccountBean.getUsable());
                        } else if (CurrencyTypeEnues.ETH.getCode().equals(currencyBean.getCurrencyName())) {
                            BigDecimal etcCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.ETH.getCode()));
                            BigDecimal btcCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.BTC.getCode()));
                            BigDecimal rmbCoin = MathUtils.multiply(userAccountBean.getUsable(), etcCoinRatio);
                            btclPrice = MathUtils.add(btclPrice, MathUtils.divide8(rmbCoin, btcCoinRatio));
                        } else if (CurrencyTypeEnues.KEO.getCode().equals(currencyBean.getCurrencyName())) {
                            BigDecimal keoCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.KEO.getCode()));
                            BigDecimal btcCoinRatio = new BigDecimal(redisUtil.get(FeedbackStatusEnums.BTC.getCode()));
                            BigDecimal rmbCoin = MathUtils.multiply(userAccountBean.getUsable(), keoCoinRatio);
                            btclPrice = MathUtils.add(btclPrice, MathUtils.divide8(rmbCoin, btcCoinRatio));
                        }
                    }

                } else {
                    rmbPrice = userAccountBean.getAmount();
                }
            }
        }
        MyAccountVo myAccountVo = new MyAccountVo();
        myAccountVo.setBtcAssets(btclPrice);
        myAccountVo.setRmbAssets(rmbPrice);
        myAccountVo.setPhone(user.getPhone());
        myAccountVo.setUserId(user.getId());
        return MessageVO.builder(myAccountVo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "用户资金记录", notes = "用户资金记录", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", name = "pageNum", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(value = "页面大小", name = "pageSize", paramType = "query", dataType = "int", required = true),
            @ApiImplicitParam(value = "明细类型:0为虚拟币，1为人民币", name = "accountType", paramType = "query", dataType = "String", required = true)
            , @ApiImplicitParam(value = "币种Id", name = "currencyId", paramType = "query", dataType = "String", required = false)
    })
    @GetMapping("/userAccountDetail")
    @UserLoginToken
    public MessageVO<MyAccountDetailVo> userAccountDetail(@RequestParam(defaultValue = "1", required = true) int pageNum,
                                                          @RequestParam(defaultValue = "10", required = true) int pageSize
            , @RequestParam(required = true) String accountType
            , @RequestParam(required = false) String currencyId
            , HttpSession session) throws RuntimeException {
        User user = (User) session.getAttribute(session.getId());
        if (com.alibaba.druid.util.StringUtils.isEmpty(user.getId())) {
            logger.info("用户为空");
            throw new RuntimeException("PLEASELOGIN");     //请登录
        }
        logger.info("个人信息{}", JSONObject.toJSONString(user));
        BigDecimal userAccount = new BigDecimal(0);
        UserAccountBean userAccountBeans = null;
        List<UserAccountDetail> rmbDetaillist=new ArrayList<>();
        if (AccountTypeEnums.RENMINGBI.getCode().equals(accountType)) {
            userAccountBeans = userAccountService.selectListByCurrencyId("", user.getId(), AccountTypeEnums.RENMINGBI.getCode());   //查询用户币种信息
        } else {
            userAccountBeans = userAccountService.selectListByCurrencyId(currencyId, user.getId(), AccountTypeEnums.TONGZHENG.getCode());   //查询用户币种信息
        }
        if (userAccountBeans != null) {
            userAccount=userAccountBeans.getUsable();
        }

        MyAccountDetailVo result = new MyAccountDetailVo();
        Page<List<UserAccountDetail>> page = PageHelper.startPage(pageNum, pageSize);
        if (AccountTypeEnums.RENMINGBI.getCode().equals(accountType)) {
            rmbDetaillist = userAccountDetailService.selectAccountByType(accountType, user.getId(), "");
        } else {
            rmbDetaillist = userAccountDetailService.selectAccountByType(accountType, user.getId(), currencyId);
        }
        result.setAccount(userAccount);
        result.setListReult(rmbDetaillist);
        result.setPageNum(pageNum);
        result.setTotal(page.getTotal());
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }
}
