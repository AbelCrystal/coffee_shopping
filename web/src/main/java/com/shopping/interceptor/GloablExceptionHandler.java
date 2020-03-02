package com.shopping.interceptor;

import com.shopping.enums.MessageEnums;
import com.shopping.vo.MessageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @date 2019-05-30 22:37
 */
@ControllerAdvice
public class GloablExceptionHandler {
    private Logger log = LoggerFactory.getLogger(GloablExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public MessageVO handleException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            Map<Object, Object> map = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                map.put(error.getField(), error.getDefaultMessage());
            }
            return MessageVO.builder().error(map.toString()).build();
        } else {
            String msg = e.getMessage();
            if (msg != null && msg.equals("PLEASELOGIN")) {
                return MessageVO.builder().msgCode(MessageEnums.NOT_LOGIN)
                        .build();
            } else if (msg != null && msg.equals("USERFROZEN")) {
                return MessageVO.builder().msgCode(MessageEnums.USERFROZEN)
                        .build();
            } else {
                e.printStackTrace();
                return MessageVO.builder().msgCode(MessageEnums.SERVICE_ERROR)
                        .build();
            }
        }
    }
}
