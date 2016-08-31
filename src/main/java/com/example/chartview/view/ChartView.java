package com.example.chartview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.chartview.R;

/**
 * desc 饼状图
 */
public class ChartView extends View {

    //弧形
    public final static int ARC = 1;
    //圆形
    public final static int CHART = 0;

    //半径
    private float mRadius;
    /**
     * mPieModel
     * enum  ARC 空心圆   CHART 实心圆
     */
    private int mPieModel;

    /**
     * mShowAnim  是否显示动画 boolean
     * 动画的原理是通过一点点改变drawArc的方法中sweepAngle的值，然后通过 postInvalidate()来重绘view实现。
     */
    private boolean mShowAnim;

    //画笔
    private Paint mPaint;
    private RectF voal;

    /**
     * startPosition 画笔的起始位置，0的时候是三点钟位置。
     */
    private float startPosition;

    /**
     * mValues 将传过来的值按照百分比分成valus.length长度存放（也就是分成几份的圆）
     */
    private float[] mValues;
    private int[] mColors;

    /**
     * animValues 动态存放当前绘制了值
     */
    private float[] animValues;
    private int mAnimCount;

    private int count;//数组个数，几份

    private float touchAngle;
    private float[] angles;
    private int select;

    public ChartView(Context context) {
        this(context, null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        mRadius = typedArray.getDimension(R.styleable.ChartView_radius, 40);
        mPieModel = typedArray.getInt(R.styleable.ChartView_pieModel, 0);
        mShowAnim = typedArray.getBoolean(R.styleable.ChartView_showAnim, false);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔消除锯齿

        select = -1;


    }


    @Override
    protected void onDraw(Canvas canvas) {
        voal = new RectF();
        voal.left = getWidth() / 2 - mRadius;
        voal.top = getHeight() / 2 - mRadius;
        voal.right = getWidth() / 2 + mRadius;
        voal.bottom = getHeight() / 2 + mRadius;

        //startPostition要在ondrew方法里面处事值，如果在构造方法中的话，activity在onrestart方法之后view会重绘调用ondraw方法。
        startPosition = 270;


        for (int i = 0; i < mValues.length; i++) {
            mPaint.setColor(mColors[i % mColors.length]);

            Log.e("slelct","========"+select);

            if(select == i){
                voal.left = getWidth()/2-mRadius+20;
                voal.top = getHeight()/2-mRadius-20;
                voal.right = getWidth()/2+mRadius+20;
                voal.bottom = getHeight()/2+mRadius-20;
            }else{
                voal.left = getWidth() / 2 - mRadius;
                voal.top = getHeight() / 2 - mRadius;
                voal.right = getWidth() / 2 + mRadius;
                voal.bottom = getHeight() / 2 + mRadius;
            }

            if (i != 0) {
                startPosition += mValues[i - 1];
            }
            canvas.drawArc(voal, startPosition, mValues[i], true, mPaint);
        }


        if (mPieModel == ARC) {
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius / 2, mPaint);
        }

        /**
         * 为了动画效果，需要先确定mValues的length，然后逐个赋值，先给mValues[0]赋值，如果mValues[0] 等于对应的百分比之后
         * 再给mValues[1]赋值，以此类推。
         * 赋值完需要return,否则循环赋值.
         */
        if (count <= mAnimCount-1) {
            if (mValues[count] + 10 > animValues[count]) {
                mValues[count] = animValues[count];
                invalidate();
                count++;
            }else{
                if (mValues[count] < animValues[count]) {
                    mValues[count] += 10;
                    invalidate();
                }
            }


        }

    }

    public void setColors(int... colors) {
        mColors = colors;
    }

    public void setValues(float... values) {
        double sum = 0;
        mValues = new float[values.length];
        for (float value : values) {
            sum += value;
        }
        mAnimCount = values.length;
        float angleSum = 0;
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                mValues[i] = 360 - angleSum;
            } else {
                mValues[i] = (float) (values[i] / sum) * 360;
                angleSum += mValues[i];
            }
        }

        angles = new float[values.length];
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                angles[i] = mValues[i];
            } else {
                angles[i] = mValues[i]+angles[i-1];
            }
        }

        if (mShowAnim) {
            count = 0;
            animValues = mValues;
            mValues = new float[mValues.length];
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                touchAngle = 0;
                //第一象限
                if (x > getWidth() / 2 && x <= getWidth() / 2 + mRadius && y < getHeight() / 2 && y >= getHeight() / 2 - mRadius) {
                    touchAngle = (float) (Math.atan((x - getWidth() / 2) / (getHeight()/2-y))/Math.PI * 180);
                }
                //第二象限
                if (x > getWidth() / 2 && x <= getWidth() / 2 + mRadius && y > getHeight() / 2 && y <= getHeight() / 2 + mRadius) {
                    touchAngle = 90 + (float) (Math.atan((y - getHeight() / 2) / (x - getWidth() / 2) )/Math.PI * 180);
                }
                //第三象限
                if (x < getWidth() / 2 && x >= getWidth() / 2 - mRadius && y > getHeight() / 2 && y <= getHeight() / 2 + mRadius) {
                    touchAngle = 180 + (float) (Math.atan((getWidth() / 2-x) / (y - getHeight() / 2))/Math.PI * 180);
                }
                //第四象限
                if (x < getWidth() / 2 && x >= getWidth() / 2 - mRadius && y < getHeight() / 2 && y >= getHeight() / 2 - mRadius) {
                    touchAngle = 270 + (float) (Math.atan((getHeight() / 2-y) / (getWidth() / 2-x))/Math.PI * 180);
                }

                select = -1;
                invalidate();
                for (int i = 0; i < angles.length; i++) {
                    if (i == 0) {
                        if (0 < touchAngle && touchAngle < angles[i]) {
                            select = i;
                            invalidate();
                        }
                    }
                    if (i > 0) {
                        if (touchAngle > angles[i - 1] && touchAngle < angles[i]) {
                            select = i;
                            invalidate();
                        }
                    }
                }

                break;
        }
        return super.onTouchEvent(event);
    }
}
