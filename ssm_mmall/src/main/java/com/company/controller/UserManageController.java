package com.company.controller;

import com.company.common.Const;
import com.company.common.ResponseCode;
import com.company.common.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/30.
 */
@Controller
@RequestMapping("user/manage")
public class UserManageController {
    @Autowired
    UserService userService;
    @ResponseBody
    @RequestMapping(value = "login.do" ,method = RequestMethod.POST)
    public ServerResponse<User> login(HttpSession session,String username, String password){
        ServerResponse<User> response = userService.login(username,password);
        if (response.isSuccess()){
            User user = (User) response.getData();
            if (user.getRole()== Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return ServerResponse.createSuccessResponse(user);
            }
        }
        return ServerResponse.createErrorMessageResponse("该用户没有权限,无法登陆管路系统!");
    }
}
