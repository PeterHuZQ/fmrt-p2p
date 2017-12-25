package com.fmrt.p2p.product.childfragment;

import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fmrt.p2p.base.BaseFragment;
import butterknife.BindView;
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
import com.fmrt.p2p.R;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.product.adapter.ActivityListAdapter;
import com.fmrt.p2p.product.bean.Activity;
import com.fmrt.p2p.product.bean.ActivityListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.LoadListView;
import com.fmrt.p2p.widget.LoadingPage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static com.fmrt.p2p.common.AppNetConfig.PTP_LOAN_BASE_URL;

/**
 * 活动列表
 */
public class ActivityListFragment extends BaseFragment implements View.OnClickListener,LoadListView.ILoadListener
{


    @BindView(R.id.lv_activity)
    LoadListView lv_activity;
    @BindView(R.id.loadingPage)
    LoadingPage mLoadingPage;

    private ImageView ivPointsMall;
    private TextView tvXS, tvRecommend;
    private RelativeLayout rlXS,rlRecommend;

    private View mHeadView;
    private View mRootView;

    //数据源
    List<Activity> activity_list = new ArrayList<>();

    //返回的推荐列表数据
    private List<Activity> resultBean;

    ActivityListAdapter adapter;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_huodong;
    }

    @Override
    protected void initView()
    {
        mHeadView = View.inflate(getActivity(), R.layout.view_item_head_huodong, null);
        if (lv_activity != null)
        {
            lv_activity.addHeaderView(mHeadView);
        }

        ivPointsMall = (ImageView) mHeadView.findViewById(R.id.iv_points_mall);
        rlXS = (RelativeLayout) mHeadView.findViewById(R.id.rl_xs);
        rlRecommend = (RelativeLayout) mHeadView.findViewById(R.id.rl_recommend);

        tvXS = (TextView) mHeadView.findViewById(R.id.tvXS);
        tvRecommend = (TextView) mHeadView.findViewById(R.id.tvRecommend);

        ivPointsMall.setOnClickListener(this);
        rlXS.setOnClickListener(this);
        rlRecommend.setOnClickListener(this);

        //lv_activity.setAdapter(null);

        TextPaint tp1 = tvXS.getPaint();
        tp1.setFakeBoldText(true);
        TextPaint tp2 = tvRecommend.getPaint();
        tp2.setFakeBoldText(true);
    }

    @Override
    public void initData()
    {
        //联网请求数据
        getDataByRetrofitAndRxJava("5");
        //给ListView设置ILoadListener
        lv_activity.setInterface(this);
        //给ListView设置适配器adapter
        adapter = new ActivityListAdapter(getActivity(), activity_list);
        lv_activity.setAdapter(adapter);
    }

    /**
     * 用RxJava和Retrofit获取数据活动频道活动列表数据
     */
    private void getDataByRetrofitAndRxJava(String num)
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
        Observer<ActivityListBeanData> observer = new Observer<ActivityListBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull ActivityListBeanData result)
            {
                //解析数据
                processData(result);
            }

            @Override
            public void onError(@NonNull Throwable e)
            {
                //网络不通，不能与服务器正在通讯，无法加载数据
                mLoadingPage.showPage(AppConstant.PAGE_ERROR_STATE);
            }

            @Override
            public void onComplete()
            {
                mLoadingPage.setVisibility(View.GONE);
            }
        };

        //调用findActivityList()获取推荐频道列表数据
        service.findActivityList()
                .doOnSubscribe(new Consumer<Disposable>()
                {
                    //doOnSubscribe用于在call之前执行一些初始化操作
                    @Override
                    public void accept(Disposable disposable) throws Exception
                    {
                        //展示“正在加载”
                        mLoadingPage.showPage(AppConstant.PAGE_LOADING_STATE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 解析数据
     *
     * @param activityListBeanData
     */
    private void processData(ActivityListBeanData activityListBeanData)
    {
        if (activityListBeanData.getStatus().equals("200"))
        {
            resultBean = activityListBeanData.getRows();
            if (resultBean != null)
            {   //有数据
                for (int i = activity_list.size(); i < resultBean.size(); i++)
                {
                    Activity entity = new Activity();
                    entity.setTitle(resultBean.get(i).getTitle());
                    entity.setTime(resultBean.get(i).getTime());
                    entity.setImgurl(resultBean.get(i).getImgurl());
                    activity_list.add(entity);
                    //数据源发生变化，更新adapter
                    adapter.notifyDataSetChanged();
                }

            } else
            {
                //没有数据
                //ToastUtil.getInstance().showToast( "没有数据", Toast.LENGTH_SHORT);
                lv_activity.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_points_mall:
                ToastUtil.getInstance().showToast("积分商城功能待开放", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_xs:
                ToastUtil.getInstance().showToast("富民小善功能待开放", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_recommend:
                ToastUtil.getInstance().showToast("推荐有礼功能待开放", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }

    @Override
    public void onLoad()
    {

    }
}
