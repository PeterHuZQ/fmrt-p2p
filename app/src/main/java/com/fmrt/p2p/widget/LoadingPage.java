package com.fmrt.p2p.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fmrt.p2p.R;
import com.fmrt.p2p.common.AppConstant;


/**
 * 自定义FrameLayout抽取公共通用view界面类loadingPage
 * 任何一个view界面展示都有4种情况：
 * （1）正在加载
 * （2）网络不通，不能与服务器正在通讯，无法加载数据
 * （3）加载成功，但无数据
 * （4）加载成功
 */
public class LoadingPage extends FrameLayout
{
    private LinearLayout loadingView;

    private LinearLayout errorView;

    private LinearLayout emptyView;

    private Context mConext;

    //构造方法
    public LoadingPage(Context context)
    {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mConext = context;

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_loading, null);

        loadingView = (LinearLayout) view.findViewById(R.id.layout_loading);
        errorView = (LinearLayout) view.findViewById(R.id.layout_error);
        emptyView = (LinearLayout) view.findViewById(R.id.layout_empty);

        addView(view);
    }


    public void showPage(int PAGE_CURRENT_STATE)
    {
        //显示“正在加载”
        loadingView.setVisibility(PAGE_CURRENT_STATE == AppConstant.PAGE_LOADING_STATE ? View.VISIBLE : View.GONE);
        //显示“网络不通，不能与服务器正在通讯，无法加载数据”
        errorView.setVisibility(PAGE_CURRENT_STATE == AppConstant.PAGE_ERROR_STATE ? View.VISIBLE : View.GONE);
        //显示“加载成功，但无数据”
        emptyView.setVisibility(PAGE_CURRENT_STATE == AppConstant.PAGE_EMPTY_STATE ? View.VISIBLE : View.GONE);
    }

}
