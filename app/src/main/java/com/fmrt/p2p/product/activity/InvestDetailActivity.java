package com.fmrt.p2p.product.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.product.bean.CusContract;
import com.fmrt.p2p.product.bean.CusContractBeanData;
import com.fmrt.p2p.product.bean.CusPerInfo;
import com.fmrt.p2p.product.bean.CusPerInfoBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.common.AppNetConfig;
import com.fmrt.p2p.util.TimeUtil;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.CustomProcessBar;
import com.fmrt.p2p.widget.InvestScrollView;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.OnClick;
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

import static com.fmrt.p2p.common.AppConstant.CONTRACT_NAME_INVEST;
import static com.fmrt.p2p.common.AppNetConfig.PTP_LOAN_BASE_URL;

/**
 * 理财详情页
 */

public class InvestDetailActivity extends BaseActivity implements View.OnClickListener
{
    private static final String TAG = "InvestDetailActivity";
    @Bind(R.id.img_back)
    ImageView mImgBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.tvRight)
    TextView mTvRight;
    @Bind(R.id.tvSerialNo)
    TextView mTvSerialNo;
    @Bind(R.id.tvYearRate)
    TextView mTvYearRate;
    @Bind(R.id.tvPayMode)
    TextView mTvPayMode;
    @Bind(R.id.tvRemainSum)
    TextView mTvRemainSum;
    @Bind(R.id.tvEndDate)
    TextView mTvEndDate;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.customProcessBar)
    CustomProcessBar mCustomProcessBar;
    @Bind(R.id.llA)
    LinearLayout mLlA;
    @Bind(R.id.tv2)
    TextView mTv2;
    @Bind(R.id.etInvestmoney)
    EditText mEtInvestmoney;
    @Bind(R.id.ibClearNumber)
    ImageButton mIbClearNumber;
    @Bind(R.id.tvEveryMonthEarn)
    TextView mTvEveryMonthEarn;
    @Bind(R.id.llEveryMonthEarn)
    LinearLayout mLlEveryMonthEarn;
    @Bind(R.id.cbAgreeInvest)
    CheckBox mCbAgreeInvest;
    @Bind(R.id.tvContract)
    TextView mTvContract;
    @Bind(R.id.tv01)
    TextView mTv01;
    @Bind(R.id.tvName)
    TextView mTvName;
    @Bind(R.id.tv02)
    TextView mTv02;
    @Bind(R.id.tvIdCard)
    TextView mTvIdCard;
    @Bind(R.id.tv03)
    TextView mTv03;
    @Bind(R.id.tvLoanMoney)
    TextView mTvLoanMoney;
    @Bind(R.id.tv04)
    TextView mTv04;
    @Bind(R.id.tvPhone)
    TextView mTvPhone;
    @Bind(R.id.tv05)
    TextView mTv05;
    @Bind(R.id.tvLiveState)
    TextView mTvLiveState;
    @Bind(R.id.tv06)
    TextView mTv06;
    @Bind(R.id.tvMarray)
    TextView mTvMarray;
    @Bind(R.id.tv07)
    TextView mTv07;
    @Bind(R.id.tvPurpose)
    TextView mTvPurpose;
    @Bind(R.id.llMain)
    LinearLayout mLlMain;
    @Bind(R.id.svNovice)
    InvestScrollView mSvNovice;
    @Bind(R.id.btnDoInvest)
    Button mBtnDoInvest;
    @Bind(R.id.rootView)
    LinearLayout mRootView;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_invest_detail;
    }

    //初始化控件
    @Override
    protected void initView()
    {
        // 赋值
        mTvTitle.setText("投资项目");
        mCustomProcessBar.setLineColor(getResources().getColor(R.color.custom_processBar_line));


    }

    @Override
    public void initData()
    {
        //接收由intent携带过来的数据
        String uuid =  (String) getIntent().getSerializableExtra("cusContractUuid");
        if (!uuid.equals("")) {
            //联网请求资产详情数据
            getCusContractDataByRetrofitAndRxJava(uuid);
        }

    }

    /**
     * 获取资产详情（Retrofit、RxJava）
     */
    public void getCusContractDataByRetrofitAndRxJava(final String uuid)
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(PTP_LOAN_BASE_URL + "/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        final RetrofitService service = retrofit.create(RetrofitService.class);
        //调用“通过uuid查询投资详情”接口：getCusContractDetailByUuid()
        service.getCusContractDetailByUuid(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<CusContractBeanData>()
                {
                    @Override
                    public void accept(CusContractBeanData resultBeanData) throws Exception
                    {
                        //Log.i(TAG, "1: " + Thread.currentThread().getName());
                        // 处理请求login后，打印日志（查看这个接口是否调用成功）
                        Log.i(TAG, "getCusContractDetailByUuid()接口调用成功: " + resultBeanData.getMsg());
                        //设置投资详情
                        setCusContractDetail(resultBeanData);
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<CusContractBeanData, ObservableSource<CusPerInfoBeanData>>()
                {
                    //处理“通过uuid查询投资详情”方法的返回
                    @Override
                    public ObservableSource<CusPerInfoBeanData> apply(@NonNull CusContractBeanData cusContractBeanData) throws Exception
                    {
                        //Log.i(TAG, "2: " + Thread.currentThread().getName());
                        if (cusContractBeanData.getStatus().equals("200"))
                        {
                            //"通过uuid查询投资详情"成功，则调用“通过uuid查询借款人详情”接口
                            Log.i(TAG, "200： " + cusContractBeanData.getMsg());
                            String uuid=cusContractBeanData.getData().getLoanid();
                            return service.getCusPerInfoByUuid(uuid);
                        } else
                        {
                            Log.e(TAG, "系统繁忙:" + cusContractBeanData.getMsg());
                            //抛出异常
                            return Observable.error(new Exception(cusContractBeanData.getMsg()));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CusPerInfoBeanData>()
                {
                    @Override
                    public void accept(CusPerInfoBeanData cusPerInfoBeanData) throws Exception
                    {
                        //Log.i(TAG, "3: " + Thread.currentThread().getName());
                        //设置借款人详情
                        setCusPerInfo(cusPerInfoBeanData);
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
     * 设置投资详情
     * @param cusContractBeanData
     */
    private void setCusContractDetail(CusContractBeanData cusContractBeanData)
    {
        if (cusContractBeanData.getStatus().equals("200"))
        {
            //设置借款人详情
            CusContract cus=cusContractBeanData.getData();
            mTvSerialNo.setText(cus.getDisplayid());
            mTvYearRate.setText(cus.getInvestorrate()+"");
            mTvRemainSum.setText(cus.getResidualamt()+ "");
            mTvEndDate.setText(TimeUtil.longTime2Str(cus.getContrenddt()));
            mTvLoanMoney.setText(cus.getBorramt()+"");
            mTvPurpose.setText(cus.getUsetype());
            mTvContract.setText(CONTRACT_NAME_INVEST);
            //设置募集进度条
            int remainAmt = cus.getResidualamt();
            int loanAmt = cus.getBorramt();
            int progress = 100 * (loanAmt - remainAmt) / loanAmt;
            float x = (float) progress / 100;
            DecimalFormat df = new DecimalFormat("0.00");
            mCustomProcessBar.setProcess(Float.parseFloat(df.format(x)));
        } else
        {
            ToastUtil.getInstance().showToast("系统繁忙", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 设置借款人详情
     * @param cusPerInfoBeanData
     */
    private void setCusPerInfo(CusPerInfoBeanData cusPerInfoBeanData)
    {

        if (cusPerInfoBeanData.getStatus().equals("200"))
        {
            //设置借款人详情
            CusPerInfo per=cusPerInfoBeanData.getData();
            mTvName.setText(per.getCustname());
            mTvIdCard.setText(per.getCertno());
            mTvPhone.setText(per.getMobile());
            mTvLiveState.setText(per.getResidstat());
            mTvMarray.setText(per.getMarriage());
        } else
        {
            ToastUtil.getInstance().showToast("系统繁忙", Toast.LENGTH_SHORT);
        }
    }

    //初始化监听
    @Override
    protected void initListener()
    {
        mEtInvestmoney.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                //设置“预计每月可赚”
                if (s.toString().toString().equals("")){
                    mTvEveryMonthEarn.setText(0 + "");
                }else if (s.toString().toString().equals("0")) {
                    mTvEveryMonthEarn.setText(0 + "");
                }else{
                    makeEveryMonthEarn(Double.parseDouble(mEtInvestmoney.getText().toString()));
                }
            }
        });

    }

    /**
     * 根据本金和年利率计算每月可赚
     * @param investmoney
     */
    private void makeEveryMonthEarn(double investmoney)
    {
        if (mLlEveryMonthEarn.getVisibility() == View.VISIBLE) {
            // 年利率除以12*本金
            try {
                double x = (Double.parseDouble(mTvYearRate.getText().toString()) / 100 * investmoney) / 12;
                mTvEveryMonthEarn.setText(String.format("%.2f", x));
            } catch (Exception e) {

            } finally {

            }
        }
    }

    @OnClick({R.id.img_back, R.id.etInvestmoney, R.id.tvContract, R.id.btnDoInvest})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.img_back:
                //结束当前设置Activity
                closeCurrent();
                break;
            case R.id.etInvestmoney:
                //点击“投资金额”上移一段距离
                int offset1 = mLlA.getMeasuredHeight();
                mSvNovice.scrollTo(0, offset1);
                mSvNovice.smoothScrollTo(0, offset1);
                break;
            case R.id.tvContract:
                break;
            case R.id.btnDoInvest:
                break;
        }

    }


}
