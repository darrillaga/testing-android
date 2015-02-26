package com.mooveit.android.testing;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.test.runner.AndroidJUnitRunner;
import android.util.Log;

import com.mooveit.android.testing.utils.FixturesLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Custom instrumentation test runner that allows execute general behaviour for the entire
 * test suite
 */
public class InstrumentationTestRunner extends AndroidJUnitRunner {

    @Override
    public void onCreate(Bundle arguments) {
        loadFixtures();
//        disableAnimation();
        super.onCreate(arguments);
    }

    private void loadFixtures() {
        if (FixturesLoader.getFixtures().isEmpty()) {
            FixturesLoader.getFixtures().putAll(
                    FixturesLoader.onLoad(
                            getContext(),
                            getContext().
                                    getString(com.mooveit.twittertopics.test.R.string.fixtures_path)
                    )
            );
        }
    }

//    private static final String TAG = "Primer";
//    private static final String ANIMATION_PERMISSION = "android.permission.SET_ANIMATION_SCALE";

//    private void disableAnimation() {
//        int permStatus = getContext().checkCallingOrSelfPermission(ANIMATION_PERMISSION);
//        if (permStatus == PackageManager.PERMISSION_GRANTED) {
//            if (reflectivelyDisableAnimation()) {
//                Log.i(TAG, "All animations disabled.");
//            } else {
//                Log.i(TAG, "Could not disable animations.");
//            }
//        } else {
//            Log.i(TAG, "Cannot disable animations due to lack of permission.");
//        }
//    }

//    private boolean reflectivelyDisableAnimation() {
//        try {
//            Class<?> windowManagerStubClazz = Class.forName("android.view.IWindowManager$Stub");
//            Method asInterface = windowManagerStubClazz.getDeclaredMethod("asInterface", IBinder.class);
//            Class<?> serviceManagerClazz = Class.forName("android.os.ServiceManager");
//            Method getService = serviceManagerClazz.getDeclaredMethod("getService", String.class);
//            Class<?> windowManagerClazz = Class.forName("android.view.IWindowManager");
//            Method setAnimationScales = windowManagerClazz.getDeclaredMethod("setAnimationScales",
//                    float[].class);
//            Method getAnimationScales = windowManagerClazz.getDeclaredMethod("getAnimationScales");
//
//            IBinder windowManagerBinder = (IBinder) getService.invoke(null, "window");
//            Object windowManagerObj = asInterface.invoke(null, windowManagerBinder);
//            float[] currentScales = (float[]) getAnimationScales.invoke(windowManagerObj);
//            for (int i = 0; i < currentScales.length; i++) {
//                currentScales[i] = 0.0f;
//            }
//            setAnimationScales.invoke(windowManagerObj, currentScales);
//            return true;
//        } catch (ClassNotFoundException cnfe) {
//            Log.w(TAG, "Cannot disable animations reflectively.", cnfe);
//        } catch (NoSuchMethodException mnfe) {
//            Log.w(TAG, "Cannot disable animations reflectively.", mnfe);
//        } catch (SecurityException se) {
//            Log.w(TAG, "Cannot disable animations reflectively.", se);
//        } catch (InvocationTargetException ite) {
//            Log.w(TAG, "Cannot disable animations reflectively.", ite);
//        } catch (IllegalAccessException iae) {
//            Log.w(TAG, "Cannot disable animations reflectively.", iae);
//        } catch (RuntimeException re) {
//            Log.w(TAG, "Cannot disable animations reflectively.", re);
//        }
//        return false;
//    }
}
