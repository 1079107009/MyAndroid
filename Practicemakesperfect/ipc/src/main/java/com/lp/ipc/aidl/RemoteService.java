
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
