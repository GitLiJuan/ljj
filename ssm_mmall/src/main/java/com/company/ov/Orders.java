package com.company.ov;

import com.company.dao.pojo.Order;
import com.company.dao.pojo.OrderItem;

import java.util.List;

/**
 * Created by Whisper on 2017/11/29 0029.
 */

/**
 * 生成订单之后生成返回订单的数据
 */
public class Orders {
    private Order order;
    private List<OrderItem> orderItemVoList;

    public Orders() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItem> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public Orders(Order order, List<OrderItem> orderItemVoList) {

        this.order = order;
        this.orderItemVoList = orderItemVoList;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "order=" + order +
                ", orderItemVoList=" + orderItemVoList +
                '}';
    }
}
