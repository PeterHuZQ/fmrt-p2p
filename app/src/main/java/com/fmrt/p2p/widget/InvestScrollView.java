package com.fmrt.p2p.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.fmrt.p2p.common.AppManager;

public class InvestScrollView extends ScrollView {
    public InvestScrollView(Context context) {
        super(context);

    }

    public InvestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public InvestScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)   //这个方法如果返回 true的话 两个手指移动，启动一个按下的手指的移动不能被传播出去。  
    {
        super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)//这个方法如果 true 则整个Activity 的 onTouchEvent() 不会被系统回调  
    {
        super.onTouchEvent(event);

        InputMethodManager imm = (InputMethodManager) AppManager.getInstance().currentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(AppManager.getInstance().currentActivity().getWindow().getDecorView().getWindowToken(), 0);
        }
        return true;
    }
}
