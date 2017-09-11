package com.fmrt.p2p.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fmrt.p2p.R;
import com.fmrt.p2p.util.UIUtils;

/**
 *募集进度条
 */

public class TCustomProcessBar extends View {

    private int mTotalWidth, mTotalHeight;
    private float mProcess; // 进度，在0-1之间
    private Paint mLinePaint, mRectPaint, mBackgroundPaint;

    private int mLineColor;// 进度条的颜色
    private int mBgColor;// 进度条背景色
    private int mTextSize;// 进度显示文字的大小

    private int mEndColor = Color.parseColor("#cccccc"); // 进度条为100%时显示的颜色
    private int mLineStroke = 10; // 进度条的粗细
    private int mRectStroke = 4; // 矩形粗细

    public TCustomProcessBar(Context context) {
        super(context);
        initValue();
    }

    private void initValue() {
        // 进度默认为50%
        mProcess = (float) 0.8;
        // 直线画笔
        mLinePaint = new Paint();
        mLineColor = getResources().getColor(R.color.theme_color);
        mLinePaint.setAntiAlias(true);

        // 字体大小
        mTextSize = 20;

        mLineStroke = UIUtils.dp2px(3);
        mRectStroke = UIUtils.dp2px(1);
        mRectPaint = new Paint();// 矩形画笔
        mRectPaint.setAntiAlias(true);
        mRectPaint.setStyle(Paint.Style.STROKE);

        mBackgroundPaint = new Paint();// 背景画笔
        mBgColor = Color.parseColor("#cccccc");
        mBackgroundPaint.setAntiAlias(true);
    }

    public TCustomProcessBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValue();
    }

    public TCustomProcessBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mTotalWidth = MeasureSpec.getSize(widthMeasureSpec);
        mTotalHeight = MeasureSpec.getSize(heightMeasureSpec);

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            mTotalHeight = mTextSize + 10 + getPaddingBottom() + getPaddingBottom();
        }
        setMeasuredDimension(mTotalWidth, mTotalHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w - getPaddingLeft() - getPaddingRight();
        mTotalHeight = h - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mLinePaint.setStrokeWidth(mLineStroke);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setTextSize(mTextSize);
        mRectPaint.setColor(mLineColor);
        mRectPaint.setStrokeWidth(mRectStroke);
        mBackgroundPaint.setStrokeWidth(mLineStroke);
        mBackgroundPaint.setColor(mBgColor);
        if (mProcess >= 1) {
            mLinePaint.setColor(mEndColor);
            mRectPaint.setColor(mEndColor);
            mBackgroundPaint.setColor(mEndColor);
        }

        String processStr = (int) (mProcess * 100) + "%";
        float textWidth = mLinePaint.measureText("100%");// 文字的长度
        float textWidth2 = mLinePaint.measureText(processStr);// 文字的长度
        Paint.FontMetricsInt fm = mLinePaint.getFontMetricsInt();
        float textHeight = fm.descent - fm.ascent;
        float lineStartX = getPaddingLeft();
        float lineStratY = mTotalHeight / 2;
        float lineStopX = (mTotalWidth - textWidth - 10) * mProcess;
        float lineStopY = lineStratY;

        canvas.drawLine(lineStartX, lineStratY, lineStopX, lineStopY, mBackgroundPaint);
        canvas.drawLine(lineStopX + textWidth + 10, lineStratY, mTotalWidth, lineStopY, mBackgroundPaint);
        canvas.drawLine(lineStartX, lineStratY, lineStopX, lineStopY, mLinePaint);
        canvas.drawText(processStr, lineStopX + (textWidth - textWidth2) / 2 + 6, lineStratY + textHeight / 3,
                mLinePaint);
        RectF rectlRectF = new RectF(lineStopX + 2, lineStratY - textHeight / 3 - 4, lineStopX + textWidth + 12,
                lineStopY + textHeight / 3 + 6);
        canvas.drawRoundRect(rectlRectF, UIUtils.dp2px(8), UIUtils.dp2px(8), mRectPaint);

    }

    public float getmProcess() {
        return mProcess;
    }

    // 设置进度条
    public void setProcess(float mProcess) {
        this.mProcess = mProcess;
    }

    public int getProcessColor() {
        return mLineColor;
    }

    // 设置进度条颜色
    public void setProcessColor(int mLineColor) {
        this.mLineColor = mLineColor;
        invalidate();
    }

    public int getmBgColor() {
        return mBgColor;
    }

    // 设置进度条背景颜色
    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        invalidate();
    }

    public int getmTextSize() {
        return mTextSize;
    }

    // 设置字体颜色
    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        invalidate();
    }

    public int getmEndColor() {
        return mEndColor;
    }

    // 设置进度条为100%的颜色
    public void setEndColor(int mEndColor) {
        this.mEndColor = mEndColor;
        invalidate();
    }

    public int getmLineStroke() {
        return mLineStroke;
    }

    // 设置进度条粗细
    public void setLineStroke(int mLineStroke) {
        this.mLineStroke = mLineStroke;
        invalidate();
    }

    public int getmRectStroke() {
        return mRectStroke;
    }

    // 设置矩形粗细
    public void setRectStroke(int mRectStroke) {
        this.mRectStroke = mRectStroke;
    }
}
