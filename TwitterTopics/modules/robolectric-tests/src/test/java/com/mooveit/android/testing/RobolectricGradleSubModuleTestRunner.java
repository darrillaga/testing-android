package com.mooveit.android.testing;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;
import org.robolectric.res.Fs;

import java.io.File;

public class RobolectricGradleSubModuleTestRunner extends RobolectricTestRunner {

    private static final int MAX_SDK_SUPPORTED_BY_ROBOLECTRIC = 18;

    public RobolectricGradleSubModuleTestRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }

    protected String getAndroidManifestPath() {
        return "../app/src/main/AndroidManifest.xml";
    }

    protected String getResPath() {
        return "../app/src/main/res";
    }

    @Override
    protected final AndroidManifest getAppManifest(Config config) {
        return new AndroidManifest(Fs.fileFromPath(getAndroidManifestPath()),
                Fs.fileFromPath(getResPath())) {
            @Override
            public int getTargetSdkVersion() {
                return MAX_SDK_SUPPORTED_BY_ROBOLECTRIC;
            }
        };
    }

    @Override
    public Setup createSetup() {
        return new CustomSetup();
    }

    private static class CustomSetup extends Setup
    {
        // This is the only way i found how to allow instrumentation of some classes.
        // Without this shadows are not instrumented
        @Override
        public boolean shouldInstrument(ClassInfo info) {
            boolean instrument = super.shouldInstrument(info) ||
                    info.getName().equals("com.nineoldandroids.view.ViewPropertyAnimator") ||
                    info.getName().equals("com.nineoldandroids.view.ViewPropertyAnimatorPreHC");

            return instrument;
        }
    }

}
