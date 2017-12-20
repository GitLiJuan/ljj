package com.company.dao.idao;

import com.company.dao.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {

    int updateByPrimaryKeySelective(Shipping record);//动态更新

    int insertSelective(Shipping record);//动态添加

    //根据id删除地址信息
    int deleteByPrimaryKey(@Param("id") Integer id);

    //新添地址
    int insert(Shipping record);

    //根据id查看详细地址
    Shipping selectByPrimaryKey(@Param("id") Integer id);

    //更新地址信息
    int updateByPrimaryKey(Shipping record);

    /**
     *分页显示地址列表
     * @return
     */
    List<Shipping> findAll();
}