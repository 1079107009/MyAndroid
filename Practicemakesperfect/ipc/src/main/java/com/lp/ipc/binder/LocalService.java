/*
 *  ------------------------------------------------------------------
 *  Copyright © 2017. Hangzhou DtDream Technology Co.,Lt d. All rights reserved.
 *  ------------------------------------------------------------------
 *  Product: 产品化App
 *  Module Name: ipc
 *  Date Created: 17-12-26 下午3:44
 *  Description:
 *  ------------------------------------------------------------------
 *  Modification History
 *  17-12-26 下午3:44           LocalService.java            lipin
 *  ------------------------------------------------------------------
 *
 *  ------------------------------------------------------------------
 */

package com.lp.ipc.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Random;

/**
 * @author Administrator
 *         如果服务只是您的自有应用的后台工作线程，则优先采用这种方法。
 *         不以这种方式创建接口的唯一原因是，您的服务被其他应用或不同的进程占用。
 *         如果您不需要执行跨越不同应用的并发 IPC，就应该通过实现一个 Binder 创建接口
 */
public class LocalService extends Service {
    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerator = new Random();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

    public class LocalBinder extends Binder {
        public LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }
}
