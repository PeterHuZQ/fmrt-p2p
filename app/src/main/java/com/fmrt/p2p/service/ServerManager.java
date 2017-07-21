package com.fmrt.p2p.service;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.usercenter.bean.LoginBeanData;
import com.fmrt.p2p.util.AppConstants;
import com.fmrt.p2p.util.MD5Utils;
import com.fmrt.p2p.util.PrefUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;




/**
 * 服务器，单例模式
 */

public class ServerManager
{
    //定义上下文
    private Context mContext;

    // 创建对象
    private static ServerManager sServerManager = new ServerManager();

    // 私有化构造
    private ServerManager() {

    }

    // 获取单例对象
    public static ServerManager getInstance(){
        return sServerManager;
    }

    //获取首页页面数据
    public String getIndexData() throws IOException{
        String url = AppConstants.INDEX_URL;
        Response response=OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute();
        return response.body().string();
    }

    //获取投资列表数据
    public String getInvestData() throws IOException{
        String url = AppConstants.INVEST_URL;
        Response response=OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute();
        return response.body().string();
    }

    //登录
    public void login(String username,String password,final Context context){
        String url = AppConstants.LOGINACTIVITY_LOGIN_URL;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", username)
                .addParams("password", MD5Utils.MD5(password))
                .build()
                .execute(new StringCallback()
                {
                    /**
                     * 当请求失败的时候回调
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id)
                    {
                        //解析数据
                        LoginBeanData loginBeanData = JSON.parseObject(response,LoginBeanData.class);
                        if(loginBeanData.isSuccess()){
                            LoginBeanData.UserBean user=loginBeanData.getData();
                            Log.e("p2p","解析成功=="+user.getUF_ACC());
                            //TODO 保存用户账号信息到SharePreference
                            PrefUtils.setString(context, "username", user.getUF_ACC());
                            PrefUtils.setString(context, "phonenum", user.getUF_PHONE());
                        }else{
                            Log.e("p2p", "登录失败,用户名或者密码错误！");
                        }
                    }
                });
    }

}
