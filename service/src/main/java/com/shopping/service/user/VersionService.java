package com.shopping.service.user;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.VersionBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.mapper.VersionMapper;
import org.reflections.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/6/28 0028 15:55
 * @Description:    版本更新公告Service
 */
@Service("versionService")
public class VersionService extends ServiceImpl<VersionMapper, VersionBean> {

    Logger logger = LoggerFactory.getLogger(VersionService.class);
    /**
     *
     *
     * @description:查询详情
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/28 0028 14:08
     */
    public List<VersionBean> selectListById(String versionId) {
        try {
            EntityWrapper<VersionBean> versionWrapper = new EntityWrapper<>();
            if (!Utils.isEmpty(versionId)) {
                versionWrapper.eq("version_id",versionId);
            }
            versionWrapper.ne("is_delete",  GlobalIsDeleteEnums.IS_DISABLE.getCode());
            versionWrapper.orderBy("update_time",false);
            return selectList(versionWrapper);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

}
