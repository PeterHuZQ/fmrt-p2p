package com.fmrt.p2p.common;

import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 全局异常捕获器：CrashHandler
 * 处理程序中未捕获崩溃的异常
 * 设计为单例模式
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler
{
    private Context mContext;

    //默认异常处理器
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    private static CrashHandler crashHandler = null;

    private CrashHandler() {

    }
    //获取单例对象
    public static CrashHandler getInstance() {
        if (crashHandler == null) {
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置系统的默认异常处理器，将CrashHandler作为系统的默认异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 把这个提示信息汉化，记录一下日志信息，反馈到后台
     * @param thread
     * @param ex
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //判断是否需要自己处理
        if(isHandle(ex)){
            //自定义异常处理
            handleException(thread, ex);
        }else{
            //系统默认异常处理器进行处理
            defaultUncaughtExceptionHandler.uncaughtException(thread,ex);
        }

    }

    /**
     * 自定义异常处理
     * @param thread
     * @param ex
     */
    private void handleException(Thread thread, Throwable ex) {
        new Thread() {
            @Override
            public void run() {
                //Android系统当中，默认情况下，线程是没有开启looper消息处理的，但是主线程除外
                Looper.prepare();
                Toast.makeText(mContext, "抱歉，系统出现未知异常，即将退出....", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        //收集一下崩溃异常信息
        collectionException(ex);

        //2秒钟后再把程序关闭
        try {
            thread.sleep(2000);
            //结束所有Activity
            AppManager.getInstance().removeAll();
            //杀进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //关闭虚拟机，释放所有内存
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收集一下崩溃异常信息
     *
     * @param ex
     */
    private void collectionException(Throwable ex) {
        //设备信息
        final String deviceInfo = Build.DEVICE + Build.VERSION.SDK_INT + Build.MODEL + Build.PRODUCT;
        final String errorInfo = ex.getMessage();
        new Thread() {
            @Override
            public void run() {
                Log.e("p2p", "deviceInfo---" + deviceInfo + ":errorInfo" + errorInfo);
            }
        }.start();
    }

    /**
     * 判断是否需要自己处理
     * @param ex
     * @return
     */
    public boolean isHandle(Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            return true;
        }
    }

}
