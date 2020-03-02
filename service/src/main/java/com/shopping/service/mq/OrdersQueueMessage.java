package com.shopping.service.mq;

import com.shopping.entity.*;

import java.util.HashSet;
import java.util.Set;

public class OrdersQueueMessage {
	private User user;      //下单人
    private Set<OrderDetailBean> orderDetailBeanSet;       //详情订单集合
    private OrderMaster orderMaster;        //主订单
    private Set<OrderCartBean> orderCartBeanSet;    //购物车集合

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderDetailBean> getOrderDetailBeanSet() {
        return orderDetailBeanSet;
    }

    public void setOrderDetailBeanSet(Set<OrderDetailBean> orderDetailBeanSet) {
        this.orderDetailBeanSet = orderDetailBeanSet;
    }

    public OrderMaster getOrderMaster() {
        return orderMaster;
    }

    public void setOrderMaster(OrderMaster orderMaster) {
        this.orderMaster = orderMaster;
    }

    public Set<OrderCartBean> getOrderCartBeanSet() {
        return orderCartBeanSet;
    }

    public void setOrderCartBeanSet(Set<OrderCartBean> orderCartBeanSet) {
        this.orderCartBeanSet = orderCartBeanSet;
    }

}
