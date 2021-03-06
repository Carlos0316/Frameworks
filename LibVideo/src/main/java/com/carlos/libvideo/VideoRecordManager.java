package com.carlos.libvideo;

import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Carlos on 2016/7/12.
 */
public class VideoRecordManager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private SurfaceHolder mSurfaceHolder;

    // ===========================================================
    // Constructors
    // ===========================================================

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public static VideoRecordManager getInstance() {
        return InstanceHolder.instance;
    }

    public void prepare(SurfaceView surfaceView) {

    }

    public void start() {

    }

    public void release() {

    }

    public void stop() {

    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class InstanceHolder {
        protected static VideoRecordManager instance = new VideoRecordManager();
    }
}
