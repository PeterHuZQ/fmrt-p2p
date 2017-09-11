package com.fmrt.p2p.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fmrt.p2p.util.UIUtils;

import butterknife.ButterKnife;

/**
 * 基类Fragment,
 * 首页IndexFragment
 * 理财ProductFragment
 * 借款LoanFragment
 * 都要继承该类
 */

public abstract class BaseFragment extends Fragment
{
     public View view;

    /**
     * 负责UI的创建
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //指定布局
        view = UIUtils.getXmlView(getLayoutId());
        ButterKnife.bind(this, view);
        //1、初始化控件
        initView();
        //2、初始化数据
        initData();
        return view;
    }

    /**
     * 抽象类，由子类实现，实现不同的效果
     * @return
     */
    protected abstract int getLayoutId();
    protected abstract void initView();
    /**
     * 当子类需要联网请求数据的时候，可以重写该方法，在该方法中联网请求
     */
    public void initData() {

    }

    /**
     * 负责得到数据，去显示UI,当Activity被创建了的时候回调这个方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
