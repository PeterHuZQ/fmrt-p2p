package com.fmrt.p2p.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.fmrt.p2p.common.CrashHandler;
import com.fmrt.p2p.util.CacheProviders;
import com.fmrt.p2p.util.Model;
import com.fmrt.p2p.util.PrefUtils;
import com.fmrt.p2p.util.ToastUtil;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * 代表整个软件，单例
 */

public class P2PApplication extends Application
{
    public static Context mContext;

    public static Handler handler = null;

    public static Thread mainThread = null;

    public static int mainThreadId = 0;

    //友盟相关初始化
    {
        PlatformConfig.setWeixin("wx310519db15733fea", "1");
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //mContext = getApplicationContext();
        mContext=this;
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = android.os.Process.myTid();

        //初始化ToastUtil
        ToastUtil.getInstance().init(this);

        //初始化默认异常处理器：CrashHandler
//        CrashHandler.getInstance().init(this);

        //初始化SharePreference工具类
        PrefUtils.getInstance().init(this);

        //初始化数据模型层全局类
        Model.getInstance().init(this);

        //初始化OkhttpUtils
        initOkhttpClient();

        //初始化缓存CacheProviders
        CacheProviders.getInstance().init(this);

        //友盟初始化
        initUMeng();

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


    private void initUMeng() {
        //友盟分享初始化
        UMShareAPI.get(this);
    }
}
