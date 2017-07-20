package com.fmrt.p2p.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.index.IndexFragment;
import com.fmrt.p2p.loan.LoanFragment;
import com.fmrt.p2p.product.ProductFragment;
import com.fmrt.p2p.usercenter.UserCenterFragment;

public class MainActivity extends BaseActivity
{

    private RadioGroup rg_main;

    private IndexFragment IndexFragment;
    private ProductFragment ProductFragment;
    private LoanFragment loanFragment;
    private UserCenterFragment userCenterFragment;

    @Override
    public int getLayoutId()
    {
        //指定布局
        return R.layout.activity_main;
    }


    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        rg_main = (RadioGroup)findViewById(R.id.rg_main);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        // 创建4个fragment对象
        IndexFragment = new IndexFragment();
        ProductFragment = new ProductFragment();
        loanFragment =new LoanFragment();
        userCenterFragment=new UserCenterFragment();
    }
    /**
     * 初始化监听
     */
    @Override
    public void initListener() {
        //RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Fragment fragment = null;

                switch (checkedId) {
                    // 首页页面
                    case R.id.rb_index:
                        fragment = IndexFragment;
                        break;

                    // 理财页面
                    case R.id.rb_product:
                        fragment = ProductFragment;
                        break;

                    // 借款页面
                    case R.id.rb_loan:
                        fragment = loanFragment;
                        break;

                    // 个人中心页面
                    case R.id.rb_usercenter:
                        fragment = userCenterFragment;
                        break;
                }

                // 实现fragment切换的方法
                switchFragment(fragment);
            }
        });

        // 默认选择首页页面
        rg_main.check(R.id.rb_index);
    }

    // 实现fragment切换的方法
    private void switchFragment(Fragment fragment) {
        //fragment_smartService.得到FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开启事务,替换主页内容,事务提交
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }
}
