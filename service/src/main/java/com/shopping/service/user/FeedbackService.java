package com.shopping.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.FeedbackBean;
import com.shopping.entity.PictureBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.PictureSourceEnums;
import com.shopping.enums.PictureStatusEnums;
import com.shopping.mapper.FeedbackMapper;
import com.shopping.service.order.PictureService;
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
 * @Date: 2019/6/28 0028 14:08
 * @Description:    意见反馈Service
 */
@Service("feedbackService")
public class FeedbackService extends ServiceImpl<FeedbackMapper, FeedbackBean> {

    @Autowired
    private OSSConfig ossConfig;

    @Autowired
    private PictureService pictureService;

    Logger logger = LoggerFactory.getLogger(FeedbackService.class);
    /**
     *
     *
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/28 0028 14:08
     */
    public List<FeedbackBean> selectListById(String feedbackId,String userId) {
        try {
            EntityWrapper<FeedbackBean> feedbackWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(feedbackId)) {
                feedbackWrapper.eq("feedback_id",feedbackId);
            }
            feedbackWrapper.eq("feedback_user_id",userId);
            feedbackWrapper.ne("is_delete",  GlobalIsDeleteEnums.IS_DISABLE.getCode());
            feedbackWrapper.orderBy("update_time",false);
            return selectList(feedbackWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     *
     *
     * @description: 提交意见反馈，保存意见反馈图片信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/28 0028 14:58
     */
    @Transactional
    public boolean addFeeback(FeedbackBean feedbackBean, MultipartFile[] files) throws RuntimeException{
        boolean flag =  insert(feedbackBean);   //存意见
        if (flag && files!=null) {  //存图片
            try {
                List<PictureBean> listPic = new ArrayList<>();
                for (MultipartFile file :
                        files) {
                    String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                    String fileName = System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 18) + suffix;
                    String ossFileUrlBoot = OSSBootUtil.upload(ossConfig, file, "images/feedback/" + DateUtils.getFormatDateTime(new Date(), DateUtils.YYYMMDD_NOT) + "/", fileName);
                    PictureBean pictureBean = new PictureBean();
                    pictureBean.setPicId(IdWorker.getNewInstance().nextIdToString());
                    pictureBean.setSourceId(feedbackBean.getFeedbackId());
                    pictureBean.setPicUrl(ossFileUrlBoot);
                    pictureBean.setPicDesc("意见反馈");
                    pictureBean.setPicStatus(PictureStatusEnums.PIC_VALID.getCode());//有效
                    pictureBean.setPicType(PictureSourceEnums.FEEDBACK.getCode());    //意见反馈
                    pictureBean.setCreateTime(DateUtils.getDBDate());
                    listPic.add(pictureBean);
                }
                if (listPic.size() > 0) {
                    this.pictureService.insertBatch(listPic);   //增加图片
                }
            }catch (Exception ex) {
                logger.error(ex.getMessage());
                throw new RuntimeException(ex.getMessage());
            }
        }
        return flag;
    }
}
