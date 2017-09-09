package com.fmrt.p2p.usercenter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.usercenter.activity.LoginActivity;
import com.fmrt.p2p.usercenter.activity.SettingActivity;
import com.fmrt.p2p.usercenter.activity.UserInfoActivity;
import com.fmrt.p2p.usercenter.activity.WithdrawActivity;
import com.fmrt.p2p.usercenter.adapter.UserCenterFragmentAdapter;
import com.fmrt.p2p.usercenter.bean.UserAcctMoyBeanData;
import com.fmrt.p2p.usercenter.bean.UserBeanData;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.MyGridView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fmrt.p2p.common.AppNetConfig.PTP_USERMANAGE_BASE_URL;

/**
 * 个人中心Fragment
 */

public class UserCenterFragment extends Fragment implements View.OnClickListener
{
    // 用户头像
    private ImageView imgHead;
    //“设置”按钮
    private TextView mTvSetting;
    // “提现”按钮
    private LinearLayout mLLGetMoney;

    //前台用户账号金额信息
    private TextView mTvAcctRemain;    //账户余额
    private TextView mTvInvestRemain;  //投资余额
    private TextView mTvTotalMoney;    //总资产

    private MyGridView gv_user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_usercenter, null);
        //1、初始化控件
        initView(view);
        return view;
    }

    /**
     * 业务逻辑
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //2、初始化数据
        initData();
        //3、初始化监听
        initListener();
    }

    //初始化控件
    private void initView(View view) {
        //用户头像
        imgHead = (ImageView) view.findViewById(R.id.imgHead);
        //设置按钮
        mTvSetting = (TextView) view.findViewById(R.id.tvSetting);
        //提现按钮
        mLLGetMoney =(LinearLayout) view.findViewById(R.id.llGetMoney);

        //前台用户账号金额信息
        mTvAcctRemain= (TextView) view.findViewById(R.id.tvAcctRemain);
        mTvInvestRemain = (TextView) view.findViewById(R.id.tvInvestRemain);
        mTvTotalMoney = (TextView) view.findViewById(R.id.tvTotalMoney);

        /*mTvAcctRemain.setFormat("#,###.00");
        mTvInvestRemain.setFormat("#,###.00");
        mTvTotalMoney.setFormat("#,###.00");*/
    }

    //初始化数据
    private void initData(){
        //创建GridView
        gv_user = (MyGridView) getActivity().findViewById(R.id.gv_user);
        //给GridView设置adapter
        gv_user.setAdapter(new UserCenterFragmentAdapter(getActivity()));

        //判断有没有登录,这里注释掉就不需要登录了
        isLogin();

        UserBeanData.DataBean user = ((BaseActivity) getActivity()).getUserInfo();
        //联网请求数据
        getDataByRetrofitAndRxJava(user.getUuid());
    }

    /**
     *用RxJava和Retrofit获取前台用户账号金额信息
     */
    private void getDataByRetrofitAndRxJava(String userid)
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(PTP_USERMANAGE_BASE_URL + "/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        RetrofitService service = retrofit.create(RetrofitService.class);

        //定义观察者：Observer
        Observer<UserAcctMoyBeanData> observer = new Observer<UserAcctMoyBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull UserAcctMoyBeanData result)
            {
                //设置“前台用户账号金额信息”
                setUserAcctMoy(result);
            }

            @Override
            public void onError(@NonNull Throwable e)
            {
                Log.e("UserCenterFragment", "onError:" + e.getMessage());
            }

            @Override
            public void onComplete()
            {
                Log.e("UserCenterFragment", "onComplete");
            }
        };

        //调用“通过uuid查询投资详情”接口：getCusContractDetailByUuid()
        service.getUserAcctMoyByUserid(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 设置“前台用户账号金额信息”
     * @param userAcctMoyBeanData
     */
    private void setUserAcctMoy(UserAcctMoyBeanData userAcctMoyBeanData)
    {
        if(userAcctMoyBeanData.getStatus().equals("200")){
            UserAcctMoyBeanData.UserAcctMoy userAcctMoy = userAcctMoyBeanData.getData();
            mTvAcctRemain.setText(userAcctMoy.getAcctbal()+"");
            mTvInvestRemain.setText(userAcctMoy.getInvebal()+"");
            mTvTotalMoney.setText(userAcctMoy.getTotal()+"");
        }else
        {
            ToastUtil.getInstance().showToast("系统繁忙", Toast.LENGTH_SHORT);
        }
    }


    //初始化监听
    private void initListener()
    {
        imgHead.setOnClickListener(this);
        mTvSetting.setOnClickListener(this);
        mLLGetMoney.setOnClickListener(this);

        //个人中心九宫格
        gv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 2:
                        ToastUtil.getInstance().showToast( "position" + position, Toast.LENGTH_SHORT);
                        break;
                    case 3:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 4:// 点击我的银行卡
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 5:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 6:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 7:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 8:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;

                }

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.imgHead:
                //ToastUtil.getInstance().showToast( "跳转到个人资料页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(UserInfoActivity.class, null);
                break;
            case R.id.tvSetting:
                //ToastUtil.getInstance().showToast( "跳转到设置页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(SettingActivity.class, null);
                break;
            case R.id.llGetMoney:
                //ToastUtil.getInstance().showToast( "跳转到提现页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(WithdrawActivity.class, null);
                break;
        }
    }

    //判断有没有登录
    private void isLogin()
    {
        UserBeanData.DataBean user = ((BaseActivity) getActivity()).getUserInfo();
        if(TextUtils.isEmpty(user.getUuid())){
            //未登录
            showLoginDialog();
        }else{
            //已登录--处理登录信息
            doUserInfo(user);
        }
    }

    private void showLoginDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("登录");
        builder.setMessage("必须先登录...go...");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ToastUtil.getInstance().showToast( "跳转到登录页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(LoginActivity.class, null);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    private void doUserInfo(UserBeanData.DataBean user)
    {
        //设置用户头像
        Log.e("p2p","用户的UUID"+user.getUuid());
        //transform对图像进行自定义处理
        /*Picasso.with(getActivity()).load(user.getUF_AVATAR_URL()).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                //图片缩放
                Bitmap zoom = BitMapUtil.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //头像圆形裁剪
                Bitmap circleBitMap = BitMapUtil.circleBitMap(zoom);
                //1:transform当中处理完图片之后，需要调用recylce方法回收
                source.recycle();
                return circleBitMap;
            }

            @Override
            public String key() {
                //2:重写key方法的返回值，不能是null
                return "";
            }
        }).into(imgHead);*/

    }
}
