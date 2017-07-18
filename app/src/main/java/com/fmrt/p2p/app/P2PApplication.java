package com.fmrt.p2p.app;

import android.app.Application;
import android.content.Context;

import com.fmrt.p2p.util.CacheProviders;
import com.fmrt.p2p.util.Model;
import com.fmrt.p2p.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 代表整个软件，单例
 */

public class P2PApplication extends Application
{
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;

        //初始化数据模型层全局类
        Model.getInstance().init(this);

        //初始化OkhttpUtils
        initOkhttpClient();

        //初始化ToastUtil
        ToastUtil.getInstance().init(this);

        //初始化缓存CacheProviders
        CacheProviders.getInstance().init(this);

    }

    private void initOkhttpClient()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
