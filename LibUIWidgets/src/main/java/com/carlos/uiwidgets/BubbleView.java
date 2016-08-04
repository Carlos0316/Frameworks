package com.carlos.uiwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Carlos on 2016/8/4.
 */
public class BubbleView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private final Random mRandom;

    private int mBubbleNums;
    private Bubble[] mBubbles;

    private float mBubbleMinRadius;
    private float mBubbleMaxRadius;

    private float mMinSpeed;
    private float mMaxSpeed;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    // ===========================================================
    // Constructors
    // ===========================================================

    public BubbleView(Context context) {
        this(context, null);
    }

    public BubbleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRandom = new Random();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BubbleView);
        try {
            mBubbleNums = a.getInteger(R.styleable.BubbleView_bubbleNumber, 10);

            mBubbleMinRadius = a.getFloat(R.styleable.BubbleView_minRadius, 10.0f);
            mBubbleMaxRadius = a.getFloat(R.styleable.BubbleView_maxRadius, 20.0f);

            mMinSpeed = a.getFloat(R.styleable.BubbleView_minSpeed, 5.0f);
            mMaxSpeed = a.getFloat(R.styleable.BubbleView_maxSpeed, 10.0f);
        } finally {
            a.recycle();
        }
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private class Bubble {
        private int mCenterX;
        private int mCenterY;
        private int mSpeedX;
        private int mSpeedY;

        private Bitmap mBitmap;

        public void move() {

        }
    }

}
