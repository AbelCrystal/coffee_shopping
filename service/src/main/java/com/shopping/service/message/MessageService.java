package com.shopping.service.message;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.MessageBean;
import com.shopping.enums.MessageStatusEnums;
import com.shopping.mapper.MessageMapper;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageService extends ServiceImpl<MessageMapper, MessageBean> {
    /**
     *
     *
     * @description: 根据条件查询消息列表
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 21:55
     */
    public List<MessageBean> getList(String userId){
        EntityWrapper<MessageBean> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("user_id",userId);
        qryWrapper.orderBy("status",false);
        return selectList(qryWrapper);
    }

    /**
     *
     *
     * @description: 获取消息详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 21:55
     */
    public MessageBean getMessageDetail(String messageId){
        MessageBean messageBean =selectById(messageId);
        messageBean.setStatus(MessageStatusEnums.READ.getCode());
        messageBean.setUpdateTime(DateUtils.getDBDate());
        updateById(messageBean);
        return messageBean;
    }

    /**
     *
     *
     * @description: 保存发送消息记录
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/8 0008 22:01
     */
    public void saveSendMessage(String uid,String title,String content,String type){
        MessageBean messageBean = new MessageBean();
        messageBean.setId(IdWorker.getNewInstance().nextIdToString());
        messageBean.setTitle(title);
        messageBean.setUserId(uid);
        messageBean.setContent(content);
        messageBean.setType(type);
        messageBean.setStatus(MessageStatusEnums.NO_READ.getCode());
        messageBean.setCreateTime(DateUtils.getDBDate());
        insert(messageBean);
    }
}
