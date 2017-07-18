package com.fmrt.p2p.util;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 网络缓存（单例模式）：Retrofit2.0+OkHttp3.0
 * 缓存策略（一）：无论有无网络我们都先获取缓存的数据
 *
 * 缓存策略（二）：判断网络，有网络，则从网络获取，并保存到缓存中，
 *                           无网络，则从缓存中获取。
 */

public class CacheProviders
{
    //定义上下文
    private Context mContext;

    // 创建对象
    private static CacheProviders cacheProviders = new CacheProviders();
    // 私有化构造
    private CacheProviders() {

    }
    // 获取单例对象
    public static CacheProviders getInstance(){
        return cacheProviders;
    }

    // 初始化的方法
    public void init(Context context){
        mContext = context;
    }

    public OkHttpClient getCacheOKHttpClient(){

        //设置缓存路径，以及缓存大小
        //在响应请求之后在 data/data/<包名>/cache 下建立一个sports 文件夹，保持缓存数据。
        File cacheFile = new File(mContext.getCacheDir(), "sports");
        int cacheSize = 10 * 1024 * 1024;      // 10 MiB
        Cache cache = new Cache(cacheFile, cacheSize);
        /**
         * 一、无论有无网路都添加缓存。
         * 目前的情况是我们这个要addNetworkInterceptor
         * 这样才有效。经过本人测试（chan）测试有效.
         * 60S后如果没有网络将获取不到数据，显示连接失败
         */
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request request = chain.request();
                Response response = chain.proceed(request);
          /*String cacheControl = request.header("Cache-Control");
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=60";
            }*/
                int maxAge = 60;
                return response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
        };
        //把拦截器设置到OkHttp里
        //主要是拦截操作，包括控制缓存的最大生命值，控制缓存的过期时间
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)//连接失败后是否重新连接
                .connectTimeout(15, TimeUnit.SECONDS)//超时时间15S
                .addNetworkInterceptor(cacheInterceptor)//这里大家一定要注意了是addNetworkOnterceptor别搞错了啊。
                .cache(cache)
                .build();
        return client;
    }

}
