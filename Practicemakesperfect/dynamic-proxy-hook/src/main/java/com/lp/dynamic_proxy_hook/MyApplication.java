
package com.lp.dynamic_proxy_hook;

import android.app.Application;
import android.content.Context;

/**
 * @author someone
 * @date 2017/12/25
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            // 在这里进行Hook,在ActivityHook的话已经晚了
            HookHelper.attachContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
