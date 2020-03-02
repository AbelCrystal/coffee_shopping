package com.shopping.vo;

import com.shopping.enums.MessageEnums;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *@description:
 *@author:Darryl_Tang
 *@Time:2019/6/21 0021
 *
 */
@Data
public class ResultVo implements Serializable {

    private String message;

    private String code;

    private Object data;

    private int total;

    public static ResultVo success() {
        return new ResultVo(MessageEnums.SUCCESS.getCode(), null, null, 0);
    }

    public static ResultVo success(String code,String msg) {
        return new ResultVo(code,msg,null,0);
    }
    public static ResultVo success(Object data) {
        return new ResultVo(MessageEnums.SUCCESS.getCode(), null, data, 0);
    }

    public static ResultVo success(Object data, int total) {
        return new ResultVo(MessageEnums.SUCCESS.getCode(), null, data, total);
    }

    public static ResultVo error(String code, String msg) {
        return new ResultVo(code, msg, null, 0);
    }

    public static ResultVo error(String code, String msg, Object data) {
        return new ResultVo(code, msg, data, 0);
    }

    public static ResultVo error(String msg) {
        return new ResultVo(MessageEnums.FAIL.getCode(), msg, null, 0);
    }

    ResultVo() {}

    private ResultVo(String code, String message, Object data, int total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }
}
