package com.fmrt.p2p.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.fmrt.p2p.usercenter.bean.UserBeanData;
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
    public void saveUserInfo(UserBeanData.DataBean user) {
        PrefUtils.setString(this, "uuid", user.getUuid());
        PrefUtils.setString(this, "username", user.getUsername());
        PrefUtils.setString(this, "phone", user.getPhone());
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public UserBeanData.DataBean getUserInfo() {
        UserBeanData.DataBean user = new UserBeanData.DataBean();
        user.setUuid(PrefUtils.getString(this, "uuid", ""));
        user.setUsername(PrefUtils.getString(this, "username", ""));
        user.setPhone(PrefUtils.getString(this, "phone", ""));
        return user;
    }


}
