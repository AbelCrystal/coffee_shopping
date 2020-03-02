package com.shopping.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.RelationshipBean;
import com.shopping.entity.User;
import com.shopping.mapper.UserMapper;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

/**
 * @author abel
 * @date 2019-05-30 20:52
 */
@Service("userService")
public class UserService extends ServiceImpl<UserMapper, User> {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private RelationshipService relationshipService;

    public User findByPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        return baseMapper.selectOne(user);
    }

    public User findById(String userId) {
        User user = new User();
        user.setId(userId);
        return baseMapper.selectOne(user);
    }

    public int updateUser(User user) {

        return baseMapper.updateAllColumnById(user);
    }

    /**
     * @description: 查询邀请码是否唯一
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/30 0030 15:55
     */
    public User selectByCode(String testCode) {
        try {
            EntityWrapper<User> userWrapper = new EntityWrapper<>();
            userWrapper.eq("invitation_code", testCode);
            return selectOne(userWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @description: 用户注册
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/30 0030 15:55
     */
    public boolean registUser(User user) throws RuntimeException{
        try {
            boolean flag = insert(user);
            if (flag) {
                if (!StringUtils.isEmpty(user.getInviterId())) {
                    RelationshipBean self = relationshipService.selectByChildInviteId(user.getInvitationCode());    //查询本身推荐关系
                    if (Objects.isNull(self)) {
                        //添加推荐关系
                        RelationshipBean parent = relationshipService.selectByChildInviteId(user.getInviterId()); //查询父级推荐关系
                        RelationshipBean add = new RelationshipBean();
                        add.setChildInviteId(user.getInvitationCode());
                        add.setCreateTime(DateUtils.getDBDate());
                        add.setParentInviteId(user.getInviterId());
                        add.setId(IdWorker.getNewInstance().nextIdToString());
                        if (Objects.isNull(parent)) {
                            //父级不存在推荐关系
                            add.setLevel(1);
                            add.setParentChildTree("-"+user.getInviterId()+"-"+user.getInvitationCode());
                        } else {
                            add.setParentChildTree(parent.getParentChildTree()+"-"+user.getInvitationCode());
                            add.setLevel(parent.getLevel() + 1);
                        }
                        logger.debug("用户："+user.getPhone()+" 添加推荐关系" + user.getInviterId() + "-" + user.getInvitationCode());
                        relationshipService.insert(add);
                    }
                }
            }
            return flag;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public User selectbyUUid(User user) {
        EntityWrapper<User> userWrapper = new EntityWrapper<>();
        if(!StringUtils.isEmpty(user.getWxUUId())){
            userWrapper.eq("wx_uuid",user.getWxUUId());
        }
        if(!StringUtils.isEmpty(user.getAliUUId())){
            userWrapper.eq("ali_uuid",user.getAliUUId());

        }
        return this.selectOne(userWrapper);
    }
}
