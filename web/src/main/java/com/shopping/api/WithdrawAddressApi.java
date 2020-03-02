package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.CurrencyBean;
import com.shopping.entity.User;
import com.shopping.entity.WithdrawAddressBean;
import com.shopping.enums.GlobalIsDefaultEnums;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.service.common.ConstantMap;
import com.shopping.service.money.CurrencyService;
import com.shopping.service.money.WithdrawAddressService;
import com.shopping.unit.Constants;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.money.WithdrawAddressVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @Des: 用户提币地址
 * @author: Darryl_Tang
 * @Time: 2019-07-03 14:40
 */

@Api(value = "/api/account", tags = "充提币")
@RestController
@RequestMapping(value = "/api/account")
public class WithdrawAddressApi {

    Logger logger = LoggerFactory.getLogger(WithdrawAddressApi.class);

    @Autowired
    private WithdrawAddressService withdrawAddressService;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ConstantMap constantMap;

    /**
     * @description: 获取用户提币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019-07-03 14:12
     */
    @ApiOperation(value = "获取用户提币地址列表", notes = "获取用户提币地址",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currencyId", value = "币种id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "withdrawId", value = "提币地址id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "当前页", required = false, defaultValue = "1", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示", required = false, defaultValue = "10", dataType = "Integer")
    })
    @GetMapping(value = "/getWithdrawAddress")
    @UserLoginToken
    public MessageVO<BaseListResult<List<WithdrawAddressVo>>> getWithdrawAddress(HttpSession session,
                                                                                 @RequestParam(required = false) String currencyId,
                                                                                 @RequestParam(required = false) String withdrawId,
                                                                                 @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) throws Exception {
        BaseListResult<List<WithdrawAddressVo>> baseListResult = new BaseListResult<>();
        User user = (User) session.getAttribute(session.getId());
        try {
            if (StringUtils.isEmpty(currencyId)) {
                CurrencyBean currencyBean = currencyService.selectListByName(constantMap.getString(Constants.CurrencyName));
                if (null != currencyBean) {
                    currencyId = currencyBean.getId();
                }
            }
            Page<List<WithdrawAddressBean>> page = PageHelper.startPage(pageNum, pageSize);
            List<WithdrawAddressBean> withdrawAddressBeanList = withdrawAddressService.selectListById(withdrawId, user.getId(), currencyId);
            List<WithdrawAddressVo> withdrawAddressVoList = new ArrayList<>();
            for (WithdrawAddressBean withdrawAddressBean : withdrawAddressBeanList) {
                WithdrawAddressVo withdrawAddressVo = new WithdrawAddressVo();
                BeanUtils.copyProperties(withdrawAddressBean, withdrawAddressVo);
                withdrawAddressVoList.add(withdrawAddressVo);
            }
            baseListResult.setListReult(withdrawAddressVoList);
            baseListResult.setTotal(page.getTotal());
            baseListResult.setPageNum(pageNum);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder(baseListResult)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    /**
     * @description: 添加提币地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/3 0003 14:43
     */
    @ApiOperation(value = "添加提币地址", notes = "添加提币地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "提币地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "label", value = "备注", required = true, dataType = "String"),
            @ApiImplicitParam(name = "currencyId", value = "币种id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认", required = false, defaultValue = "1", dataType = "String")
    })
    @PostMapping(value = "/addWithdrawAddress")
    @UserLoginToken
    public MessageVO addWithdrawAddress(@RequestParam(required = false) String currencyId,
                                        String address,
                                        String label,
                                        HttpSession session,
                                        @RequestParam(required = false, defaultValue = "1") String isDefault) throws Exception {
        User user = (User) session.getAttribute(session.getId());
        if (StringUtils.isEmpty(currencyId)) {
            CurrencyBean currencyBean = currencyService.selectListByName(constantMap.getString(Constants.CurrencyName));
            if (null != currencyBean) {
                currencyId = currencyBean.getId();
            }
        }
        List<WithdrawAddressBean> withdrawAddressBeanList = withdrawAddressService.selectListById(null,user.getId(),currencyId);
        if (CollectionUtils.isEmpty(withdrawAddressBeanList)) {
            isDefault = GlobalIsDefaultEnums.YES.getCode();
        }
        address = HtmlUtils.htmlEscape(address);
        WithdrawAddressBean withdrawAddressBean = new WithdrawAddressBean();
        withdrawAddressBean.setWithdrawId(IdWorker.getNewInstance().nextIdToString());
        withdrawAddressBean.setUserId(user.getId());
        withdrawAddressBean.setCurrencyId(currencyId);
        withdrawAddressBean.setAddress(address);
        withdrawAddressBean.setLabel(label);
        withdrawAddressBean.setIsDelete(GlobalIsDeleteEnums.IS_NORMAL.getCode());
        withdrawAddressBean.setIsDefault(isDefault);
        withdrawAddressBean.setCreateTime(DateUtils.getDBDate());
        boolean flag = withdrawAddressService.addWithdrawAddress(withdrawAddressBean);
        if (flag) {
            return MessageVO.builder().msgCode(MessageEnums.ADD_SUCCESS).build();
        }
        return MessageVO.builder().msgCode(MessageEnums.ADD_FAIL).build();
    }


    @ApiOperation(value = "修改提币地址", notes = "修改提币地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "address", value = "提币地址", required = true, dataType = "String"),
            @ApiImplicitParam(name = "label", value = "备注", required = true, dataType = "String"),
            @ApiImplicitParam(name = "withdrawId", value = "提币地址id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "isDefault", value = "是否默认", required = false, dataType = "String")
    })
    @PostMapping("/updateWithdrawAddress")
    @UserLoginToken
    public MessageVO updateWithdrawAddress(@RequestParam(value = "address") String address,
                                           @RequestParam(value = "withdrawId") String withdrawId,
                                           @RequestParam(value = "label") String label,
                                           @RequestParam(value = "isDefault",required = false) String isDefault, HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        List<WithdrawAddressBean> withdrawAddressBeanList = withdrawAddressService.selectListById(withdrawId, user.getId(),null);
        boolean flag = false;
        if (withdrawAddressBeanList.size() != 0) {
            WithdrawAddressBean withdrawAddressBean = withdrawAddressBeanList.get(0);
            if(!StringUtils.isEmpty(address)) {
                address = HtmlUtils.htmlEscape(address);
                withdrawAddressBean.setAddress(address);
            }
            if (!StringUtils.isEmpty(label)) withdrawAddressBean.setLabel(label);
            if (!StringUtils.isEmpty(isDefault)) withdrawAddressBean.setIsDefault(isDefault);
            withdrawAddressBean.setUpdateTime(DateUtils.getDBDate());
            flag = withdrawAddressService.updateWithdrawAddress(withdrawAddressBean);
        }
        if (flag) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.UPDATE_SUCCESS)
                    .build();
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.UPDATE_Fail)
                .build();
    }


    @ApiOperation(value = "删除提币地址", notes = "删除提币地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/deleteWithdrawAddress")
    @UserLoginToken
    public MessageVO deleteWithdrawAddress(@RequestParam(required = true) String withdrawId,HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        List<WithdrawAddressBean> withdrawAddressBeanList = withdrawAddressService.selectListById(withdrawId,user.getId(),null);
        if (withdrawAddressBeanList.size() == 1) {
            WithdrawAddressBean withdrawAddressBean = withdrawAddressBeanList.get(0);
            withdrawAddressBean.setIsDelete(GlobalIsDeleteEnums.IS_DISABLE.getCode());
            withdrawAddressBean.setUpdateTime(DateUtils.getDBDate());
            withdrawAddressService.updateById(withdrawAddressBean);
            return MessageVO.builder()
                    .msgCode(MessageEnums.DELETE_SUCCESS)
                    .build();
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.DELETE_FAIL)
                .build();
    }

    @ApiOperation(value = "设置默认提币地址", notes = "设置默认提币地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/updateDefaultWithdrawAddress")
    @UserLoginToken
    public MessageVO updateDefaultWithdrawAddress(String withdrawId,HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        boolean flag = withdrawAddressService.updateDefaultWithdrawAddress(withdrawId,user.getId());
        if (flag) {
            return MessageVO.builder()
                    .msgCode(MessageEnums.SETTING_SUCCESS)
                    .build();
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.SETTING_FAILD)
                .build();
    }
}
