package com.fmrt.p2p.usercenter.activity;


import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.R;
import com.fmrt.p2p.app.MainActivity;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.usercenter.bean.LoginBeanData;
import com.fmrt.p2p.util.AppConstants;
import com.fmrt.p2p.util.MD5Utils;
import com.fmrt.p2p.util.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 登录Activity
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView img_back;

    private EditText et_login_name;

    private EditText et_login_pwd;

    private ImageButton ibClearPwd, ibClearAct;

    private Button bt_login_regist;

    private Button bt_login_login;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_login;
    }

    //初始化控件
    @Override
    protected void initView()
    {
        img_back = (ImageView) findViewById(R.id.img_back);
        et_login_name = (EditText) findViewById(R.id.et_login_name);
        et_login_pwd = (EditText) findViewById(R.id.et_login_pwd);
        ibClearAct = (ImageButton) findViewById(R.id.ibClearAct);
        ibClearPwd = (ImageButton) findViewById(R.id.ibClearPwd);
        bt_login_regist = (Button) findViewById(R.id.bt_login_regist);
        bt_login_login = (Button) findViewById(R.id.bt_login_login);
    }

    @Override
    public void initData() {
    }

    //初始化监听
    @Override
    protected void initListener()
    {
        img_back.setOnClickListener(this);
        ibClearAct.setOnClickListener(this);
        ibClearPwd.setOnClickListener(this);

        // 登录按钮的点击事件处理
        bt_login_login.setOnClickListener(this);
        // 注册按钮的点击事件处理
        bt_login_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.img_back:
                //结束当前登录Activity
                closeCurrent();
                break;

            case R.id.ibClearAct:
                et_login_name.setText("");
                break;

            case R.id.ibClearPwd:
                et_login_pwd.setText("");
                break;

            case R.id.bt_login_login:
                login();
                break;

            case R.id.bt_login_regist:
                //regist();
                break;
        }

    }

    // 登录按钮的页面逻辑处理
    private void login() {

        //1 获取输入的用户名和密码
        final String loginName = et_login_name.getText().toString();
        final String loginPwd = et_login_pwd.getText().toString();

        // 2 校验输入的用户名和密码
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd))
        {
            ToastUtil.getInstance().showToast( "输入的用户名或密码不能为空",Toast.LENGTH_SHORT);
            return;
        }
        // 3 向后台服务器发送登录请求
        ServerManager_login(loginName,loginPwd);
    }

    private void ServerManager_login(String username,String password)
    {
        String url = AppConstants.LOGINACTIVITY_LOGIN_URL;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", username)
                .addParams("password", MD5Utils.MD5(password))
                .build()
                .execute(new StringCallback()
                {
                    /**
                     * 当请求失败的时候回调
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response 请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id)
                    {
                        //解析数据
                        LoginBeanData loginBeanData = JSON.parseObject(response,LoginBeanData.class);
                        if(loginBeanData.isSuccess()){
                            LoginBeanData.UserBean user=loginBeanData.getData();
                            Log.e("p2p","解析成功=="+user.getUF_ACC());
                            //TODO 保存用户账号信息到SharePreference
                            saveUserInfo(user);
                            ToastUtil.getInstance().showToast( "登录成功",Toast.LENGTH_SHORT);
                            //跳转到主页面
                            gotoActivity(MainActivity.class, null);
                            //结束当前登录Activity
                            closeCurrent();
                        }else{
                            ToastUtil.getInstance().showToast( "用户名或者密码错误",Toast.LENGTH_SHORT);
                        }
                    }
                });

    }
}
