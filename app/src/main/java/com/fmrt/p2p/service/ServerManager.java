package com.fmrt.p2p.service;

import android.content.Context;

import com.fmrt.p2p.common.AppNetConfig;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 服务器，单例模式
 */

public class ServerManager
{
    //定义上下文
    private Context mContext;

    private OkHttpClient mOkHttpClient;

    // 创建对象
    private static ServerManager sServerManager = new ServerManager();

    // 私有化构造
    private ServerManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    // 获取单例对象
    public static ServerManager getInstance()
    {
        return sServerManager;
    }






    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //登录
    public String login(String url, String username, String password, String validateCode)
    {
        String params = "username=" + username + "&password=" + password + "&validateCode=" + validateCode;
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, params);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        // new call
        Call call = mOkHttpClient.newCall(request);
        // 执行请求
        Response response = null;

        try
        {
            response = call.execute();
            return response.body().string();
        } catch (IOException e1)
        {

            e1.printStackTrace();
        }
        return null;
    }

    //注册
    public String regist(String url, String username, String password)
    {
        String params = "username=" + username + "&password=" + password;
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, params);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        // new call
        Call call = mOkHttpClient.newCall(request);
        // 执行请求
        Response response = null;

        try
        {
            response = call.execute();
            return response.body().string();
        } catch (IOException e1)
        {

            e1.printStackTrace();
        }
        return null;
    }
}
