package com.shopping.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.*;
import com.shopping.enums.MessageEnums;
import com.shopping.service.product.ProductInfoService;
import com.shopping.service.product.ProductPicInfoService;
import com.shopping.service.user.UserAccountDetailService;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.product.ProductInfoVo;
import com.shopping.vo.product.ProductPicInfoVo;
import com.shopping.vo.user.UserAccountCollectVo;
import com.shopping.vo.user.UserAccountDetailVo;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @date 2019-05-30 20:45
 */
@Api(value = "/api/accountdetail", tags = "资金明细")
@RestController
@RequestMapping(value = "/api/accountdetail")
public class UserAccountDetailApi {
    Logger logger = LoggerFactory.getLogger(UserAccountDetailApi.class);

    @Autowired
    private UserAccountDetailService userAccountDetailService;

    @Autowired
    private ProductPicInfoService productPicInfoService;
    private int numPerPage = 20;


    @GetMapping("/getAccountDetailByUserId")
    @UserLoginToken
    @ApiOperation(value = "资金明细", notes = "资金明细", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "changesType", value = "支付来源:0支出  1收入 2充值 3转币,4冻结，5解冻，6分润", required = false, dataType = "String"),
            @ApiImplicitParam(name = "payType", value = "支付方式：0商城币，1微信，2支付宝", required = false, dataType = "String", defaultValue = "-1"),
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int", defaultValue = "10")

    })
    public MessageVO<BaseListResult<List<UserAccountCollectVo>>> getAccountDetailByUserId(@RequestParam(required = false,defaultValue = "-1") String changesType,
                                                                                          @RequestParam(required = false, defaultValue = "-1") String payType,
                                                                                          @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                                          @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
        String userId = ((User) session.getAttribute(session.getId())).getId();
        List<UserAccountCollectVo> userAccountCollectVoList = new ArrayList<UserAccountCollectVo>();
        Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = userAccountDetailService.getMounth(userId, changesType, payType);
        for (Map<String, Object> map : list) {
            UserAccountCollectVo userAccountCollectVo = new UserAccountCollectVo();
            String time = map.get("time").toString();
            double accountIn = 0;
            double accountOut = 0;
            if (!payType.equals("-1") && !changesType.equals("-1")) {
                 accountIn = userAccountDetailService.getMounthCollect(userId, time, 1, changesType, payType);
//                accountOut = userAccountDetailService.getMounthCollect(userId, time, 2, changesType, payType);
            }
            userAccountCollectVo.setAmount(accountIn);
            userAccountCollectVo.setTime(time);
            List<UserAccountDetailVo> userAccountDetailVoList = new ArrayList<UserAccountDetailVo>();
            List<UserAccountDetail> userAccountDetailList = userAccountDetailService.getList(userId, time, changesType, payType);
            for (UserAccountDetail userAccountDetail : userAccountDetailList) {
                UserAccountDetailVo userAccountDetailVo = new UserAccountDetailVo();
                BeanUtils.copyProperties(userAccountDetail, userAccountDetailVo);
                switch (userAccountDetailVo.getChangesType()) {
                    case "0":
                        userAccountDetailVo.setChangesType("支出");
                        userAccountDetailVo.setFlag("0");
                        break;
                    case "1":
                        userAccountDetailVo.setChangesType("收入");
                        userAccountDetailVo.setFlag("1");
                        break;
                    case "2":
                        userAccountDetailVo.setChangesType("充值");
                        userAccountDetailVo.setFlag("1");
                        break;
                    case "3":
                        userAccountDetailVo.setChangesType("转币");
                        userAccountDetailVo.setFlag("0");
                        break;
                    case "4":
                        userAccountDetailVo.setChangesType("冻结");
                        userAccountDetailVo.setFlag("0");
                        break;
                    case "5":
                        userAccountDetailVo.setChangesType("解冻");
                        userAccountDetailVo.setFlag("1");
                        break;
                    default:
                        userAccountDetailVo.setChangesType("分润");
                        userAccountDetailVo.setFlag("1");
                }
                userAccountDetailVoList.add(userAccountDetailVo);
            }
            userAccountCollectVo.setUserAccountDetailList(userAccountDetailVoList);
            userAccountCollectVoList.add(userAccountCollectVo);
        }
        BaseListResult baseListResult = new BaseListResult();
        baseListResult.setPageNum(pageNum);
        baseListResult.setTotal(page.getTotal());
        baseListResult.setListReult(userAccountCollectVoList);
        return MessageVO.builder(baseListResult)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }
}
