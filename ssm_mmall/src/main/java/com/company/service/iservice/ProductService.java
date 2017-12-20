package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Product;
import com.company.vo.ProductDetailVo;
import com.company.vo.ProductListVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface ProductService {
    //新增商品,更新产品
   ServerResponse<Product> insertorupdate(Product record);
    //后台：查询商品列表
    ServerResponse<PageInfo<ProductListVo>> findAll(Integer PageNum, Integer PageSize);
    //根据id,商品名查询商品
    ServerResponse<PageInfo<ProductListVo>> selectByIdOrName(Integer PageNum,Integer PageSize,Product record);
    //后台：查询商品详情
    ServerResponse<ProductDetailVo> selectmandetail(Integer id);
    //商品上下架（修改状态）
    ServerResponse<Product> upAndDown(Integer id,Integer status);

    //前台：商品查询及动态排序
    ServerResponse <PageInfo<ProductListVo>> orderByprice(Integer PageNum,Integer PageSize,String name,Integer categoryId,Integer flag);
    //前台：查询商品详情
    ServerResponse<Product> selectdetail(Integer id);



}
