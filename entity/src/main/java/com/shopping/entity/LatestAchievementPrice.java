package com.shopping.entity;


import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/15 0015 14:02
 * @Description: 时价实体
 */
@Data
public class LatestAchievementPrice {

    private Integer coinId;

    private String coinName;

    private BigDecimal buy;

    private BigDecimal sell;
}
