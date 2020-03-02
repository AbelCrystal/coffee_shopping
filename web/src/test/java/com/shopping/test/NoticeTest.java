//package com.shopping.test;
//
//import com.shopping.entity.Notice;
//import com.shopping.enums.NoticeEmums;
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
//import java.util.*;
//
//@RunWith(SpringRunner.class)
//
//@SpringBootTest
//
//@WebAppConfiguration
//public class NoticeTest {
//    @Autowired
//    NoticeService noticeService;
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
//        List<Notice> arryList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Notice notice1 = new Notice();
//            notice1.setId(IdWorker.getNewInstance().nextIdToString());
//            notice1.setNoticeType(NoticeEmums.INDEX_NOTICE.getCode());
//            notice1.setContent("您好商城正式上线" + i);
//            notice1.setTitle("商城上线" + i);
//            notice1.setStartTime(DateUtils.getDBDate());
//            notice1.setEndTime(DateUtils.addDays(DateUtils.getDBDate(), 10));
//            arryList.add(notice1);
//        }
//
//        noticeService.insertBatch(arryList);
//    }
//
//    @After
//    public void after() {
//
//        System.out.println("测试结束-----------------");
//    }
//
//}
