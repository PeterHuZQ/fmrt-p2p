package com.fmrt.p2p.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类Fragment,
 * 理财InvestFragment
 * 转让TransferFragment
 * 借款LoanFragment
 * 个人中心UserCenterFragment
 * 都要继承该类
 */

public abstract class BaseFragment extends Fragment
{
    protected Context mContext;

    /**
     * 1、当该类被系统创建的时候被回调
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    /**
     * 2、负责UI的创建
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象类，由子类实现，实现不同的效果
     * @return
     */
    public abstract View initView() ;

    /**
     * 3、负责得到数据，去显示UI,当Activity被创建了的时候回调这个方法
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要联网请求数据的时候，可以重写该方法，在该方法中联网请求
     */
    public void initData() {

    }
}
