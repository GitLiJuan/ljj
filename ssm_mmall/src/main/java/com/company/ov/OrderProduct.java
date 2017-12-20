package com.company.ov;

import com.company.dao.pojo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Whisper on 2017/11/30 0030.
 */

/**
 * 查询商品的详情
 */
public class OrderProduct {
    private List<OrderItem> orderItemVoList;
    private String imageHost;
    private BigDecimal totalPrice;

    public OrderProduct() {
    }

    public OrderProduct(List<OrderItem> orderItemVoList, String imageHost, BigDecimal totalPrice) {
        this.orderItemVoList = orderItemVoList;
        this.imageHost = imageHost;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderItemVoList=" + orderItemVoList +
                ", imageHost='" + imageHost + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public List<OrderItem> getOrderItemVoList() {
        return orderItemVoList;
    }

    public void setOrderItemVoList(List<OrderItem> orderItemVoList) {
        this.orderItemVoList = orderItemVoList;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
