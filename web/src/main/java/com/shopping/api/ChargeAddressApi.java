package com.shopping.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.ChargeAddressBean;
import com.shopping.entity.CurrencyBean;
import com.shopping.entity.ProductCategory;
import com.shopping.entity.User;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.service.common.ConstantMap;
import com.shopping.service.money.ChargeAddressService;
import com.shopping.service.money.CurrencyService;
import com.shopping.unit.*;
import com.shopping.vo.MessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * @Des: 用户充币地址
 * @author: Darryl_Tang
 * @Time: 2019-07-02 21:40
 */

@Api(value = "/api/account", tags = "充提币")
@RestController
@RequestMapping(value = "/api/account")
public class ChargeAddressApi {

    Logger logger = LoggerFactory.getLogger(ChargeAddressApi.class);
    @Autowired
    private ChargeAddressService chargeAddressService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConstantMap constantMap;

    /**
     * @description: 获取用户充币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019-07-02 21:42
     */
    @ApiOperation(value = "获取用户充币地址", notes = "获取用户充币地址", httpMethod = "GET")
    @GetMapping(value = "/getChargeAddress")
    @UserLoginToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyId", value = "币种id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "create", value = "是否创建", required = false, dataType = "boolean")
    })
    public MessageVO getChargeAddress(@RequestParam(required = false) String currencyId, HttpSession session,
                                      @RequestParam(required = false, defaultValue = "0") boolean create) throws Exception {
        User user = (User) session.getAttribute(session.getId());
        if (StringUtils.isEmpty(user.getId())) {
            throw new RuntimeException("PLEASELOGIN");
        }
        if (StringUtils.isEmpty(currencyId)) {
            CurrencyBean currencyBean = currencyService.selectListByName(constantMap.getString(Constants.CurrencyName));
            if (null != currencyBean) {
                currencyId = currencyBean.getId();
            }
        }
        EntityWrapper<CurrencyBean> qryCurrencyBeanWrapper = new EntityWrapper<>();
        qryCurrencyBeanWrapper.eq("id", currencyId);
        CurrencyBean currencyBean = currencyService.selectOne(qryCurrencyBeanWrapper);
        ChargeAddressBean chargeAddressBean = chargeAddressService.selectListById(user.getId(), currencyId);
        if (Objects.isNull(chargeAddressBean)) {
            //不存在 则 生成地址
            TZBMessage tzbMessage = new TZBMessage();
            tzbMessage.setACCESS_KEY(currencyBean.getAccessKey());
            tzbMessage.setIP(currencyBean.getIp());
            tzbMessage.setPORT(currencyBean.getPort());
            tzbMessage.setSECRET_KEY(currencyBean.getSecrtKey());
            if (tzbMessage.getACCESS_KEY() == null
                    || tzbMessage.getIP() == null
                    || tzbMessage.getPORT() == null
                    || tzbMessage.getSECRET_KEY() == null) {
                return MessageVO.builder().msgCode(MessageEnums.CURRENCY_INFO_ERROR).build();
            }
            TZBUtils tzbUtils = new TZBUtils(tzbMessage);
            String address;
            try {
                address = tzbUtils.getAddress(user.getId() + "");
            } catch (Exception e) {
                //e.printStackTrace();
                return MessageVO.builder().msgCode(MessageEnums.CREATE_ADDRESS_ERROR).build();
            }

            if (StringUtils.isBlank(address)) { //地址异常
                return MessageVO.builder().msgCode(MessageEnums.CREATE_ADDRESS_ERROR).build();
            } else {
                chargeAddressBean = new ChargeAddressBean();
                chargeAddressBean.setChargeId(IdWorker.getNewInstance().nextIdToString());
                chargeAddressBean.setCurrencyId(currencyId);
                chargeAddressBean.setAddress(address);
                chargeAddressBean.setUserId(user.getId());
                chargeAddressBean.setCreateTime(DateUtils.getDBDate());
                chargeAddressBean.setUpdateTime(DateUtils.getDBDate());
                chargeAddressBean.setIsDelete(GlobalIsDeleteEnums.IS_NORMAL.getCode());
                chargeAddressService.insert(chargeAddressBean);
            }
        }
        return MessageVO.builder(chargeAddressBean).msgCode(MessageEnums.SUCCESS).build();

    }
}
