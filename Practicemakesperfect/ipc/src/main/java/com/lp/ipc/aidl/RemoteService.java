/*
 *  ------------------------------------------------------------------
 *  Copyright © 2017. Hangzhou DtDream Technology Co.,Lt d. All rights reserved.
 *  ------------------------------------------------------------------
 *  Product: 产品化App
 *  Module Name: ipc
 *  Date Created: 17-12-26 下午5:19
 *  Description:
 *  ------------------------------------------------------------------
 *  Modification History
 *  17-12-26 下午5:19           RemoteService.java            lipin
 *  ------------------------------------------------------------------
 *
 *  ------------------------------------------------------------------
 */

package com.lp.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import static com.lp.aidl.IRemoteService.Stub;

public class RemoteService extends Service {
    private final Stub mBinder = new Stub() {

        @Override
        public int getPid() throws RemoteException {
            return 10086;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
