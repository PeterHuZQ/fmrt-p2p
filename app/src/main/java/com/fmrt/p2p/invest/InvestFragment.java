package com.fmrt.p2p.invest;


import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.invest.bean.InvestBeanData;
import com.fmrt.p2p.service.ServerManager;
import com.fmrt.p2p.util.Model;
import com.fmrt.p2p.util.ToastUtil;


/**
 * Created by Administrator on 2017-07-03.
 */

public class InvestFragment extends BaseFragment
{
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    //返回的数据
    private InvestBeanData.ResultBean resultBean;

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

    @Override
    public void initData(){
        super.initData();
        Log.e("p2p", "投资页面InvestFragment的数据被初始化了。。。" );
        //联网请求投资页面的数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        //获取全局线程池对象（创建子线程）,去服务器请求投资页面的数据
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 去P2PInvest服务器请求投资页面的数据
                    final String result= ServerManager.getInstance().getInvestData();
                    Log.e("p2p", "首页联网成功");
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
        InvestBeanData investBeanData = JSON.parseObject(json,InvestBeanData.class);
        resultBean=investBeanData.getResult();
        if(resultBean != null){ //有数据
            Log.e("p2p", "解析成功=="+resultBean.getBanner_info().get(0).getOption() );
        }else{
            //没有数据
            ToastUtil.getInstance().showToast( "没有数据",Toast.LENGTH_SHORT);
        }
    }

}
