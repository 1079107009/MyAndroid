package com.lp.dynamic_proxy_hook;

import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author weishu
 * @date 16/1/28
 */
public class HookHelper {

    public static void attachContext() throws Exception {
        // 先获取到当前的ActivityThread对象
        Class<?> activityThreadClazz = Class.forName("android.app.ActivityThread");

        // 拿到原始的sCurrentActivityThread字段
        Object activityThread = getField(activityThreadClazz, null, "sCurrentActivityThread");
        if (activityThread == null) {
            activityThread = ((ThreadLocal<?>) getField(activityThreadClazz, null, "sThreadLocal")).get();
        }
        Instrumentation mInstrumentation = (Instrumentation) invoke(
                activityThread.getClass(), activityThread, "getInstrumentation");
        // 创建代理对象
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // 偷梁换柱
        setField(activityThread.getClass(), activityThread, "mInstrumentation", evilInstrumentation);
    }

    public static Object getField(Class clazz, Object target, String name) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field.get(target);
    }

    public static void setField(Class clazz, Object target, String name, Object value) throws Exception {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    @SuppressWarnings("unchecked")
    public static Object invoke(Class clazz, Object target, String name, Object... args)
            throws Exception {
        Class[] parameterTypes = null;
        if (args != null) {
            parameterTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                parameterTypes[i] = args[i].getClass();
            }
        }

        Method method = clazz.getDeclaredMethod(name, parameterTypes);
        method.setAccessible(true);
        return method.invoke(target, args);
    }

}

