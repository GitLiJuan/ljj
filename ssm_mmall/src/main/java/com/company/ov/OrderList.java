package com.company.ov;

import com.company.dao.pojo.Order;
import com.company.dao.pojo.OrderItem;
import com.company.dao.pojo.Shipping;

import java.util.List;

/**
 * Created by Whisper on 2017/11/30 0030.
 */
public class OrderList {
    private Order order;
    private List<OrderItem> orderItemList;
    private String imageHost;
    private Integer shippingId;
    private String receiveName;
    private ShippingVo shippingVo;
    public OrderList() {
    }

    public OrderList(Order order, List<OrderItem> orderItemList, String imageHost, Integer shippingId, String receiveName, ShippingVo shippingVo) {
        this.order = order;
        this.orderItemList = orderItemList;
        this.imageHost = imageHost;
        this.shippingId = shippingId;
        this.receiveName = receiveName;
        this.shippingVo = shippingVo;
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public OrderList(Order order, List<OrderItem> orderItemList, String imageHost, Integer shippingId, String receiveName) {
        this.order = order;
        this.orderItemList = orderItemList;
        this.imageHost = imageHost;
        this.shippingId = shippingId;
        this.receiveName = receiveName;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "order=" + order +
                ", orderItemList=" + orderItemList +
                ", imageHost='" + imageHost + '\'' +
                ", shippingId=" + shippingId +
                ", receiveName='" + receiveName + '\'' +
                ", shippingVo=" + shippingVo +
                '}';
    }
}
