package com.fmrt.p2p.product.childfragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.fmrt.p2p.R;
import com.fmrt.p2p.product.adapter.InvestListAdapter;
import com.fmrt.p2p.product.bean.InvestBeanData;
import com.fmrt.p2p.service.ServerManager;
import com.fmrt.p2p.util.Model;
import com.fmrt.p2p.util.ToastUtil;

import java.util.List;



/**
 * 投资列表
 */

public class InvestListFragment extends Fragment
{
    private ListView lv_invest;

    //返回的数据
    private List<InvestBeanData.InvestBean> invest_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= View.inflate(getActivity(), R.layout.fragment_invest_list,null);
        //1、初始化控件
        initView(view);
        return view;
    }

    /**
     * 业务逻辑
     */
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //2、初始化数据
        initData();

    }



    private void initView(View view)
    {
        lv_invest = (ListView) view.findViewById(R.id.lv_invest);
    }

    private void initData()
    {
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        //获取全局线程池对象（创建子线程）,去服务器请求投资频道的数据
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 去P2PInvest服务器请求投资频道的数据
                    final String result= ServerManager.getInstance().getInvestData();
                    Log.e("p2p", "投资频道联网成功content："+ result);
                    // 更新页面显示
                    getActivity().runOnUiThread(new Runnable() {
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
        if(investBeanData.isSuccess()){
            invest_list=investBeanData.getData();
            if(invest_list != null){ //有数据
                Log.e("p2p", "解析成功=="+invest_list.get(0).getId() );

                lv_invest.setAdapter(new InvestListAdapter(getActivity(),invest_list));
            }else{
                //没有数据
                ToastUtil.getInstance().showToast( "没有数据",Toast.LENGTH_SHORT);
            }
        }
    }


}
