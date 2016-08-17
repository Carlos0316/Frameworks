package com.study.carlos.frameworks.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.carlos.uiwidgets.manager.BlurImageManager;
import com.study.carlos.frameworks.R;

/**
 * Created by Carlos on 2016/8/17.
 */
public class BlurImageTestActivity extends Activity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private ImageView mIvBlured;
    private ImageView mIvOrigin;

    private SeekBar mSeekBar;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_image_test);

        mIvBlured = (ImageView) findViewById(R.id.blur_image_test_iv_blured);
        mIvOrigin = (ImageView) findViewById(R.id.blur_image_test_iv_origin);
        mSeekBar = (SeekBar) findViewById(R.id.blur_image_test_sb);

        Bitmap src = BitmapFactory.decodeResource(getResources(), R.mipmap.bg);
        Bitmap out = BlurImageManager.doBlur(this, src);

        mIvBlured.setImageBitmap(out);
        mIvOrigin.setImageBitmap(src);

        mIvBlured.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mIvOrigin.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mIvOrigin.setAlpha(0.0f);

        mSeekBar.setMax(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                mIvOrigin.setAlpha(0.01f * progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================    

}
