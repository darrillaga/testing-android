package com.mooveit.android.testing.utils;

import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Test utilities for intents.
 */
public class IntentUtils {

    /**
     * Creates an ActivityMonitor for a chooserActivity (Intent.createChooser(...))
     * @param instrumentation
     * @return
     */
    public static Instrumentation.ActivityMonitor createChooserMonitor(Instrumentation instrumentation) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CHOOSER);
        return instrumentation.addMonitor(
                intentFilter,
                null,
                true
        );
    }
}
