package com.lp.ams_pms_hook;

import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

/**
 * @author someone
 * @date 2017/12/28
 */

public class HookHelper {

    public static void hookActivityManager() throws Exception {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            hookActivityManager26();
        } else {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

            // 获取 gDefault 这个字段, 想办法替换它
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            Class<?> singleton = Class.forName("android.util.Singleton");
            Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象
            Object rawIActivityManager = mInstanceField.get(gDefault);
            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface}, new HookHandler(rawIActivityManager));
            mInstanceField.set(gDefault, proxy);
        }
    }

    private static void hookActivityManager26() throws Exception {
        Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");

        // 获取 IActivityManagerSingleton 这个字段, 想办法替换它
        Field gDefaultField = activityManagerClass.getDeclaredField("IActivityManagerSingleton");
        gDefaultField.setAccessible(true);
        Object gDefault = gDefaultField.get(null);

        //IActivityManagerSingleton是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
        Class<?> singleton = Class.forName("android.util.Singleton");
        Field mInstanceField = singleton.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);

        // ActivityManager的gDefault对象里面原始的 IActivityManager对象
        Object rawIActivityManager = mInstanceField.get(gDefault);

        // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
        Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{iActivityManagerInterface}, new HookHandler(rawIActivityManager));
        mInstanceField.set(gDefault, proxy);
    }
}
