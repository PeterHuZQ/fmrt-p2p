package com.fmrt.p2p.usercenter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseFragment;
import com.fmrt.p2p.usercenter.adapter.UserCenterFragmentAdapter;
import com.fmrt.p2p.util.PrefUtils;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.MyGridView;

/**
 * 个人中心Fragment
 */

public class UserCenterFragment extends Fragment implements View.OnClickListener
{
    //“设置”按钮
    private TextView mTvSetting;

    private MyGridView gv_user;

    private Button bt_user_logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_usercenter, null);
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
        //3、初始化监听
        initListener();
    }

    //初始化控件
    private void initView(View view) {
        //设置按钮
        mTvSetting = (TextView) view.findViewById(R.id.tvSetting);
        bt_user_logout=(Button) view.findViewById(R.id.bt_user_logout);
    }

    //初始化数据
    private void initData(){
        //创建GridView
        gv_user = (MyGridView) getActivity().findViewById(R.id.gv_user);
        //给GridView设置adapter
        gv_user.setAdapter(new UserCenterFragmentAdapter(getActivity()));

        //TODO 在button上显示当前用户名称
        String username = PrefUtils.getString(
                getActivity(), "username", "username");
        bt_user_logout.setText("退出登录"+username);
    }

    private void initListener()
    {
        mTvSetting.setOnClickListener(this);
        //个人中心九宫格
        gv_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 1:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 2:
                        ToastUtil.getInstance().showToast( "position" + position, Toast.LENGTH_SHORT);
                        break;
                    case 3:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 4:// 点击我的银行卡
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 5:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 6:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 7:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;
                    case 8:
                        ToastUtil.getInstance().showToast( "position" + position,Toast.LENGTH_SHORT);
                        break;

                }

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.tvSetting:
                //跳转到设置页面
                /*Intent intent = new Intent(getActivity(),
                        SettingActivity.class);*/
                //启动指定的activity
                /*startActivity(intent);*/
                ToastUtil.getInstance().showToast( "跳转到设置页面",Toast.LENGTH_SHORT);
                break;
        }
    }
}
