package com.fmrt.p2p.product.childfragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.app.MainActivity;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.common.AppConstant;
import com.fmrt.p2p.product.activity.InvestDetailActivity;
import com.fmrt.p2p.product.adapter.TransferListAdapter;
import com.fmrt.p2p.product.bean.ContractInfo;
import com.fmrt.p2p.product.bean.TransferListBeanData;
import com.fmrt.p2p.service.RetrofitService;
import com.fmrt.p2p.util.DateUtils;
import com.fmrt.p2p.util.NewoPupWindowUtilsForSearch;
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
import static com.fmrt.p2p.common.AppNetConfig.PTP_INVEST_BASE_URL;

/**
 * 转让列表
 */

public class TransferListFragment extends BaseFragment implements LoadListView.ILoadListener, View.OnClickListener ,NewoPupWindowUtilsForSearch.PopWindowClickListenler
{
    //排序
    @BindView(R.id.chooseRate)
    TextView chooseRate;
    @BindView(R.id.year_imageup)
    ImageView year_imageup;
    @BindView(R.id.year_imagedown)
    ImageView year_imagedown;
    @BindView(R.id.chooseTime)
    TextView chooseTime;
    @BindView(R.id.time_imageup)
    ImageView time_imageup;
    @BindView(R.id.time_imagedown)
    ImageView time_imagedown;
    @BindView(R.id.chooseMoney)
    TextView chooseMoney;
    @BindView(R.id.money_imageup)
    ImageView money_imageup;
    @BindView(R.id.money_imagedown)
    ImageView money_imagedown;
    //筛选
    @BindView(R.id.custom_item)
    TextView mCustomItem;



    //ListView绑定适配器，适配器绑定数据源
    @BindView(R.id.lv_transfer)
    LoadListView lv_transfer;
    @BindView(R.id.llHint)
    LinearLayout ll_hint;
    @BindView(R.id.loadingPage)
    LoadingPage mLoadingPage;


    //数据源
    List<ContractInfo> transfer_list = new ArrayList<>();



    //返回的数据
    private List<ContractInfo> resultBean;

    private static final String CUSCONTRACT_UUID = "cusContractUuid";

    String num = "5";

    TransferListAdapter adapter;

    //转让频道自定义排序筛选相关
    private String rateOrder = "", dueDateOrder = "", capitalOrder = "", moneyStart = "", moneyEnd = "",timeStart = "", timeEnd = "", rateStart = "", rateEnd = "";
    private MainActivity mActivity;
    private PopupWindow mPopupWindow;
    private WindowManager.LayoutParams mLp;
    private NewoPupWindowUtilsForSearch mNewoPupWindowUtilsForSearch;

    private TransferListFragment instance;


    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_transfer_list;
    }

    @Override
    protected void initView()
    {
        instance = this;

        mActivity = (MainActivity) getActivity();
        // 设置背景颜色变暗
        mLp = mActivity.getWindow().getAttributes();
        //筛选弹窗帮助类
        mNewoPupWindowUtilsForSearch = new NewoPupWindowUtilsForSearch();
        //初始化筛选弹窗布局
        mPopupWindow = mNewoPupWindowUtilsForSearch.initpopwindow(mActivity,this);

    }

    @Override
    public void initData()
    {
        //联网请求数据
        getDataByRetrofitAndRxJava("5", timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);
        //给ListView设置ILoadListener
        lv_transfer.setInterface(this);
        //给ListView设置适配器adapter
        adapter = new TransferListAdapter(getActivity(), transfer_list);
        lv_transfer.setAdapter(adapter);
    }

    /**
     * 用RxJava和Retrofit获取数据转让频道列表数据
     */
    private void getDataByRetrofitAndRxJava(String num, String timeStart,String timeEnd, String moneyStart,String moneyEnd,String rateStart,String rateEnd,String dueDateOrder, String capitalOrder, String rateOrder)
    {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(5, TimeUnit.SECONDS);
        //创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(PTP_INVEST_BASE_URL + "/")
                .build();

        //用Retrofit创建一个RetrofitService的代理对象
        RetrofitService service = retrofit.create(RetrofitService.class);

        //定义观察者：Observer
        Observer<TransferListBeanData> observer = new Observer<TransferListBeanData>()
        {
            @Override
            public void onSubscribe(@NonNull Disposable d)
            {

            }

            @Override
            public void onNext(@NonNull TransferListBeanData result)
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
                //防止Fragment被销毁后请求返回的数据找不到View
                if (instance == null)
                {
                    return;
                }
                mLoadingPage.setVisibility(View.GONE);
            }
        };

        //调用findTransferInfoByWhere()获取转让频道列表数据
        service.findTransferInfoByWhere(num, timeStart, timeEnd, moneyStart, moneyEnd, rateStart, rateEnd, dueDateOrder, capitalOrder, rateOrder)
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
     * @param transferListBeanData
     */
    private void processData(TransferListBeanData transferListBeanData)
    {
        if (transferListBeanData.getStatus().equals("200"))
        {
            resultBean = transferListBeanData.getRows();
            if (resultBean != null)
            {
                //防止Fragment被销毁后请求返回的数据找不到View
                if (instance == null)
                {
                    return;
                }
                //有数据
                transfer_list.clear();
                for (int i = transfer_list.size(); i < resultBean.size(); i++)
                {
                    ContractInfo entity = new ContractInfo();
                    entity.setUuid(resultBean.get(i).getUuid());
                    entity.setTrans_contr_no(resultBean.get(i).getTrans_contr_no());
                    entity.setRate(resultBean.get(i).getRate());
                    entity.setContrenddt(resultBean.get(i).getContrenddt());
                    entity.setCurr_principal(resultBean.get(i).getCurr_principal());
                    transfer_list.add(entity);
                }
                //数据源发生变化，更新adapter
                adapter.notifyDataSetChanged();

            } else
            {
                //没有数据
                //ToastUtil.getInstance().showToast( "没有数据", Toast.LENGTH_SHORT);
                lv_transfer.setVisibility(View.GONE);
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
        lv_transfer.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //根据位置得到对应的数据
                ContractInfo contractInfo = transfer_list.get(position);
                //启动资产详情页，传递数据，把数据放在intent里
                startInvestDetailActivity(contractInfo.getUuid());
            }
        });


    }

    /**
     * 启动资产详情页
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
                lv_transfer.loadComplete();
            }
        }, 2000);

    }

    /**
     * 获取更多数据
     */
    private void getLoadData()
    {
        num = Integer.parseInt(num) + 5 + "";
        getDataByRetrofitAndRxJava(num, timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);
    }

    //筛选点击事件
    @OnClick({R.id.chooseRate, R.id.chooseTime, R.id.chooseMoney, R.id.custom_item})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            //年利率 //"利率由高到低", 1, "desc"   2, "asc"
            case R.id.chooseRate:
                if ("".equals(rateOrder))
                {
                    rateOrder = "desc";
                    setImageUpDown(year_imageup, year_imagedown, false, true);
                    chooseRate.setSelected(true);
                } else if ("desc".equals(rateOrder))
                { //如果当前是由小到大
                    rateOrder = "asc";
                    setImageUpDown(year_imageup, year_imagedown, true, false);
                    chooseRate.setSelected(true);
                } else
                {
                    rateOrder = "";
                    setImageUpDown(year_imageup, year_imagedown, false, false);
                    chooseRate.setSelected(false);
                }
                getDataByRetrofitAndRxJava("5", timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);
                break;
            //到期时间
            case R.id.chooseTime:
                if ("".equals(dueDateOrder))
                {
                    dueDateOrder = "desc";
                    setImageUpDown(time_imageup, time_imagedown, false, true);
                    chooseTime.setSelected(true);
                } else if ("desc".equals(dueDateOrder))
                { //如果当前是由小到大
                    dueDateOrder = "asc";
                    setImageUpDown(time_imageup, time_imagedown, true, false);
                    chooseTime.setSelected(true);
                } else
                {
                    dueDateOrder = "";
                    setImageUpDown(time_imageup, time_imagedown, false, false);
                    chooseTime.setSelected(false);
                }
                getDataByRetrofitAndRxJava("5", timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);

                break;
            //资金
            case R.id.chooseMoney:

                if ("".equals(capitalOrder))
                {
                    capitalOrder = "desc";
                    setImageUpDown(money_imageup, money_imagedown, false, true);
                    chooseMoney.setSelected(true);
                } else if ("desc".equals(capitalOrder))
                { //如果当前是由小到大
                    capitalOrder = "asc";
                    setImageUpDown(money_imageup, money_imagedown, true, false);
                    chooseMoney.setSelected(true);
                } else
                {
                    capitalOrder = "";
                    setImageUpDown(money_imageup, money_imagedown, false, false);
                    chooseMoney.setSelected(false);
                }
                getDataByRetrofitAndRxJava("5", timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);
                break;

            // 筛选
            case R.id.custom_item:
                //ToastUtil.getInstance().showToast( "跳出自定义筛选", Toast.LENGTH_SHORT);
                showPopWindows();
                break;
        }
    }

    //设置上下图片的样式,设置上面的图片下面的状态为相反
    private void setImageUpDown(ImageView imageup, ImageView imagedown, boolean a, boolean b)
    {
        imageup.setSelected(a);
        imagedown.setSelected(b);
    }

    /**
     * 显示筛选的popWindows
     */
    private void showPopWindows()
    {
        //获得跟视图View
        View view = mActivity.getWindow().getDecorView().findViewById(R.id.popup_view);
        mPopupWindow.showAsDropDown(view, 0, 0);
        // 设置背景颜色变暗
        mLp.alpha = 0.7f;
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
            case R.id.disappear:
                mPopupWindow.dismiss();
                break;
            //重置数据
            case R.id.bt_restore:
                mNewoPupWindowUtilsForSearch.restoreData();
                mNewoPupWindowUtilsForSearch.restoreAllClickView();
                break;
            //年利率tab  1.首先判断有没有光标在2个edt 里面,没有taost
            // 2.如果有直接将点击的数据赋值,如果是第一个ed 赋值以后跳转到第二个edt 清空tab状态 如果是第二个直接赋值
            case R.id.tv_year_tab1:
                mNewoPupWindowUtilsForSearch.ChangeDataET("5.1");
                break;
            case R.id.tv_year_tab2:
                mNewoPupWindowUtilsForSearch.ChangeDataET("5.4");
                break;
            case R.id.tv_year_tab3:
                mNewoPupWindowUtilsForSearch.ChangeDataET("5.7");
                break;
            case R.id.tv_year_tab4:
                mNewoPupWindowUtilsForSearch.ChangeDataET("6.0");
                break;
            //时间的tab
            case R.id.tv_month_tab1:
                mNewoPupWindowUtilsForSearch.setMonthTextDate( "0", "3");
                break;
            case R.id.tv_month_tab2:
                mNewoPupWindowUtilsForSearch.setMonthTextDate( "4", "6");
                break;
            case R.id.tv_month_tab3:
                mNewoPupWindowUtilsForSearch.setMonthTextDate( "7", "9");
                break;
            case R.id.tv_month_tab4:
                mNewoPupWindowUtilsForSearch.setMonthTextDate( "10", "12");
                break;
            //金额的tab
            case R.id.tv_money_tab1:
                mNewoPupWindowUtilsForSearch.setMoneyTextDate( "0", "5000");
                break;
            case R.id.tv_money_tab2:
                mNewoPupWindowUtilsForSearch.setMoneyTextDate( "5000", "20000");
                break;
            case R.id.tv_money_tab3:
                mNewoPupWindowUtilsForSearch.setMoneyTextDate( "20000", "50000");
                break;
            case R.id.tv_money_tab4:
                mNewoPupWindowUtilsForSearch.setMoneyTextDate( "50000", "");
                break;
            //确认按钮
            case R.id.bt_submit:
                //做完校验执行
                if ( mNewoPupWindowUtilsForSearch.checkData() && mNewoPupWindowUtilsForSearch.checkSubmitData()) {
                    ArrayList<String> mCustomTaskData=mNewoPupWindowUtilsForSearch.getFilterParams();
                    moneyStart = mCustomTaskData.get(0);
                    moneyEnd = mCustomTaskData.get(1);
                    //现在的时间
                    setCurrentStartMonth(mCustomTaskData.get(2));
                    setCurrentEndMonth(mCustomTaskData.get(3));
                    rateStart = mCustomTaskData.get(4);
                    rateEnd = mCustomTaskData.get(5);
                    mPopupWindow.dismiss();
                    //请求后台接口
                    getDataByRetrofitAndRxJava("5", timeStart,timeEnd, moneyStart,moneyEnd,rateStart,rateEnd,dueDateOrder, capitalOrder, rateOrder);
                }
                break;
        }
    }

    private void setCurrentStartMonth(String s) {
        if (!s.equals(" ") && !s.equals("")) {
            int currentMonth = Integer.parseInt(DateUtils.getMonth());
            int year = Integer.parseInt(DateUtils.getYear());
            int month = currentMonth + Integer.parseInt(s);
            if (month > 12) {
                year++;
                month = month - 12;
            }
            timeStart = year + "-" + month + "-" + DateUtils.getDay();
        } else {
            timeStart = "";
        }
    }

    private void setCurrentEndMonth(String s) {
        if (!s.equals(" ") && !s.equals("")) {
            int currentMonth = Integer.parseInt(DateUtils.getMonth());
            int year = Integer.parseInt(DateUtils.getYear());
            int month = currentMonth + Integer.parseInt(s);
            if (month > 12) {
                year++;
                month = month - 12;
            }
            timeEnd = year + "-" + month + "-" + DateUtils.getDay();
        } else {
            timeEnd = "";
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        instance = null;
    }
}
