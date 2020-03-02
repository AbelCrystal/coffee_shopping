package com.shopping.vo;
import com.alibaba.fastjson.JSONObject;
import com.shopping.enums.MessageEnums;
import lombok.Data;
import java.io.Serializable;
/**
 * @description: 构建网络传输的类
 * @author: Abel
 * @create: 2019-04-09
 */
@Data
public class MessageVO<T> implements Serializable{



    private static final long serialVersionUID = 7366217171681294440L;
    private String code;
    private String msg;
    private T data;

    private MessageVO(Builder builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = (T)builder.data;
    }


    /**
     * 由于redis序列换是JDK序列，所以要无参构造函数给JSON调用，解决不能序列化问题
     */
    public MessageVO() {
    }

    /**
     * 使用build模式，较少重复代码
     * @return
     */

    public static Builder builder(){
        return new Builder();
    }

    public static <T> Builder builder(T data){
        Builder<T> builder = new Builder<>();
        builder.data(data);
        return builder;
    }



    public static class Builder<T> {
        private String code;
        private String msg;
        private T data;

        public Builder msgCode(MessageEnums messageEnums) {
            this.msg = messageEnums.getDesc();
            this.code = messageEnums.getCode();
            return this;
        }
        public Builder error(String msg) {
            this.msg = msg;
            this.code = "403";
            return this;
        }
        public Builder errorMessage(String msg,String code) {
            this.msg = msg;
            this.code = code;
            return this;
        }

        public MessageVO build() {
            return new MessageVO(this);
        }

        /**
         * 不在对外提供，解决泛型在builder模式底下有warning
         * @param data
         * @return
         */
        private Builder<T> data(T data) {
            this.data = data;
            return this;
        }

    }

    public static void main(String[] args) {

        System.err.println(JSONObject.toJSONString(MessageVO.builder("123213")
                .msgCode(MessageEnums.API_ERROR).build())
                );
    }

}