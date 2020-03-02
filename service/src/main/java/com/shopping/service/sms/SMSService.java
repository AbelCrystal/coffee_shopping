package com.shopping.service.sms;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.SmsRecord;
import com.shopping.mapper.SmsRecordMapper;
import org.springframework.stereotype.Service;

@Service("smsService")
public class SMSService extends ServiceImpl<SmsRecordMapper,SmsRecord> {

}
