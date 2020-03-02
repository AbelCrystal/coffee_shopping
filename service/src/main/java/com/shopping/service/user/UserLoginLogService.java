package com.shopping.service.user;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.User;
import com.shopping.entity.UserLoginLog;
import com.shopping.mapper.UserLoginLogMapper;
import com.shopping.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author abel
 * @date 2019-05-30 20:52
 */
@Service("userLoginLogService")
public class UserLoginLogService extends ServiceImpl<UserLoginLogMapper, UserLoginLog> {



}
