package com.company.controller;

import com.company.common.Const;
import com.company.common.ServerResponse;
import com.company.dao.pojo.Order;
import com.company.dao.pojo.User;
import com.company.ov.OrderList;
import com.company.ov.OrderProduct;
import com.company.ov.Orders;
import com.company.service.iservice.OrderService;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.activation.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Whisper on 2017/11/29 0029.
 */
@Controller
@RequestMapping(value = "order")
@ResponseBody
public class OrderController {
    @Autowired
    private OrderService orderService;
    /**
     *
     * @param session 获取session,获取当前绑定用户
     * @param shippingId 接收前台的收货地址id
     * @return success返回订单信息 error返回错误信息
     */
    @RequestMapping(value = "create.do", method = RequestMethod.POST)
    public ServerResponse<Orders> create(HttpSession session, Integer shippingId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        ServerResponse<Orders> serverResponse = orderService.create(shippingId, 26);
        return serverResponse;
    }

    /**
     * 获取当前用户的订单商品
     * @param session
     * @return
     */
    @RequestMapping(value = "get_order_cart_product.do", method = RequestMethod.POST)
    public ServerResponse<OrderProduct> getOrderCartProduct(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        return orderService.getOrderCartProduct(user.getId());
    }

    /**
     * 查询用户订单,需要分页,根据用户id
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo<OrderList>> list(HttpSession session, Integer pageNum, Integer pageSize){
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        return orderService.getOrderListByUserId(user.getId(), pageNum, pageSize);
    }

    /**
     * 根据订单编号查询订单详情
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    public ServerResponse<OrderList> getOrderList(Long orderNo){
        return orderService.getOrderDetailByOrderNo(orderNo);
    }

    /**
     * 取消订单
     * @param orderNo
     * @return
     */
    @RequestMapping(value = "cacel.do", method = RequestMethod.POST)
    public ServerResponse<Order> cacelOrder(Long orderNo){
        return orderService.updateOrderStatus(orderNo, 0);
    }

}
