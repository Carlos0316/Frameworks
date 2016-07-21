package com.study.carlos.frameworks;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by Carlos on 2016/7/12.
 */
public class VideoRecordActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback{

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;

    private File mVideoFile;
    private MediaRecorder mMediaRecorder;
    private Camera mCamera;

    private Button mStartBtn;
    private Button mStopBtn;

    private boolean isRecording = false;

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
        setContentView(R.layout.activity_media_record);
        initView();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_record_btn_start:
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(this, "SD卡不存在，請插入SD卡！", Toast.LENGTH_LONG).show();
                }

                try {
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                    mVideoFile = File.createTempFile("myvideo", ".mp4", dir);
                    mMediaRecorder = new MediaRecorder();
                    mMediaRecorder.reset();
                    if (mCamera != null) {
                        mMediaRecorder.setCamera(mCamera);
                    }
                    //設置從麥克風採集聲音
                    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //設置從攝像頭採集圖像
                    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                    //設置視頻文件的輸出格式
                    //必須在設置聲音編碼格式、圖像編碼格式之前設置
                    mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                    //設置聲音編碼格式
                    mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //設置圖像編碼格式
                    mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
                    //每秒4幀
                    mMediaRecorder.setVideoFrameRate(16);
                    mMediaRecorder.setVideoEncodingBitRate(1 * 1024 * 512);
                    mMediaRecorder.setOutputFile(mVideoFile.getAbsolutePath());
                    //指定使用SurfaceView來預覽視頻
                    mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                    mMediaRecorder.prepare();
                    mMediaRecorder.start();

                    mStartBtn.setEnabled(false);
                    mStopBtn.setEnabled(true);
                    isRecording = true;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.video_record_btn_stop:
                if (isRecording) {
                    mMediaRecorder.stop();
                    mMediaRecorder.release();
                    mMediaRecorder = null;
                    mStartBtn.setEnabled(true);
                    mStopBtn.setEnabled(false);
                }
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        freeCameraResource();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    private void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.video_record_surfaceview);
        mStartBtn = (Button) findViewById(R.id.video_record_btn_start);
        mStopBtn = (Button) findViewById(R.id.video_record_btn_stop);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        mSurfaceHolder.setKeepScreenOn(true);

        mStopBtn.setEnabled(false);

        mStartBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);
    }

    private void initCamera() {
        if (mCamera != null) {
            freeCameraResource();
        }
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            e.printStackTrace();
            freeCameraResource();
        }
        if (mCamera == null)
            return;

        Camera.Parameters params = mCamera.getParameters();
        params.set("orientation", "portrait");
        mCamera.setParameters(params);
        mCamera.setDisplayOrientation(90);
        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        mCamera.unlock();
    }

    private void freeCameraResource() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
