//package com.shopping.test;
//
//import com.shopping.entity.Advertisement;
//import com.shopping.entity.SupplierInfo;
//import com.shopping.entity.UserInfoPic;
//import com.shopping.enums.AdvertEmums;
//import com.shopping.enums.SupplierEmums;
//import com.shopping.enums.UserInfoPicEmums;
//import com.shopping.service.index.AdvertisementService;
//import com.shopping.service.index.SupplierInfoService;
//import com.shopping.service.user.UserInfoPicService;
//import com.shopping.service.user.UserInfoService;
//import com.shopping.unit.DateUtils;
//import com.shopping.unit.IdWorker;
//import com.shopping.vo.user.UserInfo;
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
//public class SupplierInfoTest {
//    @Autowired
//    SupplierInfoService supplierInfoService;
//    @Autowired
//    UserInfoPicService userInfoPicService;
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
//        List<SupplierInfo> arryList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            UserInfoPic userInfoPic = new UserInfoPic();
//            String strLogId = IdWorker.getNewInstance().nextIdToString();
//            userInfoPic.setId(strLogId);
//            userInfoPic.setPicPath("d://user/" + "shenfen" + i + "jpg");
//            userInfoPic.setPicUrl("https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            userInfoPic.setStatus(UserInfoPicEmums.PIC_EFFECTIVE.getCode());
//            userInfoPic.setRemark("beizhu"+strLogId);
//            userInfoPic.setCreateTime(DateUtils.getDBDate());
//            userInfoPicService.insert(userInfoPic);
//            SupplierInfo supplierInfo = new SupplierInfo();
//            supplierInfo.setId(IdWorker.getNewInstance().nextIdToString());
//            supplierInfo.setApplicationTime(DateUtils.getDBDate());
//            supplierInfo.setAreaAddress("广东深圳");
//            supplierInfo.setAreaExplain("红酒专卖");
//            supplierInfo.setAreaLogId(strLogId);
//            supplierInfo.setAreaName("红酒专卖" + i);
//            supplierInfo.setAreaType("0");
//            supplierInfo.setAuditState(SupplierEmums.AUDIT_SUCCESS.getCode());
//            supplierInfo.setAuditTime(DateUtils.getDBDate());
//            UserInfoPic businessLicensePicIdPic = new UserInfoPic();
//            String strBusinessLicensePicId = IdWorker.getNewInstance().nextIdToString();
//            businessLicensePicIdPic.setId(strBusinessLicensePicId);
//            businessLicensePicIdPic.setPicPath("d://user/" + "shenfen" + i + "jpg");
//            businessLicensePicIdPic.setPicUrl("https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            businessLicensePicIdPic.setStatus(UserInfoPicEmums.PIC_EFFECTIVE.getCode());
//            businessLicensePicIdPic.setRemark("beizhu"+strBusinessLicensePicId);
//            businessLicensePicIdPic.setCreateTime(DateUtils.getDBDate());
//            userInfoPicService.insert(businessLicensePicIdPic);
//            supplierInfo.setBusinessLicensePicId(strBusinessLicensePicId);
//            supplierInfo.setBusinessLnteType("00001");
//            UserInfoPic CardBackPicId = new UserInfoPic();
//            String strCardBackPicId = IdWorker.getNewInstance().nextIdToString();
//            CardBackPicId.setId(strCardBackPicId);
//            CardBackPicId.setPicPath("d://user/" + "shenfen" + i + "jpg");
//            CardBackPicId.setPicUrl("https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            CardBackPicId.setStatus(UserInfoPicEmums.PIC_EFFECTIVE.getCode());
//            CardBackPicId.setRemark("beizhu"+strCardBackPicId);
//            CardBackPicId.setCreateTime(DateUtils.getDBDate());
//            userInfoPicService.insert(CardBackPicId);
//            supplierInfo.setCardBackPicId(strCardBackPicId);
//            UserInfoPic CardFrontPicId = new UserInfoPic();
//            String strCardFrontPicId = IdWorker.getNewInstance().nextIdToString();
//            CardFrontPicId.setId(strCardFrontPicId);
//            CardFrontPicId.setPicPath("d://user/" + "shenfen" + i + "jpg");
//            CardFrontPicId.setPicUrl("https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            CardFrontPicId.setStatus(UserInfoPicEmums.PIC_EFFECTIVE.getCode());
//            CardFrontPicId.setRemark("beizhu"+strCardBackPicId);
//            CardFrontPicId.setCreateTime(DateUtils.getDBDate());
//            userInfoPicService.insert(CardFrontPicId);
//            supplierInfo.setCardFrontPicId(strCardFrontPicId);
//            supplierInfo.setCorporateName("深圳慧怡科技有限公司" + i);
//            supplierInfo.setCreditCode("1212121212"+i);
//            UserInfoPic CurrencyPicId = new UserInfoPic();
//            String strCurrencyPicId = IdWorker.getNewInstance().nextIdToString();
//            CurrencyPicId.setId(strCurrencyPicId);
//            CurrencyPicId.setPicPath("d://user/" + "shenfen" + i + "jpg");
//            CurrencyPicId.setPicUrl("https://gd2.alicdn.com/imgextra/i2/3237192654/O1CN01TNmFa01VTZgp9o2Yj_!!3237192654.jpg_400x400.jpg");
//            CurrencyPicId.setStatus(UserInfoPicEmums.PIC_EFFECTIVE.getCode());
//            CurrencyPicId.setRemark("beizhu"+strCurrencyPicId);
//            CurrencyPicId.setCreateTime(DateUtils.getDBDate());
//            userInfoPicService.insert(CurrencyPicId);
//            supplierInfo.setCurrencyPicId(strCurrencyPicId);
//            supplierInfo.setJuridicalCardno("111111");
//            supplierInfo.setJuridicalName("232323");
//            supplierInfo.setOperatorName("abel");
//            supplierInfo.setOperatorPhone("13510941260");
//            arryList.add(supplierInfo);
//
//        }
//
//        supplierInfoService.insertBatch(arryList);
//    }
//
//    @After
//    public void after() {
//
//        System.out.println("测试结束-----------------");
//    }
//}
