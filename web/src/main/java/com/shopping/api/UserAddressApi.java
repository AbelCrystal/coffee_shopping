package com.shopping.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.User;
import com.shopping.entity.UserAddressBean;
import com.shopping.enums.GlobalIsDefaultEnums;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.service.user.UserAddressService;
import com.shopping.unit.ClassCompareUtil;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.user.UserAddressVo;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/api/user", tags = "个人中心功能")
@RestController
@RequestMapping(value = "/api/user")
public class UserAddressApi {
    Logger logger = LoggerFactory.getLogger(UserAddressApi.class);

    @Autowired
    private UserAddressService userAddressService;

    /**
     * @description: 增加用户收货地址
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/21 0021 11:43
     */
    @ApiOperation(value = "增加用户收货地址", notes = "增加用户收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/addAddress")
    @UserLoginToken
    public MessageVO addAddress(@Valid @RequestBody UserAddressVo userAddressVo, HttpSession session) {
        try {
            User user = (User) session.getAttribute(session.getId());
            UserAddressBean userAddressBean = new UserAddressBean();
            BeanUtils.copyProperties(userAddressVo, userAddressBean);
            userAddressBean.setAddressId(IdWorker.getNewInstance().nextIdToString());
            userAddressBean.setUserId(user.getId());
            userAddressBean.setCreateTime(DateUtils.getDBDate());
            userAddressBean.setUpdateTime(DateUtils.getDBDate());
            userAddressBean.setIsDelete(GlobalIsDeleteEnums.IS_NORMAL.getCode());
            //增加前需要先查询用户是否有默认地址
            userAddressService.addAddress(userAddressBean);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder().msgCode(MessageEnums.ADD_SUCCESS).build();
    }


    /**
     * @description:查询用户收货地址 默认查询所有
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/21 0021 11:44
     */
    @ApiOperation(value = "查询收货地址列表或详细", notes = "查询收货地址列表或详细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/findAddress")
    @UserLoginToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int", defaultValue = "10"),
            @ApiImplicitParam(name = "addressId", value = "地址id", required = false, dataType = "String"),
    })
    public MessageVO<BaseListResult<List<UserAddressVo>>> findAddress(@RequestParam(required = false) String addressId,
                                                                      @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                      HttpSession session) {
        BaseListResult<List<UserAddressVo>> baseListResult = new BaseListResult<>();
        User user = (User) session.getAttribute(session.getId());
        try {
            Page<List<UserAddressBean>> page = PageHelper.startPage(pageNum, pageSize);
            List<UserAddressBean> userAddressBeanList = userAddressService.selectListById(addressId,user.getId());
            List<UserAddressVo> userAddressVoList = new ArrayList<>();
            for (UserAddressBean userAddressBean : userAddressBeanList) {
                UserAddressVo userAddressVo = new UserAddressVo();
                BeanUtils.copyProperties(userAddressBean, userAddressVo);
                userAddressVoList.add(userAddressVo);
            }
            baseListResult.setListReult(userAddressVoList);
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

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/updateAddress")
    @UserLoginToken
    public MessageVO updateAddress(@Valid @RequestBody UserAddressVo userAddressVo,HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        String addressId = userAddressVo.getAddressId();
        List<UserAddressBean> userAddressBeanList = userAddressService.selectListById(addressId,user.getId());
        boolean flag = false;
        if (userAddressBeanList.size() != 0) {
            UserAddressBean userAddressBean = userAddressBeanList.get(0);   //根据id查询到该收货地址
            UserAddressBean userAddressBean1 = new UserAddressBean();
            BeanUtils.copyProperties(userAddressVo, userAddressBean1);
            userAddressBean1.setUserId(userAddressBean.getUserId());
            userAddressBean1.setCreateTime(userAddressBean.getCreateTime());
            userAddressBean1.setUpdateTime(userAddressBean.getUpdateTime());
            if (ClassCompareUtil.compareObject(userAddressBean, userAddressBean1)) {
                return MessageVO.builder().msgCode(MessageEnums.UPDATE_Fail).build();
            } else {
                userAddressBean1.setUpdateTime(DateUtils.getDBDate());
                flag = userAddressService.updateAddress(userAddressBean1);
            }
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

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/deleteAddress")
    @UserLoginToken
    public MessageVO deleteAddress(@RequestParam(required = true) String addressId,HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        List<UserAddressBean> userAddressBeanList = userAddressService.selectListById(addressId,user.getId());
        if (userAddressBeanList.size() == 1) {
            UserAddressBean userAddressBean = userAddressBeanList.get(0);
            userAddressBean.setIsDelete(GlobalIsDeleteEnums.IS_DISABLE.getCode());
            userAddressBean.setUpdateTime(DateUtils.getDBDate());
            userAddressService.updateById(userAddressBean);
            return MessageVO.builder()
                    .msgCode(MessageEnums.DELETE_SUCCESS)
                    .build();
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.DELETE_FAIL)
                .build();
    }

    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/updateDefaultAddress")
    @UserLoginToken
    public MessageVO updateDefaultAddress(String addressId,HttpSession session) {
        User user = (User) session.getAttribute(session.getId());
        boolean flag = userAddressService.updateDefaultAddress(addressId,user.getId());
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
