package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.common.ConstantMap;
import com.shopping.service.message.MessageService;
import com.shopping.service.money.CapitalOperationService;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.money.WithdrawFeesService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.Constants;
import com.shopping.unit.MD5;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.money.CapitalOperationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Des: 用户充提币记录
 * @author: Darryl_Tang
 * @Time: 2019-07-02 21:40
 */

@Api(value = "/api/account", tags = "充提币")
@RestController
@RequestMapping(value = "/api/account")
public class CapitalOperationApi {

    Logger logger = LoggerFactory.getLogger(CapitalOperationApi.class);

    @Autowired
    private CapitalOperationService capitalOperationService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConstantMap constantMap;

    @Autowired
    private WithdrawFeesService withdrawFeesService;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MessageService messageService;

    /**
     * @description: 获取用户充币记录
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019-07-02 21:42
     */
    @ApiOperation(value = "获取用户充提币记录", notes = "获取用户充提币记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int", defaultValue = "10"),
            @ApiImplicitParam(name = "currencyId", value = "币id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态 充值 0项确认 1项确认 2项确认 3充值成功 提现 0等待提现 1正在处理 2提现中 3提现成功 4提现失败 5用户撤销'", required = false, dataType = "String", defaultValue = "-1"),
            @ApiImplicitParam(name = "type", value = "记录类型 0充币 1提币", required = false, dataType = "String", defaultValue = "-1"),
            @ApiImplicitParam(name = "capitalId", value = "记录id,查询详情使用", required = false, dataType = "String"),
    })
    @GetMapping(value = "/getChargeWithdrawList")
    @UserLoginToken
    public MessageVO<BaseListResult<List<CapitalOperationVo>>> getChargeWithdrawList(@RequestParam(required = false) String currencyId,
                                           HttpSession session,
                                           @RequestParam(required = false, defaultValue = "-1") String type,
                                           @RequestParam(required = false, defaultValue = "-1") String status,
                                           @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                           @RequestParam(required = false) String capitalId) throws Exception {
        User user = (User) session.getAttribute(session.getId());
        BaseListResult<List<CapitalOperationVo>> baseListResult = new BaseListResult<>();
        StringBuilder filter = new StringBuilder();
        try {
            filter.append("  1=1 ");
            if (!StringUtils.isEmpty(capitalId)) {
                filter.append("and id = '" + capitalId + "'\n");
            } else {
                filter.append(" and user_id = '" + user.getId()+"'\n");
                if (!StringUtils.isEmpty(currencyId)) {
                    filter.append(" and currency_id = '" + currencyId+"'\n");
                }
                if (!status.equals("-1")) {
                    filter.append(" and status = '" + status+"'\n");
                }
                if (!type.equals("-1")) {
                    filter.append(" and type = '" + type+"'\n");
                }
            }
            Page<List<CapitalOperationBean>> page = PageHelper.startPage(pageNum, pageSize);
            List<CapitalOperationBean> capitalOperationBeanList = capitalOperationService.selectListByFilter(filter.toString());
            List<CapitalOperationVo> capitalOperationVoList = new ArrayList<>();
            for (CapitalOperationBean capitalOperationBean : capitalOperationBeanList) {
                CapitalOperationVo capitalOperationVo = new CapitalOperationVo();
                BeanUtils.copyProperties(capitalOperationBean, capitalOperationVo);
                capitalOperationVoList.add(capitalOperationVo);
            }
            baseListResult.setListReult(capitalOperationVoList);
            baseListResult.setTotal(page.getTotal());
            baseListResult.setPageNum(pageNum);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder(baseListResult).msgCode(MessageEnums.SUCCESS).build();
    }


    /**
     * @description: 用户提币
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/6 0006 17:45
     */
    @ApiOperation(value = "用户提币", notes = "用户提币", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyId", value = "提现币种id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "address", value = "提币地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "amount", value = "提币数量", required = true, dataType = "Bigdecimal"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String"),
    })
    @PostMapping("/withdrawCoin")
    @UserLoginToken
    public MessageVO withdrawCoin(@RequestParam(value = "currencyId", required = false) String currencyId,
                                  @RequestParam(value = "address") String address,
                                  @RequestParam(value = "amount") BigDecimal amount,
                                  @RequestParam(value = "code") String code,
                                  HttpSession session) throws Exception {
        try {
            User user = (User) session.getAttribute(session.getId());
            address = HtmlUtils.htmlEscape(address);
            CurrencyBean currencyBean;
            if (StringUtils.isEmpty(currencyId)) {
                currencyBean = currencyService.selectListByName(constantMap.getString(Constants.CurrencyName));
                if (null != currencyBean) {
                    currencyId = currencyBean.getId();
                }
            } else {
                EntityWrapper<CurrencyBean> qryCurrencyBeanWrapper = new EntityWrapper<>();
                qryCurrencyBeanWrapper.eq("id", currencyId);
                currencyBean = currencyService.selectById(currencyId);
            }

            WithdrawFeesBean withdrawFeesBean = withdrawFeesService.findByCoin(currencyId); //查询手续费
            BigDecimal minWithdraw = withdrawFeesBean.getMinWithdraw().compareTo(new BigDecimal(0)) == -1 ? withdrawFeesBean.getMinWithdraw() : new BigDecimal(0.0001);
            BigDecimal maxWithdraw = new BigDecimal(constantMap.getDouble(Constants.MaxWithdrawNum));

            if (amount.compareTo(minWithdraw) == -1) {  //小于最小提现
                new RuntimeException("小于最小提现额度：" + maxWithdraw);
            }

            if (maxWithdraw.compareTo(new BigDecimal(0)) > 0 && amount.compareTo(maxWithdraw) > -1) {
                new RuntimeException("大于最大提现额度：" + maxWithdraw);
            }

            if (Objects.isNull(currencyBean) || currencyBean.getIsWithdraw().equals(GlobalEnums.DISABLE.getCode()) || currencyBean.getStatus().equals(GlobalEnums.DISABLE.getCode())) {
                //不支持提现
                return MessageVO.builder().msgCode(MessageEnums.NONSUPPORT_WITHDRAW).build();
            }

            UserAccountBean userAccountBean = userAccountService.selectListByCurrencyId(currencyId, user.getId(), AccountTypeEnums.TONGZHENG.getCode()); //用户资金
            if (Objects.isNull(userAccountBean) || amount.compareTo(userAccountBean.getUsable()) > 0) {
                //账户余额不足!
                return MessageVO.builder().msgCode(MessageEnums.NOT_ENOUGH_COIN).build();
            }

            //今日提现次数
            int time = capitalOperationService.getTodayCoinWithdrawTimes(user);
            int times = constantMap.getInt(Constants.DAY_DRAW_COIN_TIMES, 0);
            if (time >= times && times > 0) {
                //今日提现次数已达上限
                return MessageVO.builder().msgCode(MessageEnums.WITHDRAW_TIMES_REACHED_LIMIT).build();
            }

            //交易密码为空
            if (StringUtils.isEmpty(user.getPayPassword())) {
                return MessageVO.builder().msgCode(MessageEnums.PAYPASSWORD_IS_NOT_SETTING).build();
            }

            //交易密码不正确
//            if (!user.getPayPassword().equals(MD5.MD(payPassword))) {
//                return MessageVO.builder().msgCode(MessageEnums.PAYPASSWORD_ERROR).build();
//            }
            boolean feeFlag = false;
            if (!StringUtils.isEmpty(constantMap.getString(Constants.WithdrawFeeIsOpen)) && constantMap.getString(Constants.WithdrawFeeIsOpen).equals(WithdrawFeeIsOpenEnums.FEE_IS_OPEN.getCode())) {
                //手续费开启
                if (amount.compareTo(withdrawFeesBean.getWithdraw()) == -1) {
                    return MessageVO.builder().msgCode(MessageEnums.FEE_NOT_ENOUGH).build();    //手续费不足
                }
                feeFlag = true;
            }

            //验证码
            String smscode = redisUtil.get(RedisPrefixEmums.ORDEER_STATE_WAIT_PAYMENT_PREFIX.getCode() + ":" + user.getPhone()); //获取验证码
            if (smscode == null || !smscode.equals(code)) {
                return MessageVO.builder()
                        .msgCode(MessageEnums.SMS_CODE_ERROR)
                        .build();
            }

            //提币 从提出的金额中减少
            boolean result = this.capitalOperationService.updateWithdrawCoin(feeFlag,withdrawFeesBean,address,currencyBean.getId(),amount,user);
            //发送提币申请消息给管理员 暂时无

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder().msgCode(MessageEnums.ADD_SUCCESS).build();
    }

    /**
     *
     *
     * @description: 取消提币
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/9 0009 15:31
     */
    @ApiOperation(value = "取消提币", notes = "取消提币", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/cancelWithdraw")
    @UserLoginToken
    public MessageVO cancelWithdraw(String id,HttpSession session) {
        try {
            User user = (User) session.getAttribute(session.getId());
            EntityWrapper<CapitalOperationBean> qryCapitalOperationBeanWrapper = new EntityWrapper<>();
            qryCapitalOperationBeanWrapper.eq("id", id);
            CapitalOperationBean capitalOperationBean = capitalOperationService.selectOne(qryCapitalOperationBeanWrapper);
            boolean flag = false;
            if (Objects.nonNull(capitalOperationBean)
                    && capitalOperationBean.getUserId().equals(user.getId())
                    && capitalOperationBean.getType().equals(CapitalOperationTypeEnums.COIN_OUT.getCode())
                    && (capitalOperationBean.getStatus().equals(CapitalOperationOutStatusEnums.WAIT_FOR_OPERATION.getCode())
                    || capitalOperationBean.getStatus().equals(CapitalOperationOutStatusEnums.OPERATION_FAILED.getCode()))) {
                flag = capitalOperationService.updateWithdrawStatus(capitalOperationBean);
            }
            if (flag) {
                return MessageVO.builder().msgCode(MessageEnums.CANCEL_SUCCESS).build();
            }
            return MessageVO.builder().msgCode(MessageEnums.CANCEL_ERROR).build();
        } catch (Exception e) {
            return MessageVO.builder(e.getMessage()).msgCode(MessageEnums.CANCEL_ERROR).build();
        }
    }

}
