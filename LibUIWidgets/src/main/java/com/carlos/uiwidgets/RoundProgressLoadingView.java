/**
 * Copyright (C) © 2014 深圳市掌玩网络技术有限公司
 * Frameworks
 * RoundLoadingView.java
 */
package com.carlos.uiwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.lang.ref.WeakReference;


public class RoundProgressLoadingView extends View {

    // ===========================================================
    // Constants
    // ===========================================================

    private final static int MSG_INVALIDATE_VIEW = 0x01;

    // ===========================================================
    // Fields
    // ===========================================================

    private int mInnerColor;
    private int mProgressColor;
    private int mProgressWidth;
    private float mMaxProgress;
    private float mProgress;

    private Paint mInnerPaint;
    private Paint mProgressPaint;

    private int mRadius;
    private int mCenterX;
    private int mCenterY;
    private RectF mRectF;

    private String mText;
    private int mTextColor;
    private int mTextSize;

    private Handler mHandler;

    // ===========================================================
    // Constructors
    // ===========================================================

    public RoundProgressLoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public RoundProgressLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundProgressLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
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

        if (getWidth() > 0 && getHeight() > 0) {
            int halfWidth = getWidth() >> 1;
            int halfHeight = getHeight() >> 1;

            mCenterX = halfWidth;
            mCenterY = halfHeight;

            int diameter = Math.min(getWidth(), getHeight());
            mRadius = (diameter >> 1) - mProgressWidth;
            int half = mProgressWidth >> 1;

            int left = halfWidth - mRadius - half;
            int top = halfHeight - mRadius - half;
            int right = halfWidth + mRadius + half;
            int bottom = halfHeight + mRadius + half;
            mRectF = new RectF(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInnerBackground(canvas);
        drawProgress(canvas);
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressLoadingView);

        mInnerColor = a.getColor(R.styleable.RoundProgressLoadingView_innerColor, Color.parseColor("#189382"));
        mProgressColor = a.getColor(R.styleable.RoundProgressLoadingView_progressColor, Color.parseColor("#1fbba6"));
        mProgressWidth = a.getDimensionPixelOffset(R.styleable.RoundProgressLoadingView_progressWidth, 20);

        mMaxProgress = a.getInteger(R.styleable.RoundProgressLoadingView_maxProgress, 100);
        mProgress = a.getInteger(R.styleable.RoundProgressLoadingView_progress, 50);

        a.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.FILL);

        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);
        mProgressPaint.setStrokeCap(Paint.Cap.BUTT);

        mHandler = new InvalidateHandler(this);
    }

    private void drawInnerBackground(Canvas canvas) {
        if (canvas != null) {
            canvas.drawCircle(mCenterX, mCenterY, mRadius, mInnerPaint);
        }
    }

    private void drawProgress(Canvas canvas) {
        if (canvas != null) {
            canvas.drawArc(mRectF, -90, 180, false, mProgressPaint);
        }
    }

    public void setProgress(float progress) {
        if (progress >= 0.0f && progress <= mMaxProgress) {
            mProgress = progress;

            if (mHandler != null) {
                mHandler.sendEmptyMessage(MSG_INVALIDATE_VIEW);
            }
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class InvalidateHandler extends Handler {

        private WeakReference<View> mRef;

        public InvalidateHandler(View v) {
            mRef = new WeakReference<View>(v);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mRef != null && mRef.get() != null) {
                mRef.get().invalidate();
            }
        }
    }

}
