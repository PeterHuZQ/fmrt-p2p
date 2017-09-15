package com.fmrt.p2p.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作用：缓存工具类(SharePreference封装)
 * 单例模式
 */

public class PrefUtils
{

    private static PrefUtils instance;
    //定义上下文
    private Context mContext;

    private SharedPreferences sp;


    /**
     * 获取实例
     *
     * @return
     */
    public static PrefUtils getInstance()
    {
        if (instance == null) {
            synchronized (PrefUtils.class) {
                if (instance == null) {
                    instance = new PrefUtils();
                }
            }
        }
        return instance;
    }
    // 初始化的方法
    public void init(Context context)
    {
        mContext = context;
        //获得SharedPreferences对象
        sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    //获取boolean值
    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    //设置boolean值
    public void setBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }
    /**
     * 得到保存的数据
     */
    public String getString( String key) {
        return sp.getString(key,"");
    }

    /**
     * 得到保存的数据
     */
    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }
    /**
     * 保存数据（设置String值）
     */
    public void setString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }
    //获取Int值
    public  int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }
    //设置Int值
    public void setInt( String key, int value) {
        sp.edit().putInt(key, value).commit();
    }
}
