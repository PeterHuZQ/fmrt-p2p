package com.fmrt.p2p.usercenter.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.R;
import com.fmrt.p2p.app.MainActivity;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.service.ServerManager;
import com.fmrt.p2p.usercenter.bean.ResultBeanData;
import com.fmrt.p2p.usercenter.bean.TokenBeanData;
import com.fmrt.p2p.usercenter.bean.UserBeanData;
import com.fmrt.p2p.util.AppConstants;
import com.fmrt.p2p.util.MyTextWatcher;
import com.fmrt.p2p.util.RandomUtil;
import com.fmrt.p2p.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fmrt.p2p.util.AppConstants.BASE_URL;

/**
 * 登录Activity
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener
{
    private ImageView img_back;

    private EditText et_login_name;

    private EditText et_login_pwd;

    private ImageButton ibClearPwd, ibClearAct;

    private TextView bt_login_regist;

    private EditText edYZM;
    private ImageButton ibClearYZM;
    private ImageView imgYZM;

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

        edYZM = (EditText) findViewById(R.id.edYZM);
        ibClearYZM = (ImageButton) findViewById(R.id.ibClearYZM);
        imgYZM = (ImageView) findViewById(R.id.imgYZM);
        bt_login_login = (Button) findViewById(R.id.bt_login_login);
        bt_login_regist = (TextView) findViewById(R.id.bt_login_regist);


    }

    @Override
    public void initData()
    {
        //下载图形验证码
        getCaptchaByRetrofitAndRxJava();
    }


    //初始化监听
    @Override
    protected void initListener()
    {
        img_back.setOnClickListener(this);
        ibClearAct.setOnClickListener(this);
        ibClearPwd.setOnClickListener(this);
        ibClearYZM.setOnClickListener(this);
        //  输入内容出现清空按钮
        edYZM.addTextChangedListener(new MyTextWatcher(ibClearYZM));
        // 点击图形验证码更换处理
        imgYZM.setOnClickListener(this);
        // 登录按钮的点击事件处理
        bt_login_login.setOnClickListener(this);
        // 注册按钮的点击事件处理
        bt_login_regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
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
            case R.id.imgYZM:
                //下载图形验证码
                getCaptchaByRetrofitAndRxJava();
                break;
            case R.id.ibClearYZM:
                edYZM.setText("");
                break;
            case R.id.bt_login_login:
                login();
                break;
            case R.id.bt_login_regist:
                gotoActivity(RegisterActivity.class, null);
                break;
        }

    }

    // 登录按钮的页面逻辑处理
    private void login()
    {

        //1 获取输入的手机号、登录密码、图形验证码
        final String loginName = et_login_name.getText().toString();
        final String loginPwd = et_login_pwd.getText().toString();
        final String validateCode = edYZM.getText().toString();
        // 2 校验输入的用户名（手机号）和密码
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd) || TextUtils.isEmpty(validateCode))
        {
            ToastUtil.getInstance().showToast("输入的用户名、密码、验证码不能为空", Toast.LENGTH_SHORT);
            return;
        }
        // 3 向后台服务器发送登录请求
        // new LoginTask(loginName, loginPwd, validateCode).execute();
        // LoginByRxJava(loginName, loginPwd, validateCode);
        LoginByRetrofitAndRxJava(loginName, loginPwd,validateCode);
    }

    /**
     * 用户登录、通过token查询用户信息（Retrofit、RxJava）
     */
    public void LoginByRetrofitAndRxJava(final String name, final String password,final String validateCode)
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL + "/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        final RetrofitService service = retrofit.create(RetrofitService.class);
        //调用“用户登录”方法
        service.login(name,password,validateCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<TokenBeanData>()
                {
                    @Override
                    public void accept(TokenBeanData resultBeanData) throws Exception
                    {
                        //Log.i(TAG, "1: " + Thread.currentThread().getName());
                        // 处理请求login后，打印日志（查看这个接口是否调用成功）
                        //Log.i(TAG, "checkData接口调用成功: " + resultBeanData.getMsg());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<TokenBeanData, ObservableSource<UserBeanData>>()
                {
                    //处理“用户登录”方法的返回
                    @Override
                    public ObservableSource<UserBeanData> apply(@NonNull TokenBeanData loginBeanData) throws Exception
                    {
                        //Log.i(TAG, "2: " + Thread.currentThread().getName());
                        if (loginBeanData.getStatus().equals("400"))
                        {
                            //“用户名或密码错误”
                            Log.e("LoginActivity", "400:" + loginBeanData.getMsg());
                            //抛出异常
                            return Observable.error(new Exception(loginBeanData.getMsg()));
                        } else if (loginBeanData.getStatus().equals("200"))
                        {
                            //登录成功，则调用“通过token查询用户信息”接口
                            Log.i("LoginActivity", "200： " + loginBeanData.getMsg());
                            return service.getUserInfoByToken(loginBeanData.getData().toString());
                        } else
                        {
                            Log.e("LoginActivity", "系统繁忙:" + loginBeanData.getMsg());
                            //抛出异常
                            return Observable.error(new Exception(loginBeanData.getMsg()));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBeanData>()
                {
                    @Override
                    public void accept(UserBeanData tokenBeanData) throws Exception
                    {
                        //Log.i(TAG, "3: " + Thread.currentThread().getName());
                        if (tokenBeanData.getStatus().equals("200"))
                        {
                            //TODO 保存用户账号信息到SharePreference
                            UserBeanData.DataBean user = tokenBeanData.getData();
                            saveUserInfo(user);
                            ToastUtil.getInstance().showToast("通过token查询用户信息成功", Toast.LENGTH_SHORT);
                            //跳转到主页面
                            gotoActivity(MainActivity.class, null);
                            //结束当前登录Activity
                            closeCurrent();
                        } else
                        {
                            ToastUtil.getInstance().showToast("系统繁忙", Toast.LENGTH_SHORT);
                        }
                    }
                }, new Consumer<Throwable>()
                {
                    @Override
                    public void accept(Throwable throwable) throws Exception
                    {
                        //Log.i(TAG, "4: " + Thread.currentThread().getName());
                        //捕获异常处理
                        Log.e("LoginActivity", "throwable:" + throwable.getMessage());
                        ToastUtil.getInstance().showToast("throwable:" +  throwable.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }



    /**
     * 用RxJava处理登录请求
     * @param name
     * @param password
     * @param validateCode
     */
    public void LoginByRxJava(final String name, final String password, final String validateCode)
    {
        Observable.create(new ObservableOnSubscribe<String>()
        {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception
            {
                String url = AppConstants.LOGINACTIVITY_LOGIN_URL;
                String ret = ServerManager.getInstance().login(url, name, password, validateCode);
                e.onNext(ret);


                Log.d("LoginActivity", "1.thread: " + Thread.currentThread().getName());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>()
                {
                    @Override
                    public void accept(String s) throws Exception
                    {
                        Log.d("LoginActivity", "2.thread: " + Thread.currentThread().getName());
                        if (s == null)
                        {

                        } else
                        {
                            ResultBeanData loginBeanData = JSON.parseObject(s, ResultBeanData.class);
                            if (loginBeanData.getStatus().equals("200"))
                            {
                                //LoginBeanData.UserBean user=loginBeanData.getData();
                                Log.e("p2p", "解析成功==");
                                //TODO 保存用户账号信息到SharePreference
                                //saveUserInfo(user);
                                ToastUtil.getInstance().showToast("登录成功", Toast.LENGTH_SHORT);
                                //跳转到主页面
                                gotoActivity(MainActivity.class, null);
                                //结束当前登录Activity
                                closeCurrent();
                            } else if (loginBeanData.getStatus().equals("3001"))
                            {
                                ToastUtil.getInstance().showToast("验证验证码失败", Toast.LENGTH_SHORT);
                            } else
                            {
                                ToastUtil.getInstance().showToast("用户名或者密码错误", Toast.LENGTH_SHORT);
                            }
                        }
                    }
                });
//                .subscribe(new Observer<String>()
//                {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d)
//                    {
//
//                    }
//
//                    @Override
//                    public void onNext(@NonNull String s)
//                    {
//                        Log.d("LoginActivity", "2.thread: " + Thread.currentThread().getName());
//                        if (s == null)
//                        {
//
//                        } else
//                        {
//                            ResultBeanData loginBeanData = JSON.parseObject(s, ResultBeanData.class);
//                            if (loginBeanData.getStatus().equals("200"))
//                            {
//                                //LoginBeanData.UserBean user=loginBeanData.getData();
//                                Log.e("p2p", "解析成功==");
//                                //TODO 保存用户账号信息到SharePreference
//                                //saveUserInfo(user);
//                                ToastUtil.getInstance().showToast("登录成功", Toast.LENGTH_SHORT);
//                                //跳转到主页面
//                                gotoActivity(MainActivity.class, null);
//                                //结束当前登录Activity
//                                closeCurrent();
//                            } else if (loginBeanData.getStatus().equals("3001"))
//                            {
//                                ToastUtil.getInstance().showToast("验证验证码失败", Toast.LENGTH_SHORT);
//                            } else
//                            {
//                                ToastUtil.getInstance().showToast("用户名或者密码错误", Toast.LENGTH_SHORT);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e)
//                    {
//                        Log.e("LoginActivity", "onError");
//                    }
//
//                    @Override
//                    public void onComplete()
//                    {
//                        Log.d("LoginActivity", "onComplete");
//                    }
//                });
    }

    class LoginTask extends AsyncTask<String, Void, String>
    {
        private String name;
        private String password;
        private String validateCode;

        public LoginTask(String name, String passsword, String validateCode)
        {
            this.name = name;
            this.password = passsword;
            this.validateCode = validateCode;
        }

        @Override
        protected String doInBackground(String... strings)
        {
            String url = AppConstants.LOGINACTIVITY_LOGIN_URL;
            String ret = ServerManager.getInstance().login(url, name, password, validateCode);
            return ret;
        }

        @Override
        protected void onPostExecute(String ret)
        {
            super.onPostExecute(ret);
            //解析数据
            if (ret == null)
            {

            } else
            {
                ResultBeanData loginBeanData = JSON.parseObject(ret, ResultBeanData.class);
                if (loginBeanData.getStatus().equals("200"))
                {
                    //LoginBeanData.UserBean user=loginBeanData.getData();
                    Log.e("p2p", "解析成功==");
                    //TODO 保存用户账号信息到SharePreference
                    //saveUserInfo(user);
                    ToastUtil.getInstance().showToast("登录成功", Toast.LENGTH_SHORT);
                    //跳转到主页面
                    gotoActivity(MainActivity.class, null);
                    //结束当前登录Activity
                    closeCurrent();
                } else if (loginBeanData.getStatus().equals("3001"))
                {
                    ToastUtil.getInstance().showToast("验证验证码失败", Toast.LENGTH_SHORT);
                } else
                {
                    ToastUtil.getInstance().showToast("用户名或者密码错误", Toast.LENGTH_SHORT);
                }
            }
        }
    }

    /**
     * 下载图形验证码（Retrofit、RxJava）
     */
    public void getCaptchaByRetrofitAndRxJava()
    {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL+"/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        RetrofitService service = retrofit.create(RetrofitService.class);

        //定义观察者：Observer
        Observer<Bitmap> observer = new Observer<Bitmap>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull Bitmap bitmap)
            {
                imgYZM.setImageBitmap(bitmap);
                imgYZM.setClickable(true);
            }

            @Override
            public void onError(@NonNull Throwable e)
            {
                Log.e("LoginActivity", "onError:"+e.getMessage());
            }

            @Override
            public void onComplete()
            {
                Log.e("LoginActivity", "onComplete");
            }
        };

        //调用getCaptcha()获取图形验证码方法
        service.getCaptcha(RandomUtil.getSuijiStr())
                .map(new Function<ResponseBody, Bitmap>()
                {
                    @Override
                    public Bitmap apply(@NonNull ResponseBody responseBody) throws Exception
                    {
                        return BitmapFactory.decodeStream(responseBody.byteStream());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
