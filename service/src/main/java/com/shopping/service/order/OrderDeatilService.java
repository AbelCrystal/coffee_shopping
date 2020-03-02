package com.shopping.service.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.FeedbackBean;
import com.shopping.entity.OrderDetailBean;
import com.shopping.entity.PictureBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.PictureSourceEnums;
import com.shopping.enums.PictureStatusEnums;
import com.shopping.mapper.FeedbackMapper;
import com.shopping.mapper.OrderDetailMapper;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OSSBootUtil;
import com.shopping.unit.OSSConfig;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/4 0004 18:08
 * @Description:    订单详情Service
 */
@Service("orderDetailService")
public class OrderDeatilService extends ServiceImpl<OrderDetailMapper, OrderDetailBean> {

    Logger logger = LoggerFactory.getLogger(OrderDeatilService.class);

    /**
     * @description: 根据用户id及商品id 查询订单信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/4 0004 18:08
     */
    public OrderDetailBean getOrderByMasterIdDetailId(String masterId, String detailId) {
        try {
            EntityWrapper<OrderDetailBean> orderMasterEntityWrapper = new EntityWrapper<>();
            orderMasterEntityWrapper.eq("master_id", masterId);
            orderMasterEntityWrapper.eq("detail_id", detailId);
            List<OrderDetailBean> orderDetailBeanList = selectList(orderMasterEntityWrapper);
            if (orderDetailBeanList.size() > 0) {
                return orderDetailBeanList.get(0);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }
}
