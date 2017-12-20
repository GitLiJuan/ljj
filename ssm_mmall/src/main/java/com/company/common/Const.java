package com.company.common;
public class Const {
    public static final String CURRENT_USER = "currentUser";
    //定义内部接口的方式,将静态内部常亮进行分组
    public interface Role{
        int ROLE_USER = 0;//用户权限
        int ROLE_ADMIN=1;//管理员权限
    }
    public interface ValidType{
        String USERNAME = "username";
        String EMAIL = "email";
    }
}
