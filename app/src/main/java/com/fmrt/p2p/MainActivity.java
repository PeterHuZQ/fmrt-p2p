package com.fmrt.p2p;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.RadioGroup;

import com.fmrt.p2p.invest.InvestFragment;
import com.fmrt.p2p.loan.LoanFragment;
import com.fmrt.p2p.transfer.TransferFragment;
import com.fmrt.p2p.usercenter.UserCenterFragment;

public class MainActivity extends FragmentActivity
{

    private RadioGroup rg_main;

    private InvestFragment investFragment;
    private TransferFragment transferFragment;
    private LoanFragment loanFragment;
    private UserCenterFragment userCenterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1、初始化控件
        initView();
        //2、初始化数据
        initData();
        //3、初始化监听
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        rg_main = (RadioGroup)findViewById(R.id.rg_main);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        // 创建4个fragment对象
        investFragment = new InvestFragment();
        transferFragment = new TransferFragment();
        loanFragment =new LoanFragment();
        userCenterFragment=new UserCenterFragment();
    }
    /**
     * 初始化监听
     */
    private void initListener() {
        //RadioGroup的选择事件
        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                Fragment fragment = null;

                switch (checkedId) {
                    // 投资页面
                    case R.id.rb_invest:
                        fragment = investFragment;
                        break;

                    // 转让页面
                    case R.id.rb_transfer:
                        fragment = transferFragment;
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
        rg_main.check(R.id.rb_invest);
    }

    // 实现fragment切换的方法
    private void switchFragment(Fragment fragment) {
        //fragment_smartService.得到FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager();
        //2.开启事务,替换主页内容,事务提交
        fragmentManager.beginTransaction().replace(R.id.fl_main, fragment).commit();
    }
}
