package com.fmrt.p2p.product.childfragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.product.activity.InvestDetailActivity;
import com.fmrt.p2p.product.adapter.RecommendListAdapter;
import com.fmrt.p2p.product.bean.CusContract;
import com.fmrt.p2p.product.bean.RecommendListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.widget.LoadingPage;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fmrt.p2p.common.AppNetConfig.PTP_LOAN_BASE_URL;

/**
 * 推荐列表
 */

public class RecommendListFragment extends BaseFragment
{
    @Bind(R.id.lv_recommend)
    ListView lv_recommend;
    @Bind(R.id.llHint)
    LinearLayout ll_hint;
    @Bind(R.id.loadingPage)
    LoadingPage mLoadingPage;

    //返回的数据
    private List<CusContract> recommend_list;

    private static final String CUSCOBTRACT_UUID = "cusContractUuid";

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_recommend_list;
    }

    @Override
    protected void initView()
    {

    }

    @Override
    public void initData()
    {
        //联网请求数据
        getDataByRetrofitAndRxJava();
    }

    /**
     * 用RxJava和Retrofit获取数据推荐频道列表数据
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
        Observer<RecommendListBeanData> observer = new Observer<RecommendListBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull RecommendListBeanData result)
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

        //调用queryContractList()获取推荐频道列表数据
        service.queryContractList()
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

    /**
     * 解析数据
     *
     * @param recommendListBeanData
     */
    private void processData(RecommendListBeanData recommendListBeanData)
    {
        if (recommendListBeanData.getStatus().equals("200"))
        {
            recommend_list = recommendListBeanData.getRows();
            if (recommend_list != null)
            { //有数据
                //  Log.e("p2p", "解析成功=="+recommend_list.get(0).getUuid() );
                lv_recommend.setVisibility(View.VISIBLE);
                ll_hint.setVisibility(View.GONE);
                lv_recommend.setAdapter(new RecommendListAdapter(getActivity(), recommend_list));

            } else
            {
                //没有数据
                //ToastUtil.getInstance().showToast( "没有数据", Toast.LENGTH_SHORT);
                lv_recommend.setVisibility(View.GONE);
                ll_hint.setVisibility(View.VISIBLE);
            }
            //设置监听
            initListener();
        }
    }


    /**
     * 初始化监听
     */
    private void initListener()
    {

        lv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //根据位置得到对应的数据
                CusContract cusContract = recommend_list.get(position);
                //启动理财详情页，传递数据，把数据放在intent里
                startInvestDetailActivity(cusContract.getUuid());
            }
        });
    }

    /**
     * 启动理财详情页
     *
     * @param uuid
     */
    private void startInvestDetailActivity(String uuid)
    {
        Intent intent = new Intent(getActivity(), InvestDetailActivity.class);
        //携带数据到InvestDetailActivity
        intent.putExtra(CUSCOBTRACT_UUID, uuid);
        //启动指定的activity
        getActivity().startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
