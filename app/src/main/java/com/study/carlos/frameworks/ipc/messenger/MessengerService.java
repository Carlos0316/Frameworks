package com.study.carlos.frameworks.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

/**
 * Created by Carlos on 2016/8/16.
 */
public class MessengerService extends Service {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private Messenger mMessenger = new Messenger(new MessageHandler());

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
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
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
            Message msgToClient = Message.obtain(msg);
            //...
            //对接收到的消息内容进行处理
            try {
                msg.replyTo.send(msgToClient);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
