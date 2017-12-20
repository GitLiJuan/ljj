package com.company.dao.idao;

import com.company.dao.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /**
     * 根据订单号查询订单
     * @param orderNo
     * @return success 返回订单 error 返回null
     */
    Order selectOrderByOrderNo(@Param("orderNo") long orderNo);
    /**
     * 根据订单号模糊查询订单
     * @param orderNo
     * @return success 返回订单 error 返回null
     */
    Order selectOrderByOrderNo1(@Param("orderNo") long orderNo);

    /**
     * 根据用户id查找用户所有的订单
     * @param userId
     * @return
     */
    List<Order> selectOrderListByUserId(@Param("userId") Integer userId);

    /**
     * 修改订单状态
     * @param orderNo
     * @param status
     * @return
     */
    int updateOrderStatus(@Param("orderNo") long orderNo,@Param("status") Integer status);
}