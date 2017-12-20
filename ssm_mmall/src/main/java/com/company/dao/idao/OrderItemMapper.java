package com.company.dao.idao;

import com.company.dao.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderItem record);

    int insertSelective(OrderItem record);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);

    /**
     * 根据订单号查询所有的订单项
     * @param orderNo 订单号
     * @return success 返回一个订单项集合 error 返回null
     */
    List<OrderItem> selectOrderItemByOrderNo(@Param("orderNo") long orderNo);

    /**
     * 根据用户id查询订单的商品信息
     * @param userId
     * @return
     */
    List<OrderItem> selectOrderItemByUserId(@Param("userId") Integer userId);
}