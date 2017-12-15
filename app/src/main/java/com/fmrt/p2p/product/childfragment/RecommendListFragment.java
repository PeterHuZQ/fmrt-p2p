package com.fmrt.p2p.product.childfragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.app.MainActivity;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.product.activity.InvestDetailActivity;
import com.fmrt.p2p.product.adapter.RecommendListAdapter;
import com.fmrt.p2p.product.bean.CusContract;
import com.fmrt.p2p.product.bean.FreshRulesBeanData;
import com.fmrt.p2p.product.bean.RecommendListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.util.NewoPupWindowUtils;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.LoadListView;
import com.fmrt.p2p.widget.LoadingPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
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

import static com.fmrt.p2p.common.AppNetConfig.PTP_LOAN_BASE_URL;

/**
 * 推荐列表
 */

public class RecommendListFragment extends BaseFragment implements LoadListView.ILoadListener,NewoPupWindowUtils.PopWindowClickListenler
{
    //ListView绑定适配器，适配器绑定数据源
    @BindView(R.id.lv_recommend)
    LoadListView lv_recommend;
    @BindView(R.id.llHint)
    LinearLayout ll_hint;
    @BindView(R.id.loadingPage)
    LoadingPage mLoadingPage;
    //新手规则按钮
    @BindView(R.id.fab)
    ImageView mFab;

    //数据源
    List<CusContract> recommend_list = new ArrayList<>();


    //返回的推荐列表数据
    private List<CusContract> resultBean;

    //返回的新手规则数据
    private FreshRulesBeanData.FreshRulesData freshRulesData;

    private static final String CUSCONTRACT_UUID = "cusContractUuid";

    String num = "5";

    RecommendListAdapter adapter;

    //新手规则相关
    private MainActivity mActivity;
    private PopupWindow mPopupWindow;
    private WindowManager.LayoutParams mLp;
    private NewoPupWindowUtils mNewoPupWindowUtils;
    private RecommendListFragment instance;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_recommend_list;
    }

    @Override
    protected void initView()
    {
        mActivity = (MainActivity) getActivity();

        //初始化新手规则按钮
        mFab.setVisibility(View.VISIBLE);
        // 设置背景颜色变暗
        mLp = mActivity.getWindow().getAttributes();
        //新手弹窗帮助类
        mNewoPupWindowUtils = new NewoPupWindowUtils();
        //初始化新手弹窗布局
        mPopupWindow = mNewoPupWindowUtils.initpopwindow(mActivity,1, this);
        //隐藏“立即投资”按钮
        mNewoPupWindowUtils.mNow_invest.setVisibility(View.GONE);
    }

    @Override
    public void initData()
    {
        //联网请求数据
        getDataByRetrofitAndRxJava("5");
        //给ListView设置ILoadListener
        lv_recommend.setInterface(this);
        //给ListView设置适配器adapter
        adapter = new RecommendListAdapter(getActivity(), recommend_list);
        lv_recommend.setAdapter(adapter);
    }

    /**
     * 用RxJava和Retrofit获取数据推荐频道列表数据
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
                //网络不通，不能与服务器正在通讯，无法加载数据
                mLoadingPage.showPage(AppConstant.PAGE_ERROR_STATE);
            }

            @Override
            public void onComplete()
            {
                mLoadingPage.setVisibility(View.GONE);
            }
        };

        //调用queryContractList()获取推荐频道列表数据
        service.queryContractList(num)
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
     * @param recommendListBeanData
     */
    private void processData(RecommendListBeanData recommendListBeanData)
    {
        if (recommendListBeanData.getStatus().equals("200"))
        {
            resultBean = recommendListBeanData.getRows();
            if (resultBean != null)
            {   //有数据
                for (int i = recommend_list.size(); i < resultBean.size(); i++)
                {
                    CusContract entity = new CusContract();
                    entity.setUuid(resultBean.get(i).getUuid());
                    entity.setDisplayid(resultBean.get(i).getDisplayid());
                    entity.setInvestorrate(resultBean.get(i).getInvestorrate());
                    entity.setContrenddt(resultBean.get(i).getContrenddt());
                    entity.setResidualamt(resultBean.get(i).getResidualamt());
                    entity.setBorramt(resultBean.get(i).getBorramt());
                    recommend_list.add(entity);
                    //数据源发生变化，更新adapter
                    adapter.notifyDataSetChanged();
                }

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
        //防止Fragment被销毁后请求返回的数据找不到View
        if (instance == null)
        {
            return;
        }
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
        intent.putExtra(CUSCONTRACT_UUID, uuid);
        //启动指定的activity
        getActivity().startActivity(intent);
    }

    /**
     * 加载更多数据
     */
    @Override
    public void onLoad()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                //获取更多数据
                getLoadData();
                //通知listview加载完毕
                lv_recommend.loadComplete();
            }
        }, 2000);
    }

    /**
     * 获取更多数据
     */
    private void getLoadData()
    {
        num = Integer.parseInt(num) + 5 + "";
        getDataByRetrofitAndRxJava(num);
    }

    /**
     * 点击新手规则，显示新手规则的popWindows
     */
    @OnClick(R.id.fab)
    public void onViewClicked() {
        getFreshRulesByRetrofitAndRxJava();
    }

    /**
     * 用RxJava和Retrofit获取数据新手规则数据
     */
    private void getFreshRulesByRetrofitAndRxJava()
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
        Observer<FreshRulesBeanData> observer = new Observer<FreshRulesBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull FreshRulesBeanData result)
            {
                //解析数据
                processFreshRulesData(result);
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

        //调用getFreshRules()获取新手规则
        service.getFreshRules()
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
     */
    private void processFreshRulesData(FreshRulesBeanData freshRulesBeanData)
    {
        if (freshRulesBeanData.getCode().equals("0"))
        {
            freshRulesData = freshRulesBeanData.getData();
            if (freshRulesData != null)
            {
                mNewoPupWindowUtils.mRate_return.setText(freshRulesData.getRuleReturnRateOfYear());
                mNewoPupWindowUtils.mIn_object.setText(freshRulesData.getRuleParticipant());
                mNewoPupWindowUtils.mInvestment_rules.setText(freshRulesData.getRuleInvestRuleDescription().replace("\\n","\n"));
                mNewoPupWindowUtils.mInvestment_quota.setText(freshRulesData.getRuleInvestAmountLimit());
                mNewoPupWindowUtils.mYear_rate.setText(freshRulesData.getRuleReturnRateOfYear());
                showPopWindows();
            } else
            {
                //没有数据
                ToastUtil.getInstance().showToast( "没有数据", Toast.LENGTH_SHORT);
            }

        }
    }

    /**
     * 显示新手规则的popWindows
     */
    private void showPopWindows() {
        //获得跟视图View
        View view = mActivity.getWindow().getDecorView().findViewById(R.id.popup_view);
        mPopupWindow.showAsDropDown(view, 0, 0);
        // 设置背景颜色变暗
        mLp.alpha = 0.5f;
        mActivity.getWindow().setAttributes(mLp);
    }

    /**
     * popwindow 的View的点击事件
     * @param view
     */
    @Override
    public void popClick(View view)
    {
        switch (view.getId()){

            //让布局消失
            case R.id.close_pop:
                mPopupWindow.dismiss();
                break;
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        instance = null;
    }
}
