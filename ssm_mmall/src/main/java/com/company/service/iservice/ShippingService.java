package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Shipping;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public interface ShippingService {

    int add(Shipping shipping);//添加地址

    int delete(@Param("id") Integer id);//删除地址

    int update(Shipping shipping);//更新地址

    ServerResponse<Shipping>  select( @Param("id")Integer id);//根据id查看收货详细地址

    ServerResponse<PageInfo<Shipping>> findAll(int pageNum, int pageSize);//分页查询
}
