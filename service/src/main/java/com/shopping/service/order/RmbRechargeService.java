package com.shopping.service.order;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.CollectionBean;
import com.shopping.entity.OrderDetailBean;
import com.shopping.entity.RmbRecharge;
import com.shopping.mapper.OrderDetailMapper;
import com.shopping.mapper.RechargeOrderMapper;
import com.shopping.service.admin.AdminAccountService;
import com.shopping.service.user.UserAccountService;
import com.shopping.unit.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/4 0004 18:08
 * @Description:    订单详情Service
 */
@Service("rmbRechargeService")
public class RmbRechargeService extends ServiceImpl<RechargeOrderMapper, RmbRecharge> {

    private  Logger logger = LoggerFactory.getLogger(RmbRechargeService.class);
    @Autowired
    private UserAccountService userAccountService;
    @Transactional
    public void  recharge( String orderId) throws  Exception{

        RmbRecharge rmbRecharge=this.selectByStatus(orderId);
        if(rmbRecharge==null){
            throw  new RuntimeException("充值订单:"+orderId+"不存在！");
        }else {
            logger.info("订单充值回调信息"+JSONObject.toJSONString(rmbRecharge));
            rmbRecharge.setStatus("1");//已支付
            rmbRecharge.setUpdateTime(DateUtils.getDBDate());
            this.updateById(rmbRecharge);
            //添加用户资产表
            userAccountService.addUserRechargeAccount(rmbRecharge);
        }
    }
    public   RmbRecharge  selectByStatus(String orderId){
        EntityWrapper<RmbRecharge> collectionWrapper = new EntityWrapper<>();
        collectionWrapper.eq("id",orderId);
        collectionWrapper.eq("status","0");
        return  selectOne(collectionWrapper);

    };


}
