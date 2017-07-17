package com.fmrt.p2p.invest;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;


/**
 * Created by Administrator on 2017-07-03.
 */

public class InvestFragment extends BaseFragment
{
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    @Override
    public View initView()
    {

        View view = View.inflate(mContext, R.layout.fragment_invest, null);

        initTitle(view);
        return view;
    }

    private void initTitle(View view)
    {
        iv_left= (ImageView) view.findViewById(R.id.iv_left);
        iv_right= (ImageView) view.findViewById(R.id.iv_right);
        iv_left.setVisibility(View.INVISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }
}
