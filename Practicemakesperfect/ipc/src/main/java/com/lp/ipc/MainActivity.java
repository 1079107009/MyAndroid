package com.lp.ipc;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.lp.aidl.IRemoteService;
import com.lp.ipc.aidl.RemoteService;

/**
 * @author lp
 */
public class MainActivity extends AppCompatActivity {

    static final int MSG_SAY_HELLO = 2;

    IRemoteService mIRemoteService;
    //    LocalService mService;
//    Messenger mService = null;
//    Messenger mClient = new Messenger(new CallbackHandler());
    boolean mBound;
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mService = new Messenger(service);
//            mBound = true;
//            Message msg = Message.obtain(null, MSG_SAY_HELLO, 0, 0);
//            msg.replyTo = mService;
//            try {
//                mClient.send(msg);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
//            mService = binder.getService();
            mIRemoteService = IRemoteService.Stub.asInterface(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
            mIRemoteService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, RemoteService.class), mConnection,
                Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void sayHello(View view) {
        if (!mBound) return;
//        // Create and send a message to the service, using a supported 'what' value
//        Message msg = Message.obtain(null, MessengerService.MSG_SAY_HELLO, 0, 0);
//        try {
//            mService.send(msg);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        int num = mService.getRandomNumber();
        int num = 0;
        try {
            num = mIRemoteService.getPid();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("HandlerLeak")
    class CallbackHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello service!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
