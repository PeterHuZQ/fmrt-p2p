package com.fmrt.p2p.product.childfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.product.activity.InvestDetailActivity;
import com.fmrt.p2p.product.adapter.RecommendListAdapter;
import com.fmrt.p2p.product.bean.CusContract;
import com.fmrt.p2p.product.bean.RecommendListBeanData;
import com.fmrt.p2p.service.RetrofitService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.fmrt.p2p.util.AppConstants.PTP_LOAN_BASE_URL;

/**
 * 推荐列表
 */

public class RecommendListFragment extends Fragment
{
    private ListView lv_recommend;
    private LinearLayout ll_hint;
    //返回的数据
    private List<CusContract> recommend_list;

    private static final String CUSCOBTRACT_UUID = "cusContractUuid";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.fragment_recommend_list, null);
        //1、初始化控件
        initView(view);
        return view;
    }

    /**
     * 业务逻辑
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //2、初始化数据
        initData();
    }

    private void initView(View view)
    {
        lv_recommend = (ListView) view.findViewById(R.id.lv_recommend);
        ll_hint = (LinearLayout) view.findViewById(R.id.llHint);
    }

    private void initData()
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
                Log.e("RecommendListFragment", "onError:" + e.getMessage());
            }

            @Override
            public void onComplete()
            {
                Log.e("LoginActivity", "onComplete");
            }
        };

        //调用queryContractList()获取推荐频道列表数据
        service.queryContractList()
                .map(new Function<RecommendListBeanData, RecommendListBeanData>()
                {
                    @Override
                    public RecommendListBeanData apply(@NonNull RecommendListBeanData responseBody) throws Exception
                    {
                        return responseBody;
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


}
