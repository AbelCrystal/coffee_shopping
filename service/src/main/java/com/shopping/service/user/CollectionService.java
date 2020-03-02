package com.shopping.service.user;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.CollectionBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.CollectionMapper;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/6/25 0025 20:53
 * @Description:    购物车Service
 */
@Service("collectionService")
public class CollectionService extends ServiceImpl<CollectionMapper, CollectionBean> {

    Logger logger = LoggerFactory.getLogger(CollectionService.class);
    /**
     *
     *
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 20:53
     */
    public List<CollectionBean> selectListById(String collectionId,String userId) {
        try {
            EntityWrapper<CollectionBean> collectionWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(collectionId)) {
                collectionWrapper.eq("collection_id",collectionId);
            }
            collectionWrapper.eq("user_id",userId);
            collectionWrapper.ne("is_delete",  GlobalIsDeleteEnums.IS_DISABLE.getCode());
            collectionWrapper.orderBy("update_time",false);
            return selectList(collectionWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
