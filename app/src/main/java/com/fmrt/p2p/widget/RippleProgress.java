package com.fmrt.p2p.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.fmrt.p2p.R;
import com.fmrt.p2p.util.UIUtils;


/**
 * @author LiHongCheng
 * @version V1.0
 * @Package test.com.spherical_ripples.View
 * @Description: 水波纹进度球
 * @mail diosamolee2014@gmail.com
 * @date 2017/9/6 14:39
 */
public class RippleProgress extends View {
    // 波纹颜色
    private static final int WAVE_PAINT_COLOR = 0x880000aa;
    // y = Asin(wx+b)+h
    private static final float STRETCH_FACTOR_A = 50;
    private static final int OFFSET_Y = 0;
    // 第一条水波移动速度
    private static final int TRANSLATE_X_SPEED_ONE = 3;
    // 第二条水波移动速度
    private static final int TRANSLATE_X_SPEED_TWO = 2;
    private final Paint mRingPaint;
    private final Paint mCirclePaint;
    private final Context mContext;
    //内环的颜色
    private int mCircleColor = Color.WHITE;
    //外环的颜色
    private int mRingColor1 = Color.MAGENTA;
    private int mRingColor = getResources().getColor(R.color.theme_color);

    private float mCycleFactorW;

    private int mTotalWidth, mTotalHeight;
    private float[] mYPositions;
    private float[] mResetOneYPositions;
    private float[] mResetTwoYPositions;
    private int mXOffsetSpeedOne = 50;
    private int mXOffsetSpeedTwo = 50;
    private int mXOneOffset;
    private int mXTwoOffset;

    private Paint mWavePaint;
    private DrawFilter mDrawFilter;
    private float oneSpeed = 0;
    private float twoSpeed = 0;

//    private String flowLeft = "新手专项";
    private int mWidth;
    private int mHeight;
    private boolean mStarted;
    private Handler mHandler;
    private int mRaduis;

    public RippleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // 将dp转化为px，用于控制不同分辨率上移动速度基本一致
        mXOffsetSpeedOne = UIUtils.dp2px(TRANSLATE_X_SPEED_ONE);
        mXOffsetSpeedTwo = UIUtils.dp2px(TRANSLATE_X_SPEED_TWO);

        // 初始绘制波纹的画笔
        mWavePaint = new Paint();
//         去除画笔锯齿
        mWavePaint.setAntiAlias(true);
        // 设置风格为实线
        mWavePaint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
        mWavePaint.setColor(mRingColor);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

//        // 初始绘制波纹的画笔
//        mWavePaint = new Paint();
////         去除画笔锯齿
//        mWavePaint.setAntiAlias(true);
//        // 设置风格为实线
//        mWavePaint.setStyle(Paint.Style.FILL);
//        // 设置画笔颜色
//        mWavePaint.setColor(mRingColor);

        //外圆的画笔
        mRingPaint = new Paint();
        mRingPaint.setColor(mRingColor);
        mRingPaint.setAlpha(255);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setAntiAlias(true);
        mRingPaint.setStrokeWidth(mRingSTROKEWidth);
        //内环的画笔
        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(mCircleSTROKEWidth);

//        leftPaint = new Paint();
//        leftPaint.setColor(Color.BLACK);
//        leftPaint.setStyle(Paint.Style.FILL);
//        leftPaint.setAntiAlias(true);
//        leftPaint.setTextSize(PxUtils.spToPx(18, mContext));

        mHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 0) {
                    invalidate();
                    if (mStarted) {
                        // 不断发消息给自己，使自己不断被重绘
                        mHandler.sendEmptyMessageDelayed(0, 30l);
                    }
                }
            }
        };


    }

    //外环的宽度
    private int mRingSTROKEWidth = 5;
    //内环环的宽度
    private int mCircleSTROKEWidth = 10;

    private boolean isInitialization = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (!mStarted) {
//            return;
//        }
        //设置速度
//        oneSpeed+=1;
//        twoSpeed+=1;

        if (!isInitialization) { //初始化一些参数提高效率
            mWidth = getWidth();
            mHeight = getHeight();
            mRaduis = Math.min(mWidth, mHeight);
            // 从canvas层面去除绘制时锯齿
            canvas.setDrawFilter(mDrawFilter);
            //裁剪成圆区域
            Path path = new Path();
            canvas.save();
            path.reset();
            canvas.clipPath(path);
            path.addCircle(mWidth / 2, mHeight / 2, mRaduis / 2, Path.Direction.CCW);
            canvas.clipPath(path, Region.Op.REPLACE);
//            float left = leftPaint.measureText(flowLeft);
//            canvas.drawText(flowLeft, mTotalWidth * 4 / 8 - left / 2,
//                    mTotalHeight * 3 / 8, leftPaint);
            //画出球
            canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2, mRaduis / 2-mRingSTROKEWidth/2
                    , mRingPaint);
        }




        resetPositonY();
        for (int i = 0; i < mTotalWidth; i++) {

            // 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
            // 绘制第一条水波纹
            canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - mTotalHeight / 2, i,
                    mTotalHeight,
                    mRingPaint);

//            // 绘制第二条水波纹
            canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - mTotalHeight / 2, i,
                    mTotalHeight,
                    mWavePaint);
        }
        //画出白条
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2,
                mRaduis / 2 - mCircleSTROKEWidth, mCirclePaint);
        //如果是静止就不在进行移动改变了
        if (!mStarted) {

            return;
        }
//        // 改变两条波纹的移动点
        mXOneOffset += mXOffsetSpeedOne;
        mXTwoOffset += mXOffsetSpeedTwo;

        // 如果已经移动到结尾处，则重头记录
        if (mXOneOffset >= mTotalWidth) {
            mXOneOffset = 0;
        }
        if (mXTwoOffset > mTotalWidth) {
            mXTwoOffset = 0;
        }


//        // 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
//        postInvalidate();
    }

    private void resetPositonY() {
        // mXOneOffset代表当前第一条水波纹要移动的距离
        int yOneInterval = mYPositions.length - mXOneOffset;
        // 使用System.arraycopy方式重新填充第一条波纹的数据
        System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
        System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

        int yTwoInterval = mYPositions.length - mXTwoOffset;
        System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
                yTwoInterval);
        System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 记录下view的宽高
        mTotalWidth = w;
        mTotalHeight = h;
        // 用于保存原始波纹的y值
        mYPositions = new float[mTotalWidth];
        // 用于保存波纹一的y值
        mResetOneYPositions = new float[mTotalWidth];
        // 用于保存波纹二的y值
        mResetTwoYPositions = new float[mTotalWidth];

        // 将周期定为view总宽度
        mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

        // 根据view总宽度得出所有对应的y值
        for (int i = 0; i < mTotalWidth; i++) {
            mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
        }
    }

    /**
     * @category 开始波动
     */
    public void startWave() {
        if (!mStarted) {
            mStarted = true;
            mHandler.sendEmptyMessage(0);
        }
    }

    /**
     * @category 停止波动
     */
    public void stopWave() {
        if (mStarted) {
            mStarted = false;
            this.mHandler.removeMessages(0);
        }
    }
}
