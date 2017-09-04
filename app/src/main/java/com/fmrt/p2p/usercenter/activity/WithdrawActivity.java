package com.fmrt.p2p.usercenter.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.fmrt.p2p.R;
import com.fmrt.p2p.base.BaseActivity;
import com.fmrt.p2p.util.ToastUtil;

/**
 * 提现Activity
 */

public class WithdrawActivity extends BaseActivity implements View.OnClickListener
{
    //公共头布局
    private ImageView iv_left;
    private TextView tv_title;
    private ImageView iv_right;

    private EditText mEdNumber;
    private ImageButton mIbClearNumber;
    private Button mBtnNext;


    @Override
    protected int getLayoutId()
    {
        //指定布局
        return R.layout.activity_withdraw;
    }

    //初始化控件
    @Override
    protected void initView()
    {
        //初始化Title
        initTitle();

        mEdNumber = (EditText) findViewById(R.id.edNumber);
        mIbClearNumber = (ImageButton) findViewById(R.id.ibClearNumber);

        mBtnNext = (Button) findViewById(R.id.btnNext);

    }

    private void initTitle()
    {
        iv_left= (ImageView) findViewById(R.id.iv_left);
        tv_title= (TextView) findViewById(R.id.tv_title);
        iv_right= (ImageView) findViewById(R.id.iv_right);
        tv_title.setText("提现");
        iv_left.setVisibility(View.VISIBLE);
        iv_right.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData()
    {

    }

    //初始化监听
    @Override
    protected void initListener()
    {
        iv_left.setOnClickListener(this);
        mBtnNext.setOnClickListener(this);
        mIbClearNumber.setOnClickListener(this);

        mEdNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.length() > 0) {
                    mIbClearNumber.setVisibility(View.VISIBLE);
                } else {
                    mIbClearNumber.setVisibility(View.INVISIBLE);
                }
                if (s.length() > 0 && Float.parseFloat(s.toString()) > 0.001) {
                    mBtnNext.setEnabled(true);
                } else {
                    mBtnNext.setEnabled(false);
                }

            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_left:
                //结束当前设置Activity
                closeCurrent();
                break;
            case R.id.btnNext:
                //提现
                clicktx();
                break;
        }
    }

    private void clicktx()
    {
        ToastUtil.getInstance().showToast( "你的提现请求已被成功受理。。。", Toast.LENGTH_SHORT);
    }
}
