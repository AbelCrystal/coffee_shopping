package com.shopping.api;

import com.shopping.enums.FeedbackStatusEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.redis.RedisUtil;
import com.shopping.vo.MessageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Des: 最新成交价
 * @author: Darryl_Tang
 * @Time: 2019-07-15 13:48
 */

@Api(value = "/api/account", tags = "充提币")
@RestController
@RequestMapping(value = "/api/account")
public class LatestAchievementPriceApi {

    Logger logger = LoggerFactory.getLogger(LatestAchievementPriceApi.class);

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "获取最新成交价", notes = "获取最新成交价", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/findLatestAchievementPrice")
    public MessageVO findLatestAchievementPrice() {
        try {
            double price = Double.parseDouble(redisUtil.get(FeedbackStatusEnums.KEO.getCode()));
            Map<String,Object> map = new HashMap<>();
            map.put("tzb",price);
           return MessageVO.builder(map).msgCode(MessageEnums.SUCCESS).build();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
    }
}
