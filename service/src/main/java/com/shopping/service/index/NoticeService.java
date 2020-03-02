package com.shopping.service.index;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.MessageTemplate;
import com.shopping.entity.Notice;
import com.shopping.mapper.MessageTemplateMapper;
import com.shopping.mapper.NoticeMapper;
import com.shopping.unit.DateUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("noticeService")
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> {

    public List<Notice> selectListByNowTime() {
        EntityWrapper<Notice> qryWrapper = new EntityWrapper<>();
        qryWrapper.le("startTime", DateUtils.getDBDate());
        qryWrapper.ge("endTime",DateUtils.getDBDate());
        return baseMapper.selectList(qryWrapper);
    }
}
