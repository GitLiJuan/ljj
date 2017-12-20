package com.company.service.impl;

import com.company.common.ServerResponse;
import com.company.dao.idao.ShippingMapper;
import com.company.dao.pojo.Shipping;
import com.company.service.iservice.ShippingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Transactional
    @Override
    public int add(Shipping shipping) {
        return shippingMapper.insert(shipping);
    }

    @Transactional
    @Override
    public int delete(@Param("id") Integer id) {
        return shippingMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(Shipping shipping) {
        return shippingMapper.updateByPrimaryKeySelective(shipping);
    }

    @Transactional
    @Override
    public ServerResponse<Shipping> select(@Param("id") Integer id) {
        Shipping shipping = shippingMapper.selectByPrimaryKey(id);
        return ServerResponse.createSuccessResponse(shipping);
    }

    @Transactional
    @Override
    public ServerResponse<PageInfo<Shipping>> findAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.findAll();
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createSuccessResponse(pageInfo);
    }
}
