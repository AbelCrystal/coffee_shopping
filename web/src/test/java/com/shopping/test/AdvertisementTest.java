//package com.shopping.test;
//
//import com.shopping.entity.Advertisement;
//import com.shopping.enums.AdvertEmums;
//import com.shopping.service.index.AdvertisementService;
//import com.shopping.service.index.NoticeService;
//import com.shopping.unit.DateUtils;
//import com.shopping.unit.IdWorker;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//
//@SpringBootTest
//
//@WebAppConfiguration
//public class AdvertisementTest {
//    @Autowired
//    AdvertisementService advertisementService;
//
//    @Before
//    public void init() {
//
//        System.out.println("开始测试-----------------");
//
//    }
//
//    @Test
//    public void test() {
//        List<Advertisement> arryList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Advertisement advertisement = new Advertisement();
//            advertisement.setId(IdWorker.getNewInstance().nextIdToString());
//            advertisement.setIsUpper(AdvertEmums.ADVERT_UPPER.getCode());
//            advertisement.setPicUrl(" https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            advertisement.setRemark("APP首页"+i);
//            advertisement.setJumpUrl("http://www.baidu.com");
//            advertisement.setCreateTime(DateUtils.getDBDate());
//            arryList.add(advertisement);
//
//        }
//
//        advertisementService.insertBatch(arryList);
//    }
//
//    @After
//    public void after() {
//
//        System.out.println("测试结束-----------------");
//    }
//
//}
