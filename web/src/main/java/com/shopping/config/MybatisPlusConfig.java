package com.shopping.config;

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 *
 *
 * @description: MybatisPlus 乐观锁
 * @param:
 * @return:
 * @auther: Darryl_Tang
 * @time: 2019/7/9 0009 17:37
 */
@Configuration
public class MybatisPlusConfig {
    /**
     * 乐观锁 插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLoker() {
        return new OptimisticLockerInterceptor();
    }

}