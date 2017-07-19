package com.fmrt.p2p.invest.childfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmrt.p2p.R;

/**
 * 投资列表
 */

public class InvestListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= View.inflate(getActivity(), R.layout.fragment_invest_list,null);
        return view;
    }
}
