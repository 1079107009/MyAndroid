/*
 *  ------------------------------------------------------------------
 *  Copyright © 2017. Hangzhou DtDream Technology Co.,Lt d. All rights reserved.
 *  ------------------------------------------------------------------
 *  Product: 产品化App
 *  Module Name: ipc
 *  Date Created: 17-12-26 下午3:19
 *  Description:
 *  ------------------------------------------------------------------
 *  Modification History
 *  17-12-26 下午3:19           MessengerService.java            lipin
 *  ------------------------------------------------------------------
 *
 *  ------------------------------------------------------------------
 */

package com.lp.ipc.messager;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

/**
 * @author lp
 *         与 AIDL 比较
 *         当您需要执行 IPC 时，为您的接口使用 Messenger 要比使用 AIDL 实现它更加简单，
 *         因为 Messenger 会将所有服务调用排入队列，而纯粹的 AIDL 接口会同时向服务发送多个请求，服务随后必须应对多线程处理。
 *         对于大多数应用，服务不需要执行多线程处理，因此使用 Messenger 可让服务一次处理一个调用。
 *         如果您的服务必须执行多线程处理，则应使用 AIDL 来定义接口。
 */
public class MessengerService extends Service {

    public static final int MSG_SAY_HELLO = 1;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("MessengerService", "onBind: 服务已绑定");
        return mMessenger.getBinder();
    }

    @SuppressLint("HandlerLeak")
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello client!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
