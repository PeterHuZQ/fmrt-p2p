package com.fmrt.p2p.util;

import android.content.Context;

import com.fmrt.p2p.app.P2PApplication;

/**
 * dp和px相互转换工具
 */

public class DensityUtil
{
    public static Context getContext() {
        return P2PApplication.mContext;
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
