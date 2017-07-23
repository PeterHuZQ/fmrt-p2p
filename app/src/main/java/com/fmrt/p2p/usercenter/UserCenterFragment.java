package com.fmrt.p2p.usercenter;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.usercenter.activity.LoginActivity;
import com.fmrt.p2p.usercenter.activity.SettingActivity;
import com.fmrt.p2p.usercenter.adapter.UserCenterFragmentAdapter;
import com.fmrt.p2p.usercenter.bean.LoginBeanData;
import com.fmrt.p2p.util.BitMapUtil;
import com.fmrt.p2p.util.DensityUtil;
import com.fmrt.p2p.util.ToastUtil;
import com.fmrt.p2p.widget.CircleImageView;
import com.fmrt.p2p.widget.MyGridView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * 个人中心Fragment
 */

public class UserCenterFragment extends Fragment implements View.OnClickListener
{
    // 用户头像
    private ImageView imgHead;
    //“设置”按钮
    private TextView mTvSetting;

    private MyGridView gv_user;


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
        //用户头像
        imgHead = (ImageView) view.findViewById(R.id.imgHead);
        //设置按钮
        mTvSetting = (TextView) view.findViewById(R.id.tvSetting);
    }

    //初始化数据
    private void initData(){
        //创建GridView
        gv_user = (MyGridView) getActivity().findViewById(R.id.gv_user);
        //给GridView设置adapter
        gv_user.setAdapter(new UserCenterFragmentAdapter(getActivity()));

        //判断有没有登录
        isLogin();
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
                //ToastUtil.getInstance().showToast( "跳转到设置页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(SettingActivity.class, null);
                break;
        }
    }

    //判断有没有登录
    private void isLogin()
    {
        LoginBeanData.UserBean user = ((BaseActivity) getActivity()).getUserInfo();
        if(TextUtils.isEmpty(user.getUF_ACC())){
            //未登录
            showLoginDialog();
        }else{
            //已登录--处理登录信息
            doUserInfo(user);
        }
    }

    private void showLoginDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("登录");
        builder.setMessage("必须先登录...go...");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ToastUtil.getInstance().showToast( "跳转到登录页面",Toast.LENGTH_SHORT);
                ((BaseActivity) getActivity()).gotoActivity(LoginActivity.class, null);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }


    private void doUserInfo(LoginBeanData.UserBean user)
    {
        //设置用户头像
        //Log.e("p2p","用户头像地址"+user.getUF_AVATAR_URL());
        //transform对图像进行自定义处理
        Picasso.with(getActivity()).load(user.getUF_AVATAR_URL()).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                //图片缩放
                Bitmap zoom = BitMapUtil.zoom(source, DensityUtil.dp2px(62), DensityUtil.dp2px(62));
                //头像圆形裁剪
                Bitmap circleBitMap = BitMapUtil.circleBitMap(zoom);
                //1:transform当中处理完图片之后，需要调用recylce方法回收
                source.recycle();
                return circleBitMap;
            }

            @Override
            public String key() {
                //2:重写key方法的返回值，不能是null
                return "";
            }
        }).into(imgHead);

    }
}
