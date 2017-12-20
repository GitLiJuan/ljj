package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.User;

import java.util.Set;

/**
 * Created by Administrator on 2017/11/28.
 */
public interface UserService {
    /**
     * 获得用户
     * @param username
     * @return
     */
    public User getByUsername(String username);
    /**
     * 获得角色
     * @param username
     * @return
     */
    public Set<String> getRoles(String username);


    /**
     * 注册,添加用户
     * @param user
     * @return String
     */
    public ServerResponse<String> registry(User user);


    /**
     * 用户登陆
     * @param username
     * @param password
     * @return ServerResponse<User>
     */
    public ServerResponse<User> login(String username ,String password);
    /**
     * 在注册是检验用户名和邮箱是否可用
     * @param str  用户名或邮箱
     * @param type 类型Const中的ValidType(USERNAME,EMAIL)
     * @return ServerResponse<String>
     */
    public ServerResponse<String> checkValid(String str,String type);

    /**
     * 忘记密码获取密保问题
     * @param username
     * @return
     */
    public ServerResponse<String> getQuestionByUsername(String username);

    /**
     * 忘记密码,根据提示信息,验证答案
     * @param username
     * @param question
     * @param answer
     * @return
     */
    public ServerResponse<String> checkAnswer(String username,String question ,String answer);

    /**
     *用户通过密保问题后重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 登陆用户重置密码
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    public ServerResponse<String> registryPassword(String passwordOld, String passwordNew,User user);

    /**
     * 登录用户修改用户信息
     * @param user
     * @return
     */
   public ServerResponse<User> updateUserInformation(User user);

    /**
     * 登录用户获取用户信息
     * @param id
     * @return
     */
   public ServerResponse<User> getInformation(Integer id);
}
