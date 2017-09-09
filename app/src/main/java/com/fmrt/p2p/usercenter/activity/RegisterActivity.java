package com.fmrt.p2p.usercenter.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.usercenter.bean.ResultBeanData;
import com.fmrt.p2p.util.MyTextWatcher;
import com.fmrt.p2p.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import static com.fmrt.p2p.common.AppNetConfig.FMRT_SSO_BASE_URL;

/**
 * 注册Activity
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "RegisterActivity";

    private ImageView mImgBack;
    private TextView mTvTitle;
    private EditText edPhone, edPhoneYZM, edPassword, edTJR;
    private TextView tvSendPhoneYZM;
    private ImageView imgEYE;
    private ImageButton ibClearPhone;
    private ImageButton ibClearYZM;
    private ImageButton ibClearPwd;
    private ImageButton ibClearPhone2;

    private CheckBox cbAgree;
    private TextView tvAgree, tvSubmit;

    private boolean pwdDisplayFlg = false;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_register;
    }

    //初始化控件
    @Override
    protected void initView()
    {
        mImgBack = (ImageView) findViewById(R.id.imgNavBack);
        mTvTitle = (TextView) findViewById(R.id.tvNavTitle);
        mTvTitle.setText("注  册");
        edPhone = (EditText) findViewById(R.id.edPhone);
        edPhoneYZM = (EditText) findViewById(R.id.edPhoneYZM);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edTJR = (EditText) findViewById(R.id.edTRJ);
        //获取验证码
        tvSendPhoneYZM = (TextView) findViewById(R.id.tvSendPhoneYZM);

        imgEYE = (ImageView) findViewById(R.id.imgEYE);

        ibClearPhone = (ImageButton) findViewById(R.id.ibClearPhone);
        ibClearYZM = (ImageButton) findViewById(R.id.ibClearYZM);
        ibClearPwd = (ImageButton) findViewById(R.id.ibClearPwd);
        ibClearPhone2 = (ImageButton) findViewById(R.id.ibClearPhone2);

        cbAgree = (CheckBox) findViewById(R.id.cbAgree);
        tvAgree = (TextView) findViewById(R.id.tvAgree);
        tvSubmit = (TextView) findViewById(R.id.tvSubmit);

    }

    @Override
    public void initData()
    {
    }

    //初始化监听
    @Override
    protected void initListener()
    {
        mImgBack.setOnClickListener(this);

        imgEYE.setOnClickListener(this);

        //输入内容出现清空按钮
        edPhone.addTextChangedListener(new MyTextWatcher(ibClearPhone));
        edPhoneYZM.addTextChangedListener(new MyTextWatcher(ibClearYZM));
        edPassword.addTextChangedListener(new MyTextWatcher(ibClearPwd));
        edTJR.addTextChangedListener(new MyTextWatcher(ibClearPhone2));

        tvSendPhoneYZM.setOnClickListener(this);

        ibClearPhone.setOnClickListener(this);
        ibClearYZM.setOnClickListener(this);
        ibClearPwd.setOnClickListener(this);
        ibClearPhone2.setOnClickListener(this);

        // 用户须知点击事件处理
        tvAgree.setOnClickListener(this);
        // 注册按钮的点击事件处理
        tvSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imgNavBack:
                //结束当前登录Activity
                closeCurrent();
                break;
            case R.id.tvSendPhoneYZM:
                if (edPhone.getText().toString().length() == 0)
                {
                    ToastUtil.getInstance().showToast("请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                } else if (edPhone.getText().toString().length() != 11)
                {
                    ToastUtil.getInstance().showToast("手机号码格式不正确", Toast.LENGTH_SHORT);
                    return;
                } else
                {
                    ToastUtil.getInstance().showToast("短信验证码已发送至手机", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.imgEYE:
                //掩藏显示登录密码
                if (!pwdDisplayFlg)
                {
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imgEYE.setImageResource(R.drawable.dis);
                } else
                {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    imgEYE.setImageResource(R.drawable.hide);
                }
                pwdDisplayFlg = !pwdDisplayFlg;
                edPassword.postInvalidate();
                break;
            case R.id.ibClearPhone:
                edPhone.setText("");
                break;
            case R.id.ibClearYZM:
                edPhoneYZM.setText("");
                break;
            case R.id.ibClearPwd:
                edPassword.setText("");
                break;
            case R.id.ibClearPhone2:
                edTJR.setText("");
                break;
            case R.id.tvSubmit:
                regist();
                break;


        }

    }

    // 注册按钮的页面逻辑处理
    private void regist()
    {

        //1 获取输入的手机号,登录密码
        final String registerName = edPhone.getText().toString();
        final String registerPwd = edPassword.getText().toString();

        // 2 校验
        if (edPhone.getText().toString().length() != 11)
        {
            ToastUtil.getInstance().showToast("手机号码错误", Toast.LENGTH_SHORT);
            return;
        }
        if (edPhoneYZM.getText().toString().length() != 6)
        {
            ToastUtil.getInstance().showToast("短信验证码错误", Toast.LENGTH_SHORT);
            return;
        }
        if (edPassword.getText().toString().length() == 0)
        {
            ToastUtil.getInstance().showToast("请输入登录密码", Toast.LENGTH_SHORT);
            return;
        }
        if (edPassword.getText().toString().length() < 6)
        {
            ToastUtil.getInstance().showToast("登录密码错误", Toast.LENGTH_SHORT);
            return;
        }
        if (edTJR.getText().toString().length() != 0 && edTJR.getText().toString().length() != 11)
        {
            ToastUtil.getInstance().showToast("推荐人号码错误", Toast.LENGTH_SHORT);
            return;
        }
        if (!cbAgree.isChecked())
        {
            ToastUtil.getInstance().showToast("请先同意用户协议", Toast.LENGTH_SHORT);
            return;
        }
        // 3 向后台服务器发送注册请求
        RegisterByRetrofitAndRxJava(registerName, registerPwd);
    }

    /**
     * 注册检查数据是否可用、用户注册（Retrofit、RxJava）
     */
    public void RegisterByRetrofitAndRxJava(final String name, final String password)
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(FMRT_SSO_BASE_URL + "/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        final RetrofitService service = retrofit.create(RetrofitService.class);
        //调用“检测数据是否可用”方法
        service.checkData(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResultBeanData>()
                {
                    @Override
                    public void accept(ResultBeanData resultBeanData) throws Exception
                    {
                        //Log.i(TAG, "1: " + Thread.currentThread().getName());
                        // 处理请求checkData后，打印日志（查看这个接口是否调用成功）
                        Log.i(TAG, "checkData接口调用成功: " + resultBeanData.getMsg());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResultBeanData, ObservableSource<ResultBeanData>>()
                {
                    //处理“检测数据是否可用”方法的返回
                    @Override
                    public ObservableSource<ResultBeanData> apply(@NonNull ResultBeanData checkBeanData) throws Exception
                    {
                        //Log.i(TAG, "2: " + Thread.currentThread().getName());
                        if (checkBeanData.getStatus().equals("4001"))
                        {
                            //“手机号已被注册”
                            Log.e("RegisterActivity", "4001:" + checkBeanData.getMsg());
                            //抛出异常
                            return Observable.error(new Exception(checkBeanData.getMsg()));
                        } else if (checkBeanData.getStatus().equals("200"))
                        {
                            //检测通过，则调用“用户注册”接口
                            Log.i("RegisterActivity", "200： " + checkBeanData.getMsg());
                            return service.register(name, password);
                        } else
                        {
                            Log.e("RegisterActivity", "系统繁忙:" + checkBeanData.getMsg());
                            //抛出异常
                            return Observable.error(new Exception(checkBeanData.getMsg()));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultBeanData>()
                {
                    @Override
                    public void accept(ResultBeanData registerBeanData) throws Exception
                    {
                        //Log.i(TAG, "3: " + Thread.currentThread().getName());
                        if (registerBeanData.getStatus().equals("200"))
                        {
                            ToastUtil.getInstance().showToast("注册成功", Toast.LENGTH_SHORT);
                            //跳转到登录页面
                            gotoActivity(LoginActivity.class, null);
                            //结束当前登录Activity
                            closeCurrent();
                        } else if (registerBeanData.getStatus().equals("4001"))
                        {
                            ToastUtil.getInstance().showToast("手机号已被注册", Toast.LENGTH_SHORT);
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
                        Log.e("RegisterActivity", "throwable:" + throwable.getMessage());
                        ToastUtil.getInstance().showToast("throwable:" +  throwable.getMessage(), Toast.LENGTH_SHORT);
                    }
                });
    }
}
