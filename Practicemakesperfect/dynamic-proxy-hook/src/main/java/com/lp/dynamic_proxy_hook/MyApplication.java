/*
 *  ------------------------------------------------------------------
 *  Copyright © 2017. Hangzhou DtDream Technology Co.,Lt d. All rights reserved.
 *  ------------------------------------------------------------------
 *  Product: 产品化App
 *  Module Name: dynamic-proxy-hook
 *  Date Created: 17-12-25 上午10:09
 *  Description:
 *  ------------------------------------------------------------------
 *  Modification History
 *  17-12-25 上午10:09           MyApplication.java            lipin
 *  ------------------------------------------------------------------
 *
 *  ------------------------------------------------------------------
 */

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
