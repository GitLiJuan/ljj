package com.company.service.impl;

import com.company.common.ServerResponse;
import com.company.dao.idao.*;
import com.company.dao.pojo.*;
import com.company.ov.OrderList;
import com.company.ov.OrderProduct;
import com.company.ov.Orders;
import com.company.ov.ShippingVo;
import com.company.service.iservice.OrderService;
import com.company.util.PropertiesUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whisper on 2017/11/29n 0029.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;
    /**
     * 创建订单
     * @param shippingId 地址id
     * @return success 返回订单信息
     *          error 返回 创建订单失败
     */
    @Transactional
    @Override
    public ServerResponse<Orders> create(Integer shippingId, Integer userId) {
        Order order = null;
        List<OrderItem> list1 = new ArrayList<>();
        long orderNo = System.currentTimeMillis();
        try{
            //根据用户id查找所有的被选中的购物项
            List<Cart> list = cartMapper.selectByChecked(userId);
            if (list == null || list.size() <= 0){
                return ServerResponse.createErrorMessageResponse("创建订单失败!");
            }
            Product product = null;
            OrderItem orderItem = null;
            BigDecimal payment = new BigDecimal(0);
            for (Cart cart : list){
                product = productMapper.selectByPrimaryKey(cart.getProductId());
                if (product == null)
                    return ServerResponse.createErrorMessageResponse("创建订单失败!");
                System.out.println(product + "+++++++++++++");
                payment = payment.add(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
                System.out.println(payment + "+++++++++");
                orderItem = new OrderItem(userId, orderNo, product.getId(), product.getName(), product.getMainImage(), product.getPrice(), cart.getQuantity(), product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
                list1.add(orderItem);
                orderItemMapper.insertSelective(orderItem);
                cartMapper.deleteByPrimaryKey(cart.getId());
            }
            order = new Order(orderNo, userId, shippingId, payment, 1, 0, 10);
            orderMapper.insertSelective(order);
        }catch (Exception e){
            return ServerResponse.createErrorMessageResponse("创建订单失败!");
        }
        order = orderMapper.selectOrderByOrderNo(orderNo);
        list1 = orderItemMapper.selectOrderItemByOrderNo(orderNo);
        return ServerResponse.createSuccessResponse(new Orders(order, list1));
    }
    /**
     * 根据用户id查询订单的商品信息
     * @param userId
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public ServerResponse<OrderProduct> getOrderCartProduct(Integer userId) {
        List<OrderItem> list = orderItemMapper.selectOrderItemByUserId(userId);
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix", "");
        BigDecimal totalPrice = new BigDecimal(0);
        OrderProduct orderProduct = null;
        if (list != null && list.size() > 0){
            for (OrderItem orderItem : list){
                totalPrice = totalPrice.add(orderItem.getTotalPrice());
            }
            orderProduct = new OrderProduct(list, imageHost, totalPrice);
            return ServerResponse.createSuccessResponse(orderProduct);
        }
        return ServerResponse.createSuccessMessageResponse("该用户还没有订单!");
    }
    /**
     * 通过用户id查找
     * @param userId
     * @return success 返回订单list error返回乜有dingdanlist的信息
     */
    @Override
    public ServerResponse<PageInfo<OrderList>> getOrderListByUserId(Integer userId, int pageNum, int pageSize) {
        List<OrderItem> orderItemList = new ArrayList<>();
        List<OrderList> orderListList = new ArrayList<>();
        OrderList orderList1 = null;
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix", "");
        Shipping shipping = null;
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectOrderListByUserId(userId);
        if (orderList.size() > 0 && orderList != null){
            for (Order order : orderList){
                if (order.getPaymentType() == 1)
                    order.setPaymentTypeDesc("在线支付");
                switch (order.getStatus()){
                    case 0 :
                        order.setStatusDesc("已取消");
                        break;
                    case 10 :
                        order.setStatusDesc("未付款");
                        break;
                    case 20 :
                        order.setStatusDesc("已付款");
                        break;
                    case 40 :
                        order.setStatusDesc("已发货");
                        break;
                    case 50 :
                        order.setStatusDesc("交易成功!");
                        break;
                    case 60 :
                        order.setStatusDesc("交易关闭");
                        break;
                    default:
                        break;
                }
                order.setUserId(null);
                orderItemList = orderItemMapper.selectOrderItemByOrderNo(order.getOrderNo());
                for (OrderItem orderItem :orderItemList){
                    orderItem.setUserId(null);
                }
                shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
                orderList1 = new OrderList(order, orderItemList, imageHost, shipping.getId(), shipping.getReceiverName());
                orderListList.add(orderList1);
            }
            PageInfo<OrderList> pageInfo = new PageInfo<>(orderListList);
            return ServerResponse.createSuccessResponse(pageInfo);
        }
        return ServerResponse.createSuccessMessageResponse("该用户现在还没有订单!");
    }
    /**
     * 根据订单编号查询订单详情
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderList> getOrderDetailByOrderNo(long orderNo) {
        Order order = orderMapper.selectOrderByOrderNo(orderNo);
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix", "");
        if (order != null){
            if (order.getPaymentType() == 1)
                order.setPaymentTypeDesc("在线支付");
                switch (order.getStatus()){
                    case 0 :
                        order.setStatusDesc("已取消");
                        break;
                    case 10 :
                        order.setStatusDesc("未付款");
                        break;
                    case 20 :
                        order.setStatusDesc("已付款");
                        break;
                    case 40 :
                        order.setStatusDesc("已发货");
                        break;
                    case 50 :
                        order.setStatusDesc("交易成功!");
                        break;
                    case 60 :
                        order.setStatusDesc("交易关闭");
                        break;
                    default:
                        break;
                }
            order.setUserId(null);

            List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderNo(orderNo);
            Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
            OrderList orderList = new OrderList(order, orderItemList, imageHost, shipping.getId(), shipping.getReceiverName(), new ShippingVo(shipping.getReceiverName(), shipping.getReceiverPhone(), shipping.getReceiverMobile(), shipping.getReceiverProvince(), shipping.getReceiverCity(), shipping.getReceiverDistrict(), shipping.getReceiverAddress(), shipping.getReceiverZip()));
            return ServerResponse.createSuccessResponse(orderList);
        }
        return ServerResponse.createErrorMessageResponse("该用户没有订单!");
    }

    /**
     *
     * @param orderNo
     * @param status 0-已取消 10-未付款 20-已付款 40-已发货 50-交易成功 60-交易关闭
     * @return
     */
    @Override
    public ServerResponse<Order> updateOrderStatus(long orderNo, Integer status) {
        Order order = orderMapper.selectOrderByOrderNo(orderNo);
        if (order.getStatus() >= 20)
            return ServerResponse.createErrorMessageResponse("订单已付款不能取消");
        int flag = orderMapper.updateOrderStatus(orderNo, status);
        if (flag == 1)
            return ServerResponse.createSuccessMessageResponse("修改成功!");
        else
            return ServerResponse.createErrorMessageResponse("该用户没有此订单!");
    }
    @Override
    public ServerResponse<Order> updateManageOrderStatus(long orderNo, Integer status) {
        Order order = orderMapper.selectOrderByOrderNo(orderNo);
        int flag = -1;
        if (order.getStatus() > 20 && order.getStatus() < 40){
            flag = orderMapper.updateOrderStatus(orderNo, status);
            if (flag == 1)
                return ServerResponse.createSuccessMessageResponse("发货成功!");
            else
                return ServerResponse.createErrorMessageResponse("发货失败!");
        }else if(order.getStatus() < 20){
            return ServerResponse.createErrorMessageResponse("发货失败!");
        }else {
            return ServerResponse.createErrorMessageResponse("发货失败!");
        }
    }

    /**
     * 根据订单号模糊查询
     * @param orderNo
     * @return
     */
    @Override
    public ServerResponse<OrderList> getOrderDetailsByOrderNo1(long orderNo) {
        Order order = orderMapper.selectOrderByOrderNo1(orderNo);
        String imageHost = PropertiesUtil.getProperty("ftp.server.http.prefix", "");
        if (order != null){
            if (order.getPaymentType() == 1)
                order.setPaymentTypeDesc("在线支付");
            switch (order.getStatus()){
                case 0 :
                    order.setStatusDesc("已取消");
                    break;
                case 10 :
                    order.setStatusDesc("未付款");
                    break;
                case 20 :
                    order.setStatusDesc("已付款");
                    break;
                case 40 :
                    order.setStatusDesc("已发货");
                    break;
                case 50 :
                    order.setStatusDesc("交易成功!");
                    break;
                case 60 :
                    order.setStatusDesc("交易关闭");
                    break;
                default:
                    break;
            }
            order.setUserId(null);

            List<OrderItem> orderItemList = orderItemMapper.selectOrderItemByOrderNo(orderNo);
            Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
            OrderList orderList = new OrderList(order, orderItemList, imageHost, shipping.getId(), shipping.getReceiverName(), new ShippingVo(shipping.getReceiverName(), shipping.getReceiverPhone(), shipping.getReceiverMobile(), shipping.getReceiverProvince(), shipping.getReceiverCity(), shipping.getReceiverDistrict(), shipping.getReceiverAddress(), shipping.getReceiverZip()));
            return ServerResponse.createSuccessResponse(orderList);
        }
        return ServerResponse.createErrorMessageResponse("没有查到!");
    }
}
