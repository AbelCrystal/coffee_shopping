//package com.shopping.test;//package com.shopping.test;
//
//import com.shopping.entity.Advertisement;
//import com.shopping.entity.ProductInfo;
//import com.shopping.entity.ProductPicInfo;
//import com.shopping.mapper.ProductInfoMapper;
//import com.shopping.service.product.ProductInfoService;
//import com.shopping.service.product.ProductPicInfoService;
//import com.shopping.unit.DateUtils;
//import com.shopping.unit.IdWorker;
//import com.shopping.unit.OrderNum;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//
//@SpringBootTest
//
//@WebAppConfiguration
//public class ProductTest {
//    @Autowired
//    private  ProductInfoService productInfoService;
//    @Autowired
//    ProductPicInfoService productPicInfoService;
//    @Before
//    public void init() {
//
//        System.out.println("开始测试-----------------");
//
//    }
//
//    @Test
//    public void test() {
//        List<ProductInfo> arryList = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            //https://img.alicdn.com/bao/uploaded/i1/890482188/O1CN01YaI0Dq1S297scnUnZ_!!0-item_pic.jpg_180x180.jpg
//            ProductInfo productPicInfo=new ProductInfo();
//            productPicInfo.setProductId(IdWorker.getNewInstance().nextIdToString());
//            productPicInfo.setProductCore(OrderNum.nextNum(28));
//            productPicInfo.setProductName("格子衬衫"+i);
//            productPicInfo.setBarCode(IdWorker.getNewInstance().nextIdToString());
//
//            productPicInfo.setBrandId(IdWorker.getNewInstance().nextIdToString());
//
//            productPicInfo.setOneCategoryId(IdWorker.getNewInstance().nextIdToString());
//
//            productPicInfo.setTwoCategoryId(IdWorker.getNewInstance().nextIdToString());
//
//            productPicInfo.setThreeCategoryId(IdWorker.getNewInstance().nextIdToString());
//
//            productPicInfo.setSupplierId("291206555662024704");
//            productPicInfo.setPrice(new BigDecimal(33));
//
//            productPicInfo.setAverageCost(new BigDecimal(32));
//
//            productPicInfo.setPublishStatus("1");
//
//            productPicInfo.setAuditStatus("1");
//
//            productPicInfo.setWeight("152cm");
//
//            productPicInfo.setPlength("130cm");
//
//            productPicInfo.setMasterPicUrl("https://img.alicdn.com/bao/uploaded/i1/890482188/O1CN01YaI0Dq1S297scnUnZ_!!0-item_pic.jpg_180x180.jpg");
//
//            productPicInfo.setShelfType("月");
//
//            productPicInfo.setPlatformType("1");
//
//            productPicInfo.setInventory((long) 1000);
//
//            productPicInfo.setSalesVolume((long) 1);
//
//            productPicInfo.setDrawingRate(new BigDecimal(0.03));
//
//            productPicInfo.setShelfLife(12);
//
//            productPicInfo.setAdvertisingSlogans("德芙纵享新丝滑！");
//
//            productPicInfo.setProductionDate(DateUtils.getDBDate());
//
//            productPicInfo.setCreateTime(DateUtils.getDBDate());
//
//            productPicInfo.setDescript("此格子衫是程序员首选款式！");
//
//
//            arryList.add(productPicInfo);
//
//        }
//
//        productInfoService.insertBatch(arryList);
//    }
//
//    @After
//    public void after() {
//
//        System.out.println("测试结束-----------------");
//    }
//
//}
