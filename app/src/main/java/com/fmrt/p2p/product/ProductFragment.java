package com.fmrt.p2p.product;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.product.adapter.MyFragmentPagerAdapter;
import com.fmrt.p2p.product.childfragment.InvestListFragment;
import com.fmrt.p2p.product.childfragment.RecommendListFragment;
import com.fmrt.p2p.product.childfragment.TransferListFragment;
import com.fmrt.p2p.util.UIUtils;

import android.support.design.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 理财Fragment
 */

public class ProductFragment extends BaseFragment
{
    //公共头布局
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    private TabLayout tab_invest_title; //定义TabLayout
    private ViewPager mViewPager;

    private List<Fragment> fragment_list;   //定义要装fragment的列表
    private List<String>  title_list;       //tab名称列表

    @Override
    public View initView()
    {
        View view =View.inflate(mContext, R.layout.fragment_invest,null);
        initTitle(view);

        tab_invest_title=  (TabLayout)view.findViewById(R.id.tab_invest_title);
        mViewPager= (ViewPager) view.findViewById(R.id.vp_invest_pager);

        //初始化子fragment
        initChildFragment(view);
        return view;
    }

    private void initTitle(View view)
    {
        iv_left= (ImageView) view.findViewById(R.id.iv_left);
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        iv_right= (ImageView) view.findViewById(R.id.iv_right);
        iv_left.setVisibility(View.INVISIBLE);
        tv_title.setText("理财");
        iv_right.setVisibility(View.INVISIBLE);
    }

    private void initChildFragment(View parentView)
    {
        //创建3个子fragment对象, 将fragment装进列表中
        fragment_list = new ArrayList<Fragment>();
        fragment_list.add(new RecommendListFragment());
        fragment_list.add(new InvestListFragment());
        fragment_list.add(new TransferListFragment());


        //TODO 将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        //UIUtils.getStringArr(@string/licai_tab);
        title_list = new ArrayList<>();
        title_list.add("推荐");
        title_list.add("投资");
        title_list.add("转让");




        //设置TabLayout的模式（MODE_FIXED 和 MODE_SCROLLABLE）
        tab_invest_title.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(0)));
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(1)));
        tab_invest_title.addTab(tab_invest_title.newTab().setText(title_list.get(2)));

        //给ViewPager设定适配器
        mViewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragment_list,title_list));
        //TabLayout加载viewpager
        tab_invest_title.setupWithViewPager(mViewPager);
    }

}
