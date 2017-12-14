package com.fmrt.p2p.product.childfragment;



import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.base.BaseFragment;
import butterknife.Bind;
import butterknife.OnClick;

import com.fmrt.p2p.R;
import com.fmrt.p2p.util.ToastUtil;


/**
 * 活动列表
 */

public class ActivityListFragment extends BaseFragment implements View.OnClickListener
{


    @Bind(R.id.footerListView)
    ListView mLvContent;

    private ImageView ivPointsMall;
    private TextView tvXS, tvRecommend;
    private RelativeLayout rlXS,rlRecommend;

    private View mHeadView;
    private View mRootView;

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_huodong;
    }

    @Override
    protected void initView()
    {
        mHeadView = View.inflate(getActivity(), R.layout.view_item_head_huodong, null);
        if (mLvContent != null)
        {
            mLvContent.addHeaderView(mHeadView);
        }

        ivPointsMall = (ImageView) mHeadView.findViewById(R.id.iv_points_mall);
        rlXS = (RelativeLayout) mHeadView.findViewById(R.id.rl_xs);
        rlRecommend = (RelativeLayout) mHeadView.findViewById(R.id.rl_recommend);

        tvXS = (TextView) mHeadView.findViewById(R.id.tvXS);
        tvRecommend = (TextView) mHeadView.findViewById(R.id.tvRecommend);

        ivPointsMall.setOnClickListener(this);
        rlXS.setOnClickListener(this);
        rlRecommend.setOnClickListener(this);

        mLvContent.setAdapter(null);

        TextPaint tp1 = tvXS.getPaint();
        tp1.setFakeBoldText(true);
        TextPaint tp2 = tvRecommend.getPaint();
        tp2.setFakeBoldText(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_points_mall:
                ToastUtil.getInstance().showToast("积分商城功能待开放", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_xs:
                ToastUtil.getInstance().showToast("富民小善功能待开放", Toast.LENGTH_SHORT);
                break;
            case R.id.rl_recommend:
                ToastUtil.getInstance().showToast("推荐有礼功能待开放", Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }

}
