package com.study.carlos.frameworks.ipc.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import java.lang.ref.WeakReference;

/**
 * Created by Carlos on 2016/8/16.
 */
public class CalculateClientActivity extends Activity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private CalculateServiceConnection mConnection;

    private ICalculate mService;

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
        mConnection = new CalculateServiceConnection(this);

        //拿到ICalculate的子类实例后进行后续操作
//        mService.add(a, b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent service = new Intent("com.carlos.action.AIDL");
        service.setPackage("com.study.carlos.frameworks");
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    @Override
    protected void onDestroy() {
        mConnection = null;
        super.onDestroy();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class CalculateServiceConnection implements ServiceConnection {

        private WeakReference<CalculateClientActivity> mRef;

        public CalculateServiceConnection(CalculateClientActivity activity) {
            mRef = new WeakReference<CalculateClientActivity>(activity);
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (mRef != null && mRef.get() != null) {
                mRef.get().mService = ICalculate.Stub.asInterface(iBinder);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            if (mRef != null && mRef.get() != null) {
                mRef.get().mService = null;
            }
        }
    }

}
