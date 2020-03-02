package com.shopping.service.mq;

import lombok.Data;

import java.util.List;
@Data
public class OrderCardReturnMessage {
    private  String messageCode;
    private List<String> orderId;

}
