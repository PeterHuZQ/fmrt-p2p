package com.fmrt.p2p.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.fmrt.p2p.usercenter.activity.LoginActivity;
import com.fmrt.p2p.usercenter.bean.LoginBeanData;
import com.fmrt.p2p.util.AppManager;
import com.fmrt.p2p.util.PrefUtils;

/**
 * Created by Administrator on 2017-07-20.
 */

public abstract class BaseActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //指定布局
        setContentView(getLayoutId());
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);
        //1、初始化控件
        initView();
        //2、初始化数据
        initData();
        //3、初始化监听
        initListener();
    }



    protected abstract int getLayoutId();

    protected abstract void initView();

    public abstract void initData();

    protected abstract void initListener();

    /**
     * 关闭当前activity
     */
    public void closeCurrent() {
        AppManager.getInstance().removeCurrent();
    }


    /**
     * 跳转到目标activity
     *
     * @param clazz
     * @param bundle
     */
    public void gotoActivity(Class clazz, Bundle bundle) {
        Intent it = new Intent(this, clazz);
        if (bundle != null) {
            it.putExtra("param", bundle);
        }
        startActivity(it);
    }

    /**
     * 保存用户登录信息
     */
    public void saveUserInfo(LoginBeanData.UserBean user) {
        PrefUtils.setString(this, "UF_ACC", user.getUF_ACC());
        PrefUtils.setString(this, "UF_AVATAR_URL", user.getUF_AVATAR_URL());
        PrefUtils.setString(this, "UF_IS_CERT", user.getUF_IS_CERT());
        PrefUtils.setString(this, "UF_PHONE", user.getUF_PHONE());
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public LoginBeanData.UserBean getUserInfo() {
        LoginBeanData.UserBean user = new LoginBeanData.UserBean();
        user.setUF_ACC(PrefUtils.getString(this, "UF_ACC", ""));
        user.setUF_AVATAR_URL(PrefUtils.getString(this, "UF_AVATAR_URL", ""));
        user.setUF_IS_CERT(PrefUtils.getString(this, "UF_IS_CERT", ""));
        user.setUF_PHONE(PrefUtils.getString(this, "UF_PHONE", ""));
        return user;
    }


}
