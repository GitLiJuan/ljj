package com.company.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * Created by Whisper on 2017/11/29 0029.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;
    //匹配String类型参数
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }
    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //匹配除了String类型之外的数据
    //如果用户传入的就是String类型数据需要在共有方法中区分
    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }



    //返回data
    public static <T> ServerResponse<T> createSuccessResponse(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    //返回msg,data
    public static <T> ServerResponse<T> createSuccessResponse(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    //返回msg
    public static <T> ServerResponse<T> createSuccessMessageResponse(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    //运行失败时返回对象
    public static <T> ServerResponse<T> createErrorResponse() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> ServerResponse<T> createErrorResponse(T data) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), data);
    }

    public static <T> ServerResponse<T> createErrorResponse(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> createErrorMessageResponse(String msg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), msg);
    }
    public static <T> ServerResponse<T> createErrorNeedLoginResponse(String msg){
        return  new ServerResponse<T>(ResponseCode.NEED_LOGIN.getCode(), msg);
    }
    public static <T> ServerResponse<T> CreateErrorIllegalArgumentResponse(String msg){
        return new ServerResponse<T>(ResponseCode.ILLEGAL_ARGUMENT.getCode(), msg);
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

}
