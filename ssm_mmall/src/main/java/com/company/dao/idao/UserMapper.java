package com.company.dao.idao;

import com.company.dao.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    User getByUsername(String username);

    /**
     * 用户登陆
     *
     * @param username
     * @param password
     * @return
     */
    User login(@Param("username") String username, @Param("password") String password);

    /**
     * 校验用户名是否存在
     *
     * @param username
     * @return
     */
    int checkUsername(@Param("username") String username);

    /**
     * 校验Email是否存在
     *
     * @param email
     * @return
     */
    int checkEmail(@Param("email") String email);

    /**
     * 根据用户名获得密保问题
     * @param username
     * @return
     */
    String selectQuestionByUsername(@Param("username") String username);


    /**
     * 校验回答密保问题是否正确
     * @param username
     * @param question
     * @param answer
     * @return
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 忘记密码用户修改密码
     * @param username
     * @param md5Password
     * @return
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("password") String md5Password);

    /**
     * 验证用户输入密码
     * @param password
     * @return
     */
    int checkPassword(@Param("password") String password,@Param("userId") int userId);

    /**
     * 修改用户信息是,检测email是否已被其他用户注册
     * @param email
     * @param id
     * @return
     */
    int checkEmailByUserId(@Param("email") String email,@Param("id") Integer id);

}