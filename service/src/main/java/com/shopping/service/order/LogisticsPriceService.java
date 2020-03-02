package com.shopping.service.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.FeedbackBean;
import com.shopping.entity.LogisticsPriceBean;
import com.shopping.entity.PictureBean;
import com.shopping.enums.GlobalIsDefaultEnums;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.PictureSourceEnums;
import com.shopping.enums.PictureStatusEnums;
import com.shopping.mapper.FeedbackMapper;
import com.shopping.mapper.LogisticsPriceMapper;
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

import java.util.*;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/4  20:54
 * @Description:    商家物流Service
 */
@Service("logisticsPriceService")
public class LogisticsPriceService extends ServiceImpl<LogisticsPriceMapper, LogisticsPriceBean> {

    Logger logger = LoggerFactory.getLogger(LogisticsPriceService.class);

    /**
     *
     *
     * @description:查询商家默认物流价格
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/4  20:54
     */
    public LogisticsPriceBean selectBySupplierId(String supplierId) {
        try {
            EntityWrapper<LogisticsPriceBean> collectionWrapper = new EntityWrapper<>();
            collectionWrapper.eq("supplier_id",supplierId);
            collectionWrapper.eq("is_default", GlobalIsDefaultEnums.YES.getCode());
            List<LogisticsPriceBean> logisticsPriceBeans = baseMapper.selectList(collectionWrapper);
            if (logisticsPriceBeans.size() == 1) {  //一个默认 取默认
                return logisticsPriceBeans.get(0);
            }
            if (logisticsPriceBeans.size() > 1) {   //多个默认 则随机
                Random random = new Random();
                int n = random.nextInt(logisticsPriceBeans.size());
                return logisticsPriceBeans.get(n);
            }
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
