package com.fmrt.p2p.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 自定义ScrollView控件：实现滚动回弹到原来位置的效果
 * 首先，创建一个类，继承ScrollView ,重写ontouch事件
 * 让布局移动每一次拉动的Y轴一半的距离，然后松手滚动（携带动画）回到原来的位置
 */

public class MyScrollView extends ScrollView
{
    //要操作的子View布局
    private View innerView;
    //记录按下时的Y轴位置
    private float y;
    //记录拖动前正常状态的位置
    private Rect normal = new Rect();
    //动画回滚是否完成的标志
    private boolean animationFinish = true;

    public MyScrollView(Context context)
    {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //当指定的xml被解析完毕变成View对象后，回调onFinishInflate()
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        int childCount = getChildCount();
        if (childCount > 0) {
            //第一步：获取要操作的子view布局
            innerView = getChildAt(0);
        }
    }

    //第二步：重写ontouch事件监听
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView == null) {
            return super.onTouchEvent(ev);
        } else {
            //如果有操作，调用自定义的ontouch事件
            commonTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 自定义touch事件处理
     */
    private void commonTouchEvent(MotionEvent ev) {
        if (animationFinish) {//判断动画回滚是否完成，只有完成了才可以拖动
            //取到这个action
            int action = ev.getAction();
            switch (action) {
                //按下时
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY(); //记录当前Y轴位置
                    break;
                //移动过程
                case MotionEvent.ACTION_MOVE:
                    float preY = y == 0 ? ev.getY() : y;
                    float nowY = ev.getY();     //抬起时Y轴位置
                    int detailY = (int) (preY - nowY);
                    y = nowY;
                    //操作view进行拖动detailY的一半
                    if (isNeedMove()) {  //判断是否需要拖动
                        //布局改变位置之前，记录一下正常状态的位置
                        if (normal.isEmpty()) {
                            normal.set(innerView.getLeft(), innerView.getTop(), innerView.getRight(), innerView.getBottom());
                        }
                        //拖动view
                        innerView.layout(innerView.getLeft(), innerView.getTop() - detailY / 2, innerView.getRight(), innerView.getBottom() - detailY / 2);
                    }
                    break;
                //抬起时
                case MotionEvent.ACTION_UP:
                    y = 0;
                    //布局回滚到原来的位置
                    if (isNeedAnimation()) {//判断是否需要回滚
                        //动画回滚到原来位置
                        animation();
                    }
                    break;
            }
        }
    }
    /**
     * 判断是否需要拖动（上下移动才需要拖动，左右移动是不需要拖动的）
     */
    private boolean isNeedMove() {
        //高度偏移量
        int offset = innerView.getMeasuredHeight() - getHeight();
        //Y轴滚动距离
        int scrollY = getScrollY();

        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }
    /**
     * 判断是否需要回滚
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
    /**
     * 动画回滚到原来位置
     */
    private void animation() {
        //动画类TranslateAnimation
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - innerView.getTop());
        //设置动画持续时间
        ta.setDuration(200);
        //设置监听
        ta.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画回滚是否完成的标志，false表示正在回滚，此时是不能拖动的
                animationFinish = false;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //清除布局innerView里的动画
                innerView.clearAnimation();
                //让布局回到正常位置
                innerView.layout(normal.left, normal.top, normal.right, normal.bottom);
                normal.setEmpty();
                animationFinish = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //把动画应用于布局innerView身上
        innerView.startAnimation(ta);
    }
}
