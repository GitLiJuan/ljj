package com.company.service.impl;

import com.company.common.ServerResponse;
import com.company.dao.idao.ProductMapper;
import com.company.dao.pojo.Product;
import com.company.service.iservice.ProductService;
import com.company.vo.ProductDetailVo;
import com.company.vo.ProductListVo;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service("productservice")
 public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;


    @Transactional
    @Override
    public  ServerResponse<Product> insertorupdate(Product record) {
       Integer id = record.getId();
        int flag = -1;
        if (id != null){
           flag = productMapper.updateByPrimaryKeySelective(record);
            if (flag == 1)
                return ServerResponse.createSuccessMessageResponse("更新商品成功！");
            else
                return ServerResponse.createErrorMessageResponse("更新商品失败！");
        }else {
            flag = productMapper.insertSelective(record);
            if (flag == 1)
                return ServerResponse.createSuccessMessageResponse("增加商品成功！");
            else
                return ServerResponse.createErrorMessageResponse("增加商品失败 ！");
        }

    }
    @Transactional
    @Override
    public ServerResponse<PageInfo<ProductListVo>> findAll(Integer PageNum, Integer PageSize) {
      PageHelper.startPage(PageNum,PageSize);
      List<ProductListVo> list=productMapper.findAll();

        if(list==null){
            return ServerResponse.createErrorMessageResponse("用户未登录，请登录");
        }
        PageInfo<ProductListVo> page=new PageInfo<ProductListVo>(list);
        return ServerResponse.createSuccessResponse(page);
    }


    @Transactional
    @Override
    public  ServerResponse<PageInfo<ProductListVo>> selectByIdOrName(Integer PageNum,Integer PageSize,Product record) {
        PageHelper.startPage(PageNum,PageSize);
        List<ProductListVo> list=productMapper.selectByIdOrName(record);
        PageInfo<ProductListVo> page=new PageInfo<ProductListVo>(list);
        if(list==null){
            return  ServerResponse.createErrorMessageResponse("用户未登录，请登录");
        }

        return ServerResponse.createSuccessResponse(page);

    }


    @Transactional
    @Override
    public  ServerResponse<Product> selectdetail(Integer id) {
        Product product=productMapper.selectdetail(id);
       if(product==null){
            return  ServerResponse.createErrorMessageResponse("没有该商品!");
        }
        return ServerResponse.createSuccessResponse(product);
    }


    @Transactional
    @Override
    public ServerResponse<PageInfo<ProductListVo>> orderByprice(Integer PageNum,Integer PageSize,String name, Integer categoryId, Integer flag) {
        PageHelper.startPage(PageNum,PageSize);
        List<ProductListVo> list=productMapper.orderByprice(name,categoryId,flag);
        PageInfo<ProductListVo> page=new PageInfo<ProductListVo>(list);
        if(list==null){
            return ServerResponse.createErrorMessageResponse("参数错误");
        }

        return ServerResponse.createSuccessResponse(page);
    }



    @Transactional
    @Override
    public ServerResponse<ProductDetailVo> selectmandetail(Integer id) {
        ProductDetailVo product=productMapper.selectmandetail(id);
        product.setImageHost("http://img.emall.com");
       if(product==null){
           return  ServerResponse.createErrorMessageResponse("用户未登录，请登录");
       }
        return ServerResponse.createSuccessResponse(product);
    }
    @Transactional
    @Override
    public ServerResponse<Product> upAndDown(Integer id, Integer status) {
        int flag = -1;
        if (status == 1) {
            flag = productMapper.up(id, status);

            if (flag == 1)
                return ServerResponse.createSuccessMessageResponse("商品上架成功");
            else
                return ServerResponse.createErrorMessageResponse("商品上架失败");

        } else{
            flag = productMapper.down(id, status);
            if (flag == 1)
                return ServerResponse.createSuccessMessageResponse("商品下架成功！");

            else {
                return ServerResponse.createErrorMessageResponse("商品下架失败");
            }
        }

    }
}
