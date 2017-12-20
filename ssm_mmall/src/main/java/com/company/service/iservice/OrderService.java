package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Order;
import com.company.ov.OrderList;
import com.company.ov.OrderProduct;
import com.company.ov.Orders;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Whisper on 2017/11/29 0029.
 */
public interface OrderService {
    /**
     * 创建订单
     * @param shippingId 地址id
     * @return success 返回订单信息
     *          error 返回 创建订单失败
     */
    ServerResponse<Orders> create(Integer shippingId, Integer userId);
    /**
     * 根据用户id查询订单的商品信息
     * @param userId
     * @return
     */
    ServerResponse<OrderProduct> getOrderCartProduct(Integer userId);

    /**
     * 通过用户id查找
     * @param userId
     * @return success 返回订单list error返回乜有dingdanlist的信息
     */
    ServerResponse<PageInfo<OrderList>> getOrderListByUserId(Integer userId, int pageNum, int pageSize);

    /**
     * 根据订单编号查询订单详情
     * @param orderNo
     * @return
     */
    ServerResponse<OrderList> getOrderDetailByOrderNo(long orderNo);

    /**
     * 修改订单状态
     * @param status 0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭
     * @param orderNo
     * @return
     */
    ServerResponse<Order> updateOrderStatus(long orderNo, Integer status);

    /**
     * 订单发货
     * @param orderNo
     * @param status
     * @return
     */
    ServerResponse<Order> updateManageOrderStatus(long orderNo, Integer status);

    /**
     * 根据订单编号模糊查询订单详情
     * @param orderNo
     * @return
     */
    ServerResponse<OrderList> getOrderDetailsByOrderNo1(long orderNo);


}
