package com.fmrt.p2p.index;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.index.adapter.BannerAdapter;
import com.fmrt.p2p.index.bean.ImgListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.LoadingPage;
import com.fmrt.p2p.widget.MyScrollView;
import com.fmrt.p2p.widget.RippleProgress;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fmrt.p2p.app.P2PApplication.mContext;
import static com.fmrt.p2p.common.AppNetConfig.PTP_LOAN_BASE_URL;


/**
 * 首页Fragment
 */

public class IndexFragment extends BaseFragment
{
    //公共头布局
    @Bind(R.id.iv_left)
    ImageView iv_left;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.iv_right)
    ImageView iv_right;
    //横幅广播Banner
    @Bind(R.id.vp_barner)
    ViewPager vpBarner;
    @Bind(R.id.circle_barner)
    CirclePageIndicator circleBarner;
    @Bind(R.id.textView1)
    TextView mTextView1;
    //水波纹进度球
    @Bind(R.id.ripple_progress)
    RippleProgress mRippleProgress;
    @Bind(R.id.p_yearlv)
    TextView mPYearlv;
    @Bind(R.id.button1)
    Button mButton1;
    @Bind(R.id.myscrollview)
    MyScrollView mMyscrollview;
    @Bind(R.id.loadingPage)
    LoadingPage mLoadingPage;


    private BannerAdapter adapter;

    //返回的数据
    private List<ImgListBeanData.BannerInfo> bannerInfo_list;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_index;
    }

    @Override
    protected void initView()
    {
        //初始化头布局
        initTitle();

        //开始波纹
        mRippleProgress.startWave();
    }

    private void initTitle()
    {
        iv_left.setVisibility(View.INVISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData()
    {
        Log.e("p2p", "首页IndexFragment的数据被初始化了。。。");
        //联网请求首页的数据
        getDataByRetrofitAndRxJava();
    }

    /**
     * 用RxJava和Retrofit获取首页图片列表
     */
    private void getDataByRetrofitAndRxJava()
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
        RetrofitService service = retrofit.create(RetrofitService.class);

        //定义观察者：Observer
        Observer<ImgListBeanData> observer = new Observer<ImgListBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {
            }

            @Override
            public void onNext(@NonNull ImgListBeanData result)
            {
                //解析数据
                processData(result);
            }

            @Override
            public void onError(@NonNull Throwable e)
            {
                mLoadingPage.showPage(AppConstant.PAGE_ERROR_STATE);
            }

            @Override
            public void onComplete()
            {
                mLoadingPage.setVisibility(View.GONE);
            }
        };

        //调用“获得首页图片列表”接口：getIndexImgList()
        service.getIndexImgList()
                .doOnSubscribe(new Consumer<Disposable>()
                {
                    //doOnSubscribe用于在call之前执行一些初始化操作
                    @Override
                    public void accept(Disposable disposable) throws Exception
                    {
                        //展示“//正在加载”
                        mLoadingPage.showPage(AppConstant.PAGE_LOADING_STATE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void processData(ImgListBeanData imgListBeanData)
    {
        if (imgListBeanData.getStatus().equals("200"))
        {
            bannerInfo_list = imgListBeanData.getRows();
            if (bannerInfo_list != null)
            {   //有数据
                //创建横幅广播Banner的适配器
                adapter = new BannerAdapter(mContext, bannerInfo_list);
                //为图片轮播ViewPager适配数据
                vpBarner.setAdapter(adapter);
                //把ViewPager交给圆形指示器
                circleBarner.setViewPager(vpBarner);
            } else
            {
                //没有数据
                ToastUtil.getInstance().showToast("没有数据", Toast.LENGTH_SHORT);
            }
        }
    }
}
