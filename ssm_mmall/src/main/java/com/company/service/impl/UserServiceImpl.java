package com.company.service.impl;

import com.company.common.Const;
import com.company.common.ServerResponse;
import com.company.common.TokenCache;
import com.company.dao.idao.UserMapper;
import com.company.dao.pojo.User;
import com.company.service.iservice.UserService;
import com.company.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

/**
 * Created by Administrator on 2017/11/28.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    @Override
    @Transactional
    public Set<String> getRoles(String username) {
        return null;
    }


    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> registry(User user) {
        //验证用户名是否已存在
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.ValidType.USERNAME);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse("用户已存在!");
        }
        //验证用邮箱是否已存在
        validResponse = this.checkValid(user.getEmail(), Const.ValidType.EMAIL);
        if (!validResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse("Email已存在");
        }
        //设置用户默认的角色
        user.setRole(Const.Role.ROLE_USER);
        //MD5对密码进行加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse("注册失败");
        }
        return ServerResponse.createSuccessMessageResponse("注册成功");
    }

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return ServerResponse<User>
     */
    @Override
    @Transactional
    public ServerResponse<User> login(String username, String password) {
        //验证用户名是否存在,不存在返回错误信息
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse("用户名不存在!");
        }
        //登陆验证
        User user = userMapper.login(username, MD5Util.MD5EncodeUtf8(password));
        if (user == null) {
            return ServerResponse.createErrorMessageResponse("用户密码错误!");
        }
        //如果登陆成功,返回数据中不应该含有密码,对密码进行处理
        //应用的org.apache.commons.lang3.StringUtil工具类进行处理,返回空的字符串
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createSuccessResponse("登陆成功!", user);
    }

    /**
     * 验证用户名.邮箱是否存在(有的用户用邮箱登陆)
     *
     * @param str  用户名或邮箱
     * @param type 类型Const中的ValidType(USERNAME,EMAIL)
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.ValidType.USERNAME.equals(type)) {
                int resultCount = userMapper.checkUsername(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMessageResponse("用户已存在!");
                }
            }
            if (Const.ValidType.EMAIL.equals(type)) {
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0) {
                    return ServerResponse.createErrorMessageResponse("Email已存在!");
                }
            }
        } else {
            return ServerResponse.createErrorMessageResponse("参数类型错误,只能选择用户名或Email!");
        }
        return ServerResponse.createSuccessMessageResponse("用户名/Email校验成功!");
    }

    /**
     * 获取用户的密保问题
     *
     * @param username
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> getQuestionByUsername(String username) {
        //验证用户名是否存在
        ServerResponse validResponse = checkValid(username, Const.ValidType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse("用户名不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.createSuccessResponse(question);
        }
        return ServerResponse.createErrorMessageResponse("该用户没有设置密保问题");
    }

    /**
     * 忘记密码,根据提示信息,验证答案
     *
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        int resultCount = userMapper.checkAnswer(username, question, answer);
        if (resultCount > 0) {
            //用户提交答案正确,通过UUID算法生成唯一的token值
            String forgetToken = UUID.randomUUID().toString();
            //通过GUAVA缓存存取该token
            TokenCache.setKey("token_" + username, forgetToken);
            //将唯一的token令牌存入ServerResponse并返回
            return ServerResponse.createSuccessResponse(forgetToken);

        }
        return ServerResponse.createErrorMessageResponse("密保问题回答错误!");
    }

    /**
     * 用户通过密保问题后重置密码
     *
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return ServerResponse<String>
     */
    @Override
    @Transactional
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //校验token令牌是否存在
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.createErrorMessageResponse("参数错误,需要传递token令牌");
        }
        //判断用户名是否存在
        ServerResponse validResponse = checkValid(username, Const.ValidType.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createErrorMessageResponse("用户名不存在!");
        }
        //从GUAVA缓存中获取的token是否有效
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.createErrorMessageResponse("token已失效!");
        }
        //对比前端传过来的forgetToken与从GUAVA缓存中获取的token是否一致
        if (StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userMapper.updatePasswordByUsername(username, md5Password);
            if (resultCount > 0) {
                return ServerResponse.createSuccessMessageResponse("修改密码成功!");
            }
        }
        return ServerResponse.createErrorMessageResponse("修改密码失败!");
    }

    /**
     * 登陆用户重置密码
     *
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<String> registryPassword(String passwordOld, String passwordNew, User user) {
        //验证旧密码
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (resultCount == 0) {
            return ServerResponse.createErrorMessageResponse("原密码输入错误,请从新输入!");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0) {
            return ServerResponse.createSuccessMessageResponse("修改密码成功!");
        }
        return ServerResponse.createErrorMessageResponse("修改密码失败!");
    }

    /**
     * 登录用户修改用户信息
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<User> updateUserInformation(User user) {
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(), user.getId());
        if (resultCount > 0) {
            return ServerResponse.createErrorMessageResponse("Email已被使用,请重新输入!");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0) {
            return ServerResponse.createSuccessResponse("更新用户信息成功!",updateUser);
        }
        return ServerResponse.createErrorMessageResponse("更新用户失败!");
    }

    /**
     * 登陆用户获取用户信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ServerResponse<User> getInformation(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.createErrorMessageResponse("找不到当前用户!");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createSuccessResponse(user);
    }
}
