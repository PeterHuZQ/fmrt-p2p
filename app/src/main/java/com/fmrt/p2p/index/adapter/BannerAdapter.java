package com.fmrt.p2p.index.adapter;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fmrt.p2p.index.bean.InvestBeanData;
import com.squareup.picasso.Picasso;

/**
 * BannerAdapter：建横幅广播Banner的适配器
 */

public class BannerAdapter extends PagerAdapter
{
    private Context mContext;
    private InvestBeanData.ResultBean resultBean; //数据
    private LayoutInflater mLayoutInflater;     //用于初始化布局

    //构造函数,参数（上下文，数据）
    public BannerAdapter(Context mContext, InvestBeanData.ResultBean resultBean){
        this.mContext = mContext;
        this.resultBean=resultBean;
        //初始化布局
        mLayoutInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount()
    {
        return resultBean.getBanner_info() == null ? 0 : resultBean.getBanner_info().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        //图片的地址
        String imageUrl = resultBean.getBanner_info().get(position).image;
        Log.e("p2p", "imageUrl的content："+ imageUrl);
        ImageView imageView = new ImageView(mContext);
        //设置缩放类型
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //用Picasso根据图片地址把图片加载出来，把图片放在imageView对象上
        Picasso.with(mContext).load(imageUrl).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }
}
