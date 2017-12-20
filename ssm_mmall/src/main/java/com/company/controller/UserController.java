package com.company.controller;

import com.company.common.Const;
import com.company.common.ResponseCode;
import com.company.common.ServerResponse;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 注册
     * @param username
     * @param password
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "registry/{username}/{password}/{email}/{phone}/{question}/{answer}",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> Registry(@PathVariable("username") String username,@PathVariable("password") String password,@PathVariable("email") String email,@PathVariable("phone") String phone,@PathVariable("question") String question,@PathVariable("answer") String answer ){
        User user = new User(username,password,email,phone,question,answer);
        return userService.registry(user);
    }

    /**
     * 用户登陆/user/login.do
     * @param username
     * @param password
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login/{username}/{password}",method = RequestMethod.GET)
    public ServerResponse<User> Login(@PathVariable("username") String username,@PathVariable("password") String password, HttpSession session){
       ServerResponse<User> responseResult = userService.login(username,password);
        if (responseResult.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responseResult.getData());
        }
        System.out.println(Const.CURRENT_USER);
        System.out.println(session.getAttribute(Const.CURRENT_USER));
        return responseResult;
    }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        

    /**
     * 用户校验 用户名username,邮箱 Email
     * @param str 用户名或用户邮箱
     * @param type 属性(是用户名还是邮箱),在Const.ValidType中获取
     * @return ServerResponse<String>
     */
    @ResponseBody
    @RequestMapping(value = "check_valid/{str}/{type}",method = RequestMethod.GET)
    public ServerResponse<String> checkValid(@PathVariable("str") String str,@PathVariable("type")  String type){
        return userService.checkValid(str,type);
    }

    /**
     * 获取登陆用户的信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    public ServerResponse<User> getUserInformation(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user!=null){
            return ServerResponse.createSuccessResponse(user);
        }
        return ServerResponse.createErrorMessageResponse("用户尚未登陆,无法获取用户信息!");
    }

    /**
     * 注销功能
     * @param session
     * @return ServerResponse<String>
     */
    @ResponseBody
    @RequestMapping(value = "logout.do",method = RequestMethod.PUT)
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createSuccessResponse("注销成功");
    }

    /**
     * 忘记密码,根据用户名提示密保问题
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetPasswordGetQuestion(String username){
        return userService.getQuestionByUsername(username);
    }

    /**
     * 忘记密码,根据提示信息,验证答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetPasswordCheckAnswer(String username,String question,String answer){
        return userService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码重设密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return userService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登陆状态重设密码
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew ){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createErrorMessageResponse("用户尚未登陆!");
        }
        return userService.registryPassword(passwordOld,passwordNew,user);
    }

    /**
     * 已登录用户修改用户信息
     * @param session
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "update_information.do",method = RequestMethod.GET)
    public ServerResponse<User> updateUserInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        System.out.println(currentUser+"111111111111");
        //判断用户是否登陆
        if (currentUser==null){
            return ServerResponse.createErrorMessageResponse("用户尚未登陆!");
        }
        System.out.println(currentUser+"111111111");
        //用户名,id不允许需修改,从session对象中获取
        user.setUsername(currentUser.getUsername());
        user.setId(currentUser.getId());
        //更新信息
        ServerResponse<User> response =  userService.updateUserInformation(user);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 已登录用户获取用户信息,未登录的强制登陆
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_information.do",method = RequestMethod.GET)
    public ServerResponse<User> getInformation(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createErrorNeedLoginResponse("用户尚未登录,返回状态码10,前端判断状态码强制登陆");
        }
        return userService.getInformation(user.getId());
    }
}
