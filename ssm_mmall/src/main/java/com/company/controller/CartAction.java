package com.company.controller;

import com.company.common.Const;
import com.company.common.ResponseCode;
import com.company.common.ServerResponse;
import com.company.dao.idao.CartMapper;
import com.company.dao.pojo.User;
import com.company.dao.vo.CartProduct;
import com.company.dao.vo.CartProductVoList;
import com.company.service.iservice.CartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service
@RequestMapping("cart")
public class CartAction {

    @Autowired
    private CartService cartService;

    //查看购物车列表
    @RequestMapping(value = "list.do",method = RequestMethod.POST)
    @ResponseBody
    private ServerResponse findAllProduct( HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        return cartService.findAllProduct(user.getId());
   }

    //购物车添加商品
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<CartProductVoList> addProduct(int productId, int count, HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        return cartService.addProduct(user.getId(),productId,count);
    }

    //购物车更新商品数量
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateNum(int productId,int count,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.updateNum(user.getId(),productId,count);
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

    //删除购物车商品
    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse deleteProduct(int productIds[],HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.deleteProduct(user.getId(),productIds);
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

    //选中购物车某一个商品
    @RequestMapping(value = "seleteOne.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse checkOneProduct(Integer productId,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.checkOneProduct(user.getId(),productId);
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

    //取消选中购物车某一个商品
    @RequestMapping(value = "unSeleteOne.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse unCheckedProduct(Integer productId,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.unCheckedProduct(user.getId(),productId);
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

    //查看购物车中的产品数量
    @RequestMapping(value = "get_cart_product_count.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse findCount(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        return cartService.findCount(user.getId());
    }

    //全选
    @RequestMapping(value = "select_all.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse allCheck(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.allCheck(user.getId());
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

    //取消全选
    @RequestMapping(value = "un_select_all.do",method = RequestMethod.POST)
    @ResponseBody
    protected ServerResponse allUnCheck(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = cartService.allUnCheck(user.getId());
        if(cnt == 0){
            return ServerResponse.CreateErrorIllegalArgumentResponse("非法参数!");
        }
        ServerResponse sr=cartService.findAllProduct(user.getId());
        return sr;
    }

}
