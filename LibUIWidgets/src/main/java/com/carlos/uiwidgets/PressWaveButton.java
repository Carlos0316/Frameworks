package com.carlos.uiwidgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 点击会有波纹效果的按钮
 */
public class PressWaveButton extends Button {

    // ===========================================================
    // Constants
    // ===========================================================

    private final static int MSG_INVALIDATE_VIEW = 0x01;

    // ===========================================================
    // Fields
    // ===========================================================

    private int mWaveColor;

    private float mPressX;
    private float mPressY;

    private float mMaxWaveRadius;
    private float mWaveRadius;
    private int mAlpha = 100;
    private int mAlphaDifValue;

    private Paint mWavePaint;

    private Timer mTimer;
    private TimerTask mTask;

    private Handler mHandler;

    // ===========================================================
    // Constructors
    // ===========================================================

    public PressWaveButton(Context context) {
        super(context);
        init(context, null);
    }

    public PressWaveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PressWaveButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawWave(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPressX = event.getX();
            mPressY = event.getY();
            onStopDrawWave();
            onStartDrawWave();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        onStopDrawWave();
        release();
        super.onDetachedFromWindow();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PressWaveButton);

        mWaveColor = a.getColor(R.styleable.PressWaveButton_waveColor, Color.BLUE);
        mMaxWaveRadius = a.getDimension(R.styleable.PressWaveButton_maxWaveRadius, 100.0f);

        a.recycle();

        mWavePaint = new Paint();
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setAntiAlias(true);
        mWavePaint.setColor(mWaveColor);

        int count = (int) mMaxWaveRadius / 5;
        mAlphaDifValue = 100 / count;

        mHandler = new InvalidateHandler(this);
    }

    private void drawWave(Canvas canvas) {
        if (mWaveRadius > mMaxWaveRadius) {
            onStopDrawWave();
            return ;
        }

        if (canvas != null && mPressX > 0 && mPressY > 0) {
            mWaveRadius += 5;
            mAlpha -= mAlphaDifValue;
            if (mAlpha < 0) {
                mAlpha = 0;
            }

            mWavePaint.setAlpha(mAlpha);
            canvas.drawCircle(mPressX, mPressY, mWaveRadius, mWavePaint);
        }
    }

    private void onStartDrawWave() {
        mTask = new TimerTask() {
            @Override
            public void run() {
                if (mHandler != null) {
                    mHandler.sendEmptyMessage(MSG_INVALIDATE_VIEW);
                }
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 0, 30);
    }

    private void onStopDrawWave() {
        mHandler.removeMessages(MSG_INVALIDATE_VIEW);
        if (mTimer != null) {
            if (mTask != null) {
                mTask.cancel();
            }
            mTimer.cancel();
        }

        mWaveRadius = 0;
        mAlpha = 100;
    }

    private void release() {
        mWavePaint.reset();
        mWavePaint = null;
        mTimer = null;
        mTask = null;
        mHandler = null;
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
