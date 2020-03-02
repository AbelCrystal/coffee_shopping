package com.shopping.interceptor;

import com.shopping.annotation.PassToken;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.User;
import com.shopping.enums.UserRedisKeyPrefixEnum;
import com.shopping.redis.RedisUtil;
import com.shopping.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;


/**
 * @author abel
 * @date 2019-05-30 20:41
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                HttpSession session = httpServletRequest.getSession();
                if (session.getAttribute(session.getId()) != null) {
                    User user = (User) session.getAttribute(session.getId());
                    //判断用户是否冻结，如果冻结限制登陆
                    if (user != null) {
                        if (redisUtil.kayExist(UserRedisKeyPrefixEnum.IS_USERFROZENKEY.getCode() + ":" + user.getId())) {
                            session.removeAttribute(session.getId());
                            throw new RuntimeException("USERFROZEN");
                        }
                    }
                    return true;
                }
                throw new RuntimeException("PLEASELOGIN");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
