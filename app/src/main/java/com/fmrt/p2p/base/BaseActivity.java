package com.fmrt.p2p.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.fmrt.p2p.usercenter.activity.GestureLockActivity;
import com.fmrt.p2p.usercenter.bean.UserBeanData;
import com.fmrt.p2p.common.AppManager;
import com.fmrt.p2p.util.PrefUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017-07-20.
 */

public abstract class BaseActivity extends FragmentActivity
{
    private static boolean isActive = false;

    public static boolean getIsActive() {
        return isActive;
    }

    public static void setIsActive(boolean isActive) {
        BaseActivity.isActive = isActive;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //指定布局
        setContentView(getLayoutId());
        // 添加Activity到堆栈
        AppManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        //1、初始化控件
        initView();
        //2、初始化数据
        initData();
        //3、初始化监听
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            // 从后台唤醒
            isActive = true;
            if (PrefUtils.getInstance().getString("gesturepwd"+PrefUtils.getInstance().getString("uuid", "")) != null && !PrefUtils.getInstance().getString("gesturepwd"+PrefUtils.getInstance().getString("uuid", "")).equals("")) {

                Bundle bundle = new Bundle();
                bundle.putInt("activityNum", 0);
                //跳转到手势密码解锁界面
                gotoActivity(GestureLockActivity.class, bundle);
            }
            else{
                Bundle bundle = new Bundle();
                bundle.putInt("activityNum", 1);
                //跳转到设置手势密码界面
                gotoActivity(GestureLockActivity.class, bundle);
            }
        }
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

    public Bundle getBundle()
    {
        return getIntent().getBundleExtra("param");
    }

    /**
     * 保存用户登录信息
     */
    public void saveUserInfo(UserBeanData.DataBean user) {
        PrefUtils.getInstance().setString("uuid", user.getUuid());
        PrefUtils.getInstance().setString("username", user.getUsername());
        PrefUtils.getInstance().setString("phone", user.getPhone());
    }

    /**
     * 获取用户登录信息
     *
     * @return
     */
    public UserBeanData.DataBean getUserInfo() {
        UserBeanData.DataBean user = new UserBeanData.DataBean();
        user.setUuid(PrefUtils.getInstance().getString("uuid", ""));
        user.setUsername(PrefUtils.getInstance().getString("username", ""));
        user.setPhone(PrefUtils.getInstance().getString("phone", ""));
        return user;
    }


}
