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



}
