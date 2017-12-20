package com.company.controller;

import com.company.common.Const;
import com.company.common.ServerResponse;
import com.company.dao.pojo.Order;
import com.company.dao.pojo.User;
import com.company.ov.OrderList;
import com.company.service.iservice.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Whisper on 2017/11/30 0030.
 */
@Controller
@RequestMapping("manage/order")
@ResponseBody
public class ManageOrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 管理员查看订单 并分页
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "list.do", method = RequestMethod.POST)
    public ServerResponse<PageInfo<OrderList>> getOrderList(HttpSession session, Integer pageNum, Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createErrorMessageResponse("用户未登录!");
        else if (user.getRole() == 0)
            return ServerResponse.createSuccessMessageResponse("没有权限!");
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        return  orderService.getOrderListByUserId(null, pageNum, pageSize);
    }
    @RequestMapping(value = "detail.do", method = RequestMethod.POST)
    public ServerResponse<OrderList> getOrderDetailByOrderNo(Long orderNo){
        return orderService.getOrderDetailByOrderNo(orderNo);
    }
    @RequestMapping(value = "search.do", method = RequestMethod.POST)
    public ServerResponse<OrderList> searchOrderListByOrderNo(Long orderNo){
        return orderService.getOrderDetailsByOrderNo1(orderNo);
    }
    @RequestMapping(value = "send_goods.do", method = RequestMethod.POST)
    public ServerResponse<Order> sendGoods(Long orderNo){
        return orderService.updateManageOrderStatus(orderNo, 40);
    }
}