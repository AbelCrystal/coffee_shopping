package com.shopping.service.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.SystemArgsBean;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.GlobalIsOpenEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.mapper.SystemargsMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Darryl_Tang
 * @Date: 2019/7/2
 * @Description:   系统参数Service
 */
@Service("systemargsService")
public class SystemargsService extends ServiceImpl<SystemargsMapper, SystemArgsBean> {

    /**
     *
     *
     * @description: 查询所有开启参数
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/2 0002 15:55
     */
    public List<SystemArgsBean> findAll() {
        EntityWrapper<SystemArgsBean> systemargsBeanEntityWrapper = new EntityWrapper<>();
        systemargsBeanEntityWrapper.eq("is_open", GlobalIsOpenEnums.IS_OPEN.getCode());
        systemargsBeanEntityWrapper.eq("is_delete", GlobalIsDeleteEnums.IS_NORMAL.getCode());
        systemargsBeanEntityWrapper.orderBy("update_time",false);
        return baseMapper.selectList(systemargsBeanEntityWrapper);
    }

    /**
     *
     *
     * @description: 封装
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/7/2 0002 15:58
     */
    public Map<String, Object> findAllMap(){
        Map<String, Object> map = new HashMap<>() ;
        List<SystemArgsBean> list = findAll() ;
        List<Object> bannerArr = new ArrayList<>();
        List<Object> enBannerArr = new ArrayList<>();
        List<Object> kBannerArr = new ArrayList<>();
        List<Object> kEnBannerArr = new ArrayList<>();
        HashMap<String, String> tempMap = null;
        for (SystemArgsBean systemargs : list) {
            String key = systemargs.getKey();
            String value = systemargs.getValue();

            if(key.contains(MessageEnums.BIGIMAGE.getCode())
                    || key.contains(MessageEnums.ENBIGIMAGE.getCode())
                    || key.contains(MessageEnums.KBIGIMAGE.getCode())
                    || key.contains(MessageEnums.KENBIGIMAGE.getCode())){
                if(tempMap == null){
                    tempMap = new HashMap();
                }else{
                    tempMap = (HashMap)tempMap.clone();
                }
                tempMap.put("value", value);
                tempMap.put("url", systemargs.getUrl() == null ? "#": systemargs.getUrl());
                if(StringUtils.hasText(value)) {
                    if(key.contains(MessageEnums.BIGIMAGE.getCode())) bannerArr.add(tempMap);
                    if(key.contains(MessageEnums.ENBIGIMAGE.getCode())) enBannerArr.add(tempMap);
                    if(key.contains(MessageEnums.KBIGIMAGE.getCode())) kBannerArr.add(tempMap);
                    if(key.contains(MessageEnums.KENBIGIMAGE.getCode())) kEnBannerArr.add(tempMap);
                }
                continue;
            }
            map.put(key, value) ;
        }
        map.put(MessageEnums.BIGIMAGE.getCode(), bannerArr) ;
        map.put(MessageEnums.ENBIGIMAGE.getCode(), enBannerArr) ;
        map.put(MessageEnums.KBIGIMAGE.getCode(), kBannerArr) ;
        map.put(MessageEnums.KENBIGIMAGE.getCode(), kEnBannerArr) ;
        return map ;
    }

}
