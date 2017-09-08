package com.fmrt.p2p.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fmrt.p2p.R;


/**
 * 自定义控件：圆环绘制
 * 获取我们自定义属性attrs.xml
 * 重现OnDraw()方法绘制我们的控件
 * 圆环的组成：外层圆(drawCircle)+中间百分比文字(drawText)+不断变化进度的弧形圈(drawArc)
 */

public class RoundProgress extends View
{
    //画笔
    private Paint paint =new Paint();
    //外层圆的颜色
    private int roundColor;
    //弧形进度圈的颜色
    private int roundProgressColor;
    //圆环的宽度
    private int textColor;
    //中间百分比文字的颜色
    private float textSize;
    //中间百分比文字的大小
    private float roundWidth;
    //最大进度
    private int max = 100;
    private int progress = 50;

    public RoundProgress(Context context)
    {
        this(context,null);
    }

    public RoundProgress(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    public RoundProgress(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        //取到fragment_home.xml里RoundProgress的属性
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //外层圆的颜色，Color.RED代表默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgress_roundColor, Color.RED);
        //圆环进度的颜色
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.GREEN);
        //圆环的宽度
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgress_roundWidth, 5);
        //中间进度百分比文字字符串的颜色
        textColor = mTypedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        //中间进度百分比的字符串的字体大小
        textSize = mTypedArray.getDimension(R.styleable.RoundProgress_textSize, 15);

        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas){
        //第一步：绘制一个最外层的圆
        paint.setColor(roundColor);//设置外层圆的颜色
        //设置圆环的宽度
        paint.setStrokeWidth(roundWidth);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //圆心位置
        int center =getWidth()/2;
        //半径
        int radius = (int) (center-roundWidth/2);
        //画圆
        canvas.drawCircle(center,center,radius,paint);

        //第二步：绘制正中间的文本
        //测量画笔上的文本宽度
        float textWidth = paint.measureText(progress + "%");
        paint.setColor(textColor);
        paint.setTextSize(textSize);//去掉画笔的宽度
        paint.setStrokeWidth(0);
        //canvas.drawText(progress+"%",sx,sy,paint);
        canvas.drawText(progress + "%", center - textWidth / 2, center + textSize / 2, paint);


        //第三步：绘制进度弧形圈
        /**
         * drawArc()方法的参数解释：
         * oval：                    绘制弧形圈所包含的矩形范围轮廓
         * 0：                      开始的角度
         * 360 * progress / max：   扫描过的角度
         * false：                  是否包含圆心
         * paint：                  绘制弧形时候的画笔
         */
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius);
        //设置弧形进度圈的颜色
        paint.setColor(roundProgressColor);
        //设置进度弧形圈的宽度，必须保持和外层圆的StrokeWidth一致，
        // 确保弧形圈绘制的时候覆盖的范围就是外层圆的宽度
        paint.setStrokeWidth(roundWidth);
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, 0, 360 * progress / max, false, paint);


    }
    //第四步：最后提供一个设置进度，根据进度重新绘制圆环的方法setProgress()
    //setProgress是这个RoundProgress控件提供给外部调用的方法
    public void setProgress(int progress){
        this.progress = progress;
        if(progress>100){
            this.progress = 100;
        }
        //重新绘制，调用postInvalidate()方法会导致onDraw()方法重新执行
        postInvalidate();
    }
}
