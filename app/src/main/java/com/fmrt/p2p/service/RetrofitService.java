package com.fmrt.p2p.service;

import com.fmrt.p2p.index.bean.AnnouncementListBeanData;
import com.fmrt.p2p.index.bean.ImgListBeanData;
import com.fmrt.p2p.product.bean.CusContractBeanData;
import com.fmrt.p2p.product.bean.CusPerInfoBeanData;
import com.fmrt.p2p.product.bean.RecommendListBeanData;
import com.fmrt.p2p.usercenter.bean.ResultBeanData;
import com.fmrt.p2p.usercenter.bean.TokenBeanData;
import com.fmrt.p2p.usercenter.bean.UserAcctMoyBeanData;
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

    //获取推荐频道列表数据
    @GET("contract/list")
    Observable<RecommendListBeanData> queryContractList(@Query("rows") String rows);

    //通过uuid查询投资详情
    @GET("contract/detail/{uuid}")
    Observable<CusContractBeanData> getCusContractDetailByUuid(@Path("uuid") String uuid);

    //通过uuid查询借款人详情
    @GET("cusperinfo/detail/{uuid}")
    Observable<CusPerInfoBeanData> getCusPerInfoByUuid(@Path("uuid") String uuid);

    //通过userid查询前台用户账号金额信息
    @GET("rest/useracctmoy/{userid}")
    Observable<UserAcctMoyBeanData> getUserAcctMoyByUserid(@Path("userid") String userid);

    //获得首页图片列表
    @GET("index/imglist")
    Observable<ImgListBeanData> getIndexImgList();

    //获得首页置顶公告列表
    @GET("index/announcement")
    Observable<AnnouncementListBeanData> getIndexAnnouncementList();
}
