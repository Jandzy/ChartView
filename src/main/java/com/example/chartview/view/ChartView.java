package com.example.chartview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.chartview.R;

/**
 * Created by jrazy on 2016/8/25.
 */
public class ChartView extends View implements ValueAnimator.AnimatorUpdateListener{

    //弧形
    public final static int ARC = 1;
    //圆形
    public final static int CHART = 0;

    private float mRadius;
    private int mPieModel;
    private boolean mShowAnim;

    //画笔
    private Paint mPaint;
    private RectF voal;
    //起始位置
    private float startPosition;

    private float[] mValues;
    private int[] mColors;

    private Handler mHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };

    public ChartView(Context context) {
        this(context,null);
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("chartview","chartview");

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        mRadius = typedArray.getDimension(R.styleable.ChartView_radius,40);
        mPieModel = typedArray.getInt(R.styleable.ChartView_pieModel,0);
        mShowAnim = typedArray.getBoolean(R.styleable.ChartView_showAnim,false);
        typedArray.recycle();

        mPaint = new Paint();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("measure","measure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("ondraw","ondraw");
        voal = new RectF();
        voal.left = getWidth()/2-mRadius;
        voal.top = getHeight()/2-mRadius;
        voal.right = getWidth()/2+mRadius;
        voal.bottom = getHeight()/2+mRadius;

        //startPostition要在ondrew方法里面处事值，如果在构造方法中的话，activity在onrestart方法之后view会重绘调用ondraw方法。
        startPosition = 270;



        for(int i=0;i<mValues.length;i++){
            mPaint.setColor(mColors[i%mColors.length]);
            if(i != 0){
                startPosition += mValues[i-1];
            }
            canvas.drawArc(voal,startPosition,mValues[i],true,mPaint);
        }


        if(mPieModel == ARC){
            mPaint.setColor(Color.WHITE);
            canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius/2,mPaint);
        }

    }

    public void setValues(float... values){
        double sum = 0;
        mValues = new float[values.length];
        for(float value: values){
            sum += value;
        }
        for(int i=0; i<values.length; i++){
            mValues[i] =(float)(values[i]/sum)*360;
        }

    }

    public void setColors(int... colors){
        mColors = colors;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float sum=0;
        for(float value:mValues){
            sum=value;
            value=0;
            if(value<sum){
                value +=1;
                invalidate();
            }

        }
    }
}
