package com.fmrt.p2p.util;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.fmrt.p2p.app.P2PApplication;

/**
 * UI相关的问题而创造的工具类
 * （1）dp和px相互转换工具
 */

public class UIUtils
{
    public static Context getContext() {
        return P2PApplication.mContext;
    }

    public static Handler getHandler() {
        return P2PApplication.handler;
    }

    /**
     * 保证runnable对象的run方法是运行在主线程当中
     * @param runnable
     */
    public static void runOnUIThread(Runnable runnable) {
        if (isInMainThread()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    private static boolean isInMainThread() {
        //当前线程的id
        int tid = android.os.Process.myTid();
        if (tid == P2PApplication.mainThreadId) {
            return true;
        }
        return false;
    }

    /**
     * 创建Fragment时可以简化View.inflate
     * @param layoutId
     * @return
     */
    public static View getXmlView(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    public static int getColor(int colorId) {
        return getContext().getResources().getColor(colorId);
    }

    public static String[] getStringArr(int arrId) {
        return getContext().getResources().getStringArray(arrId);
    }

    public static void Toast(String text, boolean isLong) {
        Toast.makeText(getContext(), text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     */
    public static int dp2px(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }
}
