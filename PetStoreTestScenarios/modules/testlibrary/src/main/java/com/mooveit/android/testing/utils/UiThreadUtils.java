package com.mooveit.android.testing.utils;

import android.support.test.InstrumentationRegistry;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.concurrent.atomic.AtomicBoolean;

import java8.util.function.Predicate;

public class UiThreadUtils {

    public static AtomicBoolean flagListenerOnUiThread(View view, Predicate<View> predicate) {
        AtomicBoolean atomicFlag = new AtomicBoolean(false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                if (predicate.test(view)) {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    atomicFlag.set(true);
                }
                return true;
            }
        });

        return atomicFlag;
    }

    public static void onUiThreadSync(Runnable runnable) {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(runnable);
    }
}
