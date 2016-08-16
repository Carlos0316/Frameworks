package com.study.carlos.frameworks.ipc.messenger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.lang.ref.WeakReference;

/**
 * Created by Carlos on 2016/8/16.
 */
public class MessengerClientActivity extends Activity {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Messenger mService;

    private Messenger mMessenger;

    private MessengerServiceConnection mConnection;

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

        mMessenger = new Messenger(new MessageHandler());

        mConnection = new MessengerServiceConnection(this);

        //发送消息给服务端的代码
//        Message message = new Message();
//        message.replyTo = mMessenger;
//        if (mService != null) {
//            try {
//                mService.send(message);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent service = new Intent("XXX");
        service.setPackage("XXX");
        bindService(service, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    @Override
    protected void onDestroy() {
        mMessenger = null;
        super.onDestroy();
    }

    // ===========================================================
    // Methods
    // ===========================================================

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

    private static class MessageHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    private static class MessengerServiceConnection implements ServiceConnection {

        private WeakReference<MessengerClientActivity> mRef;

        public MessengerServiceConnection(MessengerClientActivity activity) {
            mRef = new WeakReference<MessengerClientActivity>(activity);
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (mRef != null && mRef.get() != null) {
                mRef.get().mService = new Messenger(iBinder);
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
