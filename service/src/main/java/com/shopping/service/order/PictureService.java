package com.shopping.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.PictureBean;
import com.shopping.enums.PictureStatusEnums;
import com.shopping.mapper.PictureMapper;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OSSBootUtil;
import com.shopping.unit.OSSConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("pictureService")
public class PictureService extends ServiceImpl<PictureMapper, PictureBean> {

    @Autowired
    private OSSConfig ossConfig;

    public void uploadPicture(String refundsId, String type, String msg, List<String> files) {
        List<PictureBean> listPic = new ArrayList<>();
        if (files!=null&&!files.isEmpty()) {
            for (String picUrl :
                    files) {
//                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
//                String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 18) + suffix;
//                String ossFileUrlBoot = OSSBootUtil.upload(ossConfig, file, "images/orderRefund/" + DateUtils.getFormatDateTime(new Date(), DateUtils.YYYMMDD_NOT) + "/", fileName);
                PictureBean pictureBean = new PictureBean();
                pictureBean.setPicId(IdWorker.getNewInstance().nextIdToString());
                pictureBean.setSourceId(refundsId);
                pictureBean.setPicUrl(picUrl);
                pictureBean.setPicDesc(msg);
                pictureBean.setPicStatus(PictureStatusEnums.PIC_VALID.getCode());//有效
                pictureBean.setPicType(type);    //退货
                pictureBean.setCreateTime(DateUtils.getDBDate());
                this.baseMapper.insert(pictureBean);
            }
        }
    }
}
