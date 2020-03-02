package com.shopping.service.user;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.SupplierInfo;
import com.shopping.entity.UserInfoPic;
import com.shopping.mapper.SupplierInfoMapper;
import com.shopping.mapper.UserInfoPicMapper;
import org.springframework.stereotype.Service;

@Service("userInfoService")
public class UserInfoService extends ServiceImpl<UserInfoPicMapper, UserInfoPic> {

}