package com.fmrt.p2p.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.fmrt.p2p.util.UIUtils;

/**

 */

public class CustomProcessBar extends View {

    private int mTotalWidth, mTotalHeight;
    private float mProcess; // 进度条，在0-1之间
    private String mHintText;// 进度提示文字
    private Paint mLinePaint;
    private int mLineColor;
    private float mTextSize;
    private int mTextColor;
    private int mCircleColor;
    private Paint mOutterCirclePaint, mInnerCirclePaint, mTextPaint;
    private float mOutterCircleRadius;// 外圆半径
    private float mInnerCircleRadius;// 内圆半径
    private int currentProcessX = 0;

    public CustomProcessBar(Context context) {
        super(context);
        initValue();
    }

    private void initValue() {
        mHintText = "0%";
        mProcess = (float) 0;
        mLinePaint = new Paint();
        mLineColor = Color.parseColor("#ffffff");
        mLinePaint.setAntiAlias(true);

        mOutterCirclePaint = new Paint();
        mCircleColor = Color.parseColor("#ffffff");
        mOutterCirclePaint.setAntiAlias(true);

        mInnerCirclePaint = new Paint();
        mInnerCirclePaint.setAntiAlias(true);

        mTextColor = Color.parseColor("#ffffff");
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
    }

    public CustomProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public CustomProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTotalWidth = MeasureSpec.getSize(widthMeasureSpec);
        mTotalHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            mTotalHeight = 40;
        }
        setMeasuredDimension(mTotalWidth, mTotalHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();
        mOutterCircleRadius = (float) (mTotalHeight / 5);
        mInnerCircleRadius = mOutterCircleRadius / 2;
        mTextSize = (float) (mTotalHeight / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float lineStartX = 0;
        float lineStratY = mTotalHeight / 4 * 3;
        float lineStopX = (mTotalWidth - mOutterCircleRadius * 3) * mProcess + mOutterCircleRadius;

        float lineStopY = lineStratY;
        float circleLocationX = currentProcessX;
        float circleLocationY = lineStopY;

        mLinePaint.setStrokeWidth(5);
        mLinePaint.setColor(mLineColor);

        if (currentProcessX <= mOutterCircleRadius) {
            currentProcessX = (int) mOutterCircleRadius;
        }

        canvas.drawLine(lineStartX, lineStratY, currentProcessX, lineStopY, mLinePaint);
        mOutterCirclePaint.setColor(mCircleColor);
        mInnerCirclePaint.setColor(mCircleColor);
        mOutterCirclePaint.setAlpha(60);
        canvas.drawCircle(circleLocationX, circleLocationY, mInnerCircleRadius, mInnerCirclePaint);
        canvas.drawCircle(circleLocationX, circleLocationY, mOutterCircleRadius, mOutterCirclePaint);

        mTextPaint.setColor(mTextColor);
        String str = "已售" + (int) (mProcess * 100) + "%";
        float mTextWidth = mTextPaint.measureText(str);
        float textX = circleLocationX - mTextSize * (str.length() - 2) / 2;
        if (textX < 0) {
            textX = 0;
        }

        if ((textX + mTextWidth) > mTotalWidth) {
            textX = mTotalWidth - mTextWidth;
        }
        float textY = (float) (lineStratY - mTotalHeight / 2.5);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        canvas.drawText(str, textX, textY, mTextPaint);

        int dis = Math.abs(currentProcessX - (int) lineStopX);
        if (dis > 0 && dis < UIUtils.dp2px(5)) {
            currentProcessX = (int) lineStopX;
            postInvalidate();
            // canvas.drawLine(lineStartX, lineStratY, lineStopX, lineStopY,
            // mLinePaint);
        } else {
            if (currentProcessX < (int) lineStopX) {
                currentProcessX += UIUtils.dp2px(5);
                postInvalidate();
            } else if (currentProcessX > (int) lineStopX) {
                currentProcessX -= UIUtils.dp2px(5);
                postInvalidate();
            }
        }

    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
        invalidate();
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public float getProcess() {
        return mProcess;
    }

    public void setProcess(double mProcess) {
        this.mProcess = (float) mProcess;
        invalidate();
    }

    public int getmLineColor() {
        return mLineColor;
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        invalidate();
    }
}
