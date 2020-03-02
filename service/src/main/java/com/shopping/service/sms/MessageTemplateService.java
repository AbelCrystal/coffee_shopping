package com.shopping.service.sms;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.MessageTemplate;
import com.shopping.entity.SmsRecord;
import com.shopping.entity.User;
import com.shopping.mapper.MessageTemplateMapper;
import com.shopping.mapper.SmsRecordMapper;
import org.springframework.stereotype.Service;

@Service("messageTemplateService")
public class MessageTemplateService extends ServiceImpl<MessageTemplateMapper, MessageTemplate> {
    public MessageTemplate findBySmsType(String smsType){
        MessageTemplate template = new MessageTemplate();
        template.setSmsType(smsType);
        return baseMapper.selectOne(template);
    }
}
