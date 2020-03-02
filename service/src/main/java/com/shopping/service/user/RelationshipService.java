package com.shopping.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.RelationshipBean;
import com.shopping.entity.VersionBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.RelationshipMapper;
import com.shopping.mapper.VersionMapper;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/30 0030 15:55
 * @Description:    推荐关系Service
 */
@Service("relationshipService")
public class RelationshipService extends ServiceImpl<RelationshipMapper, RelationshipBean> {

    Logger logger = LoggerFactory.getLogger(RelationshipService.class);
    /**
     *
     *
     * @description:根据子级推荐码查询推荐关系
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/30 0030 14:08
     */
    public RelationshipBean selectByChildInviteId(String childInvite) {
        try {
            EntityWrapper<RelationshipBean> relationshipWrapper = new EntityWrapper<>();
            relationshipWrapper.eq("child_invite_id",childInvite);
            return selectOne(relationshipWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

}
