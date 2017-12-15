package com.fmrt.p2p.product;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.product.adapter.MyFragmentPagerAdapter;
import com.fmrt.p2p.product.childfragment.ActivityListFragment;
import com.fmrt.p2p.product.childfragment.RecommendListFragment;
import com.fmrt.p2p.product.childfragment.TransferListFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


/**
 * 理财Fragment
 */

public class ProductFragment extends BaseFragment
{
    //公共头布局
    @BindView(R.id.iv_left)
    ImageView iv_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    //定义TabLayout
    @BindView(R.id.tab_invest_title)
    TabLayout tab_invest_title;
    @BindView(R.id.vp_invest_pager)
    ViewPager mViewPager;

    private List<Fragment> fragment_list;   //定义要装fragment的列表
    private List<String> title_list;       //tab名称列表

    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_invest;
    }

    @Override
    protected void initView()
    {

        initTitle();

        //初始化子fragment
        initChildFragment();
    }

    private void initTitle()
    {
        iv_left.setVisibility(View.INVISIBLE);
        tv_title.setText("理财");
        iv_right.setVisibility(View.INVISIBLE);
    }

    private void initChildFragment()
    {
        //创建3个子fragment对象, 将fragment装进列表中
        fragment_list = new ArrayList<Fragment>();
        fragment_list.add(new RecommendListFragment());
        fragment_list.add(new TransferListFragment());
        fragment_list.add(new ActivityListFragment());



        //TODO 将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        //UIUtils.getStringArr(@string/licai_tab);
        title_list = new ArrayList<>();
        title_list.add("推荐");
        title_list.add("转让");
        title_list.add("活动");



        //设置TabLayout的模式（MODE_FIXED 和 MODE_SCROLLABLE）
        tab_invest_title.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(0)));
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(1)));
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(2)));

        //给ViewPager设定适配器
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragment_list, title_list));
        //TabLayout加载viewpager
        tab_invest_title.setupWithViewPager(mViewPager);
    }

}
