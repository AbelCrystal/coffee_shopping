package com.shopping.service.user;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.UserAccountDetail;
import com.shopping.enums.AccountTypeEnums;
import com.shopping.mapper.UserAccountDetailMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @date 2019-05-30 20:52
 */
@Service("userAccountDetailService")
public class UserAccountDetailService extends ServiceImpl<UserAccountDetailMapper, UserAccountDetail> {

    public List<Map<String, Object>> getMounth(String userId, String changesType, String payType) {
        String sql = "DATE_FORMAT(create_time,'%Y-%m') time  ";
        EntityWrapper<UserAccountDetail> qryWrapper = new EntityWrapper<>();
        qryWrapper.setSqlSelect(sql);
        qryWrapper.addFilter("user_id=" + userId);
        if (!StringUtils.isEmpty(changesType) && !changesType.equals("-1")) {
            qryWrapper.addFilter("changes_type=" + changesType);
        }
        if (!StringUtils.isEmpty(payType) && !payType.equals("-1")) {
            qryWrapper.addFilter("pay_type=" + payType);
        }
        qryWrapper.groupBy("time");
        qryWrapper.orderBy("time", false);
        return baseMapper.selectMaps(qryWrapper);
    }

    public double getMounthCollect(String userId, String time, int type, String changesType, String payType) {
        String sql = " sum(amount) amount ";
        EntityWrapper<UserAccountDetail> qryWrapper = new EntityWrapper<>();
        qryWrapper.setSqlSelect(sql);
        qryWrapper.addFilter("user_id='" + userId + "'");
        if (!StringUtils.isEmpty(changesType) && !changesType.equals("-1")) {
            qryWrapper.addFilter("changes_type=" + changesType);
        }
        if (!StringUtils.isEmpty(payType) && !changesType.equals("-1")) {
            qryWrapper.addFilter("pay_type=" + payType);
        }
//        if(type==1){
//            qryWrapper.addFilter("changes_type in(1,2,5)");
//        }else{
//            qryWrapper.addFilter("changes_type in(0,3,4)");
//        }
        qryWrapper.addFilter("DATE_FORMAT(create_time,'%Y-%m')='" + time + "'");
        List<Map<String, Object>> list = baseMapper.selectMaps(qryWrapper);
        double amount = 0.0;
        if (list != null && list.size() > 0 && list.get(0) != null && list.get(0).get("amount") != null) {
            amount = Double.parseDouble(list.get(0).get("amount").toString());
        }
        return amount;
    }

    public List<UserAccountDetail> getList(String userId, String time, String changesType, String payType) {
        EntityWrapper<UserAccountDetail> qryWrapper = new EntityWrapper<>();
        qryWrapper.addFilter("user_id='" + userId + "'");
        if (!StringUtils.isEmpty(changesType) && !changesType.equals("-1")) {
            qryWrapper.addFilter("changes_type=" + changesType);
        }
        if (!StringUtils.isEmpty(payType) && !changesType.equals("-1")) {
            qryWrapper.addFilter("pay_type=" + payType);
        }
        qryWrapper.addFilter("DATE_FORMAT(create_time,'%Y-%m')='" + time + "'");
        qryWrapper.orderBy("create_time", false);
        return baseMapper.selectList(qryWrapper);
    }

    public List<UserAccountDetail> selectListByType(String type, String userId) {
        EntityWrapper<UserAccountDetail> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("user_id", userId);
        qryWrapper.eq("account_type", type);
        qryWrapper.orderBy("create_time", false);
        List<UserAccountDetail> listDetail = this.selectList(qryWrapper);
        return listDetail;
    }

    public List<UserAccountDetail> selectAccountByType(String type, String userId, String currencyId) {
        EntityWrapper<UserAccountDetail> qryWrapper = new EntityWrapper<>();
        if(AccountTypeEnums.TONGZHENG.getCode().equals(type)&&!StringUtils.isEmpty(currencyId)){
            qryWrapper.eq("currency_id", currencyId);
        }
        qryWrapper.eq("user_id", userId);
        qryWrapper.eq("account_type", type);
        qryWrapper.orderBy("create_time", false);
        List<UserAccountDetail> listDetail = this.selectList(qryWrapper);
        return listDetail;
    }
}
