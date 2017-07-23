package com.fmrt.p2p.usercenter.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.app.MainActivity;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.usercenter.bean.LoginBeanData;
import com.fmrt.p2p.util.AppManager;
import com.fmrt.p2p.util.PrefUtils;

/**
 * 设置Activity
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView mImgBack;
    private TextView tvUserName;
    private TextView tvPhone;
    private TextView bt_user_logout;  //退出登录

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_settings;
    }

    //初始化控件
    @Override
    protected void initView(){
        mImgBack = (ImageView)  findViewById(R.id.img_back);
        tvUserName = (TextView) findViewById(R.id.tvUserName1);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        bt_user_logout = (TextView) findViewById(R.id.bt_user_logout);
    }

    @Override
    public void initData() {
        LoginBeanData.UserBean user=getUserInfo();
        //TODO 显示当前用户名
        tvUserName.setText(user.getUF_ACC());
        //TODO 显示当前用户手机号
        tvPhone.setText(user.getUF_PHONE());
    }

    //初始化监听
    @Override
    protected void initListener(){
        mImgBack.setOnClickListener(this);
        bt_user_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_back:
                //结束当前设置Activity
                closeCurrent();
                break;
            case R.id.bt_user_logout:
                //退出登录
                showLoginOutDialog();
                break;
        }
    }

    private void showLoginOutDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出登录");
        builder.setMessage("你确定要退出吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //退出登录逻辑
                LoginBeanData.UserBean user=new LoginBeanData.UserBean();
                saveUserInfo(user);
                //结束所有Activity
                AppManager.getInstance().removeAll();
                //跳转到主页面
                gotoActivity(MainActivity.class, null);
            }
        });
        builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
