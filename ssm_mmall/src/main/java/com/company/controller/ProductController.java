package com.company.controller;

import com.company.common.Const;
import com.company.common.ServerResponse;
import com.company.dao.pojo.Product;
import com.company.service.iservice.ProductService;
import com.company.vo.ProductListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productservice;
    //商品详情
    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<Product> selectdetail( Integer productId, HttpSession session){
        ServerResponse responseResult=productservice.selectdetail(productId);
        if(responseResult.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responseResult.getData());
        }
        return  responseResult;
    }

    //商品排序
    @RequestMapping(value = "list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<List<ProductListVo>> orderByprice(Integer PageNum, Integer PageSize , String keyword, Integer categoryId, String orderBy, HttpSession session){
        if(PageSize==null){
            PageSize=10;
        }
        if(PageNum==null){
            PageNum=1;
        }
        Integer flag=-1;
        if(orderBy.equals("price_desc")){
            flag=1;
        }else if(orderBy.equals("price_asc")){
            flag=0;
        }
        ServerResponse responseResult=productservice.orderByprice(PageNum,PageSize,keyword,categoryId,flag);
        if(responseResult.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responseResult.getData());
        }
        return responseResult;
    }
}
