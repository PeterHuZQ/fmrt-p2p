package com.fmrt.p2p.product.childfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fmrt.p2p.R;

/**
 * 推荐列表
 */

public class RecommendListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= View.inflate(getActivity(), R.layout.fragment_recommend_list,null);
        return view;
    }
}
