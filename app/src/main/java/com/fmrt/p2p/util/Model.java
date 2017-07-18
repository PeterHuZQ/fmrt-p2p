package com.fmrt.p2p.util;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据模型层全局类,这个类是单例模式
 */

public class Model
{
    //定义上下文
    private Context mContext;

    // 创建对象
    private static Model model = new Model();

    //获取线程池
    private ExecutorService executors = Executors.newCachedThreadPool();

    // 私有化构造
    private Model() {

    }

    // 获取单例对象
    public static Model getInstance(){
        return model;
    }

    // 初始化的方法
    public void init(Context context){
        mContext = context;
    }

    // 获取全局线程池对象
    public ExecutorService getGlobalThreadPool(){
        return executors;
    }

    //用户登录成功后的处理方法
    public void loginSuccess() {

    }
}
