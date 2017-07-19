package com.fmrt.p2p.invest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * MyFragmentPagerAdapter:理财适配器（ViewPager的适配器）
 *
 * FragmentPagerAdapter派生自PagerAdapter，它是用来呈现Fragment页面的，
 * 这些Fragment页面会一直保存在fragment manager中，以便用户可以随时取用。
 *
 * 对于FragmentPagerAdapter的派生类，只需要重写getItem(int)和getCount()就可以了。
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    //一个Fragment的List对象，用于保存用于滑动的Fragment对象
    private List<Fragment> fragment_list;
    //tab名的列表
    private List<String> title_list;

    //构造函数
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String>title_list) {
        super(fm);
        this.fragment_list=fragments;
        this.title_list=title_list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_list.get(position);
    }

    @Override
    public int getCount() {
        return fragment_list == null ? 0 : fragment_list.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return title_list.get(position % title_list.size());
    }

}
