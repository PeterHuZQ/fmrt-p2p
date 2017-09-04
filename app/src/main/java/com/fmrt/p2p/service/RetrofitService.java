package com.fmrt.p2p.service;

import com.fmrt.p2p.usercenter.bean.ResultBeanData;
import com.fmrt.p2p.usercenter.bean.TokenBeanData;
import com.fmrt.p2p.usercenter.bean.UserBeanData;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Retrofit接口定义
 */

public interface RetrofitService
{
    //获取图形验证码
    @GET("vcode/{param}.jpg")
    Observable<ResponseBody> getCaptcha(@Path("param") String param);

    //注册检查数据是否可用
    @GET("user/check/{param}")
    Observable<ResultBeanData> checkData(@Path("param") String param);

    //用户注册
    @POST("user/doRegister")
    Observable<ResultBeanData> register(@Query("username") String username, @Query("password") String password);

    //用户登录
    @POST("user/doLogin")
    Observable<TokenBeanData> login(@Query("username") String username, @Query("password") String password, @Query("validateCode") String validateCode);

    //通过token查询用户信息
    @GET("user/token/{token}")
    Observable<UserBeanData> getUserInfoByToken(@Path("token") String token);
}
