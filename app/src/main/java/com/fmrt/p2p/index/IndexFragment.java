package com.fmrt.p2p.index;


import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.index.adapter.BannerAdapter;
import com.fmrt.p2p.index.bean.IndexBeanData;
import com.fmrt.p2p.service.ServerManager;
import com.fmrt.p2p.util.Model;
import com.fmrt.p2p.util.ToastUtil;
import com.viewpagerindicator.CirclePageIndicator;


/**
 * 首页Fragment
 */

public class IndexFragment extends BaseFragment
{
    //公共头布局
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    //横幅广播Banner
    private ViewPager vpBarner;
    private CirclePageIndicator circleBarner;

    //返回的数据
    private IndexBeanData.ResultBean resultBean;

    private BannerAdapter adapter;

    @Override
    public View initView()
    {

        View view = View.inflate(mContext, R.layout.fragment_index, null);

        initTitle(view);

        vpBarner=(ViewPager)view.findViewById(R.id.vp_barner);
        circleBarner=(CirclePageIndicator)view.findViewById(R.id.circle_barner);
        return view;
    }

    private void initTitle(View view)
    {
        iv_left= (ImageView) view.findViewById(R.id.iv_left);
        iv_right= (ImageView) view.findViewById(R.id.iv_right);
        iv_left.setVisibility(View.INVISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData(){
        super.initData();
        Log.e("p2p", "首页IndexFragment的数据被初始化了。。。" );
        //联网请求首页的数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        //获取全局线程池对象（创建子线程）,去服务器请求首页的数据
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 去P2PInvest服务器请求首页的数据
                    final String result= ServerManager.getInstance().getIndexData();
                    // Log.e("p2p", "首页联网成功content："+ result);
                    // 更新页面显示
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(TextUtils.isEmpty(result)){
                                //Toast.makeText(mContext, "获取数据为空", Toast.LENGTH_SHORT).show();
                                ToastUtil.getInstance().showToast( "获取数据为空", Toast.LENGTH_SHORT);
                            }else{
                                //解析数据
                                processData(result);
                            }

                        }
                    });

                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void processData(String json){
        IndexBeanData indexBeanData = JSON.parseObject(json,IndexBeanData.class);
        resultBean= indexBeanData.getResult();
        if(resultBean != null){ //有数据
            // Log.e("p2p", "解析成功=="+resultBean.getBanner_info().get(0).getOption() );

            //创建横幅广播Banner的适配器
            adapter = new BannerAdapter(mContext,resultBean);
            //为图片轮播ViewPager适配数据
            vpBarner.setAdapter(adapter);
            //把ViewPager交给圆形指示器
            circleBarner.setViewPager(vpBarner);

        }else{
            //没有数据
            ToastUtil.getInstance().showToast( "没有数据",Toast.LENGTH_SHORT);
        }
    }

}
