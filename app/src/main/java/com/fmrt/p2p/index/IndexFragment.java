package com.fmrt.p2p.index;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.index.adapter.BannerAdapter;
import com.fmrt.p2p.index.bean.ImgListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.RippleProgress;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.List;
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

import static com.fmrt.p2p.util.AppConstants.PTP_LOAN_BASE_URL;


/**
 * 首页Fragment
 */

public class IndexFragment extends BaseFragment
{
    //公共头布局
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    //横幅广播Banner
    private ViewPager vpBarner;
    private CirclePageIndicator circleBarner;

    private BannerAdapter adapter;

    // 水波纹进度球
    private RippleProgress mRippleProgress;

    //返回的数据
    private List<ImgListBeanData.BannerInfo> bannerInfo_list;

    @Override
    public View initView()
    {

        View view = View.inflate(mContext, R.layout.fragment_index, null);

        initTitle(view);

        vpBarner=(ViewPager)view.findViewById(R.id.vp_barner);
        circleBarner=(CirclePageIndicator)view.findViewById(R.id.circle_barner);

        mRippleProgress = (RippleProgress) view.findViewById(R.id.ripple_progress);
        //开始波纹
        mRippleProgress.startWave();
        return view;
    }

    private void initTitle(View view)
    {
        iv_left= (ImageView) view.findViewById(R.id.iv_left);
        iv_right= (ImageView) view.findViewById(R.id.iv_right);
        iv_left.setVisibility(View.INVISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData(){
        super.initData();
        Log.e("p2p", "首页IndexFragment的数据被初始化了。。。" );
        //联网请求首页的数据
        getDataByRetrofitAndRxJava();
    }

    /**
     *用RxJava和Retrofit获取首页图片列表
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
                ////解析数据
                processData(result);
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
        service.getIndexImgList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void processData(ImgListBeanData imgListBeanData){
        if (imgListBeanData.getStatus().equals("200")){
            bannerInfo_list=imgListBeanData.getRows();
            if (bannerInfo_list != null)
            {   //有数据
                //创建横幅广播Banner的适配器
                adapter = new BannerAdapter(mContext,bannerInfo_list);
                //为图片轮播ViewPager适配数据
                vpBarner.setAdapter(adapter);
                //把ViewPager交给圆形指示器
                circleBarner.setViewPager(vpBarner);
            }else{
                //没有数据
                ToastUtil.getInstance().showToast( "没有数据",Toast.LENGTH_SHORT);
            }
        }
    }
}
