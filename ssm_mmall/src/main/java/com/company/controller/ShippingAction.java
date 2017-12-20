package com.company.controller;

import com.company.common.Const;
import com.company.common.ResponseCode;
import com.company.common.ServerResponse;
import com.company.dao.pojo.Shipping;
import com.company.dao.pojo.User;
import com.company.service.iservice.ShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Controller
@RequestMapping("shipping")
public class ShippingAction {
    @Autowired
    private ShippingService shippingService;

    //添加收货地址
    @RequestMapping(value = "add.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse add(Shipping shipping,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = shippingService.add(shipping);
        if(cnt == 0){
            return ServerResponse.createErrorMessageResponse("新建地址失败!");
        }else{
            return ServerResponse.createSuccessMessageResponse("新建地址成功!");
        }
    }

    //删除收货地址
    @RequestMapping(value = "delete.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete(Integer id,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = shippingService.delete(id);
        if(cnt == 0){
            return ServerResponse.createErrorMessageResponse("删除地址失败!");
        }else{
            return ServerResponse.createSuccessMessageResponse("删除地址成功!");
        }
    }

    //登录状态下更新地址信息
    @RequestMapping(value = "update.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update(Shipping shipping,HttpSession session){
        System.out.println("=========");
        User user= (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        int cnt = shippingService.update(shipping);
        if(cnt == 0){
            return ServerResponse.createErrorMessageResponse("更新地址失败!");
        }else{
            return ServerResponse.createSuccessMessageResponse("更新地址成功!");
        }
    }

    //根据id查看详细地址信息
    @RequestMapping(value = "select.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse select(Integer id,HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if( user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        return shippingService.select(id);
    }

    //分页显示地址列表
    @RequestMapping(value = "findAll.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PageInfo<Shipping>> findAll(Integer pageNum,Integer pageSize,HttpSession session){
        pageNum = pageNum == null ? 1 : pageNum;
        pageSize = pageSize == null ? 10 : pageSize;
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createErrorNeedLoginResponse("用户未登录!");
        }
        return shippingService.findAll(pageNum,pageSize);
    }
}
