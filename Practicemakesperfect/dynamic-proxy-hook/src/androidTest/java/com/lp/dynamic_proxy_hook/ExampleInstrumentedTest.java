/*
 *  ------------------------------------------------------------------
 *  Copyright © 2017. Hangzhou DtDream Technology Co.,Lt d. All rights reserved.
 *  ------------------------------------------------------------------
 *  Product: 产品化App
 *  Module Name: dynamic-proxy-hook
 *  Date Created: 17-12-22 上午10:59
 *  Description:
 *  ------------------------------------------------------------------
 *  Modification History
 *  17-12-22 上午10:58           ExampleInstrumentedTest.java            lipin
 *  ------------------------------------------------------------------
 *
 *  ------------------------------------------------------------------
 */

package com.lp.dynamic_proxy_hook;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lp.dynamic_proxy_hook", appContext.getPackageName());
    }
}
