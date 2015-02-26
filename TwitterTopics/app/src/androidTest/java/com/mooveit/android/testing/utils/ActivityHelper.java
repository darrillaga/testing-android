package com.mooveit.android.testing.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;

public class ActivityHelper<ACTIVITY extends Activity> {

    private ACTIVITY mActivity;
    private Instrumentation mInstrumentation;

    public ActivityHelper(Instrumentation instrumentation, ACTIVITY activity) {
        this.mActivity = activity;
        this.mInstrumentation = instrumentation;
    }

    public ActivityHelper onCreate(Bundle icicle) {
        mInstrumentation.callActivityOnCreate(mActivity, icicle);
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
    public ActivityHelper onCreate(Bundle icicle,
                                     PersistableBundle persistentState) {
        mInstrumentation.callActivityOnCreate(mActivity, icicle, persistentState);
        return this;
    }

    public ActivityHelper onDestroy() {
        mInstrumentation.callActivityOnDestroy(mActivity);
        return this;
    }

    public ActivityHelper onRestoreInstanceState(Bundle savedInstanceState) {
        mInstrumentation.callActivityOnRestoreInstanceState(mActivity, savedInstanceState);
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
    public ActivityHelper onRestoreInstanceState(Bundle savedInstanceState,
                                                   PersistableBundle persistentState) {

        mInstrumentation.callActivityOnRestoreInstanceState(mActivity, savedInstanceState, persistentState);
        return this;
    }

    public ActivityHelper onPostCreate(Bundle icicle) {
        mInstrumentation.callActivityOnPostCreate(mActivity, icicle);
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
    public ActivityHelper onPostCreate(Bundle icicle,
                                         PersistableBundle persistentState) {

        mInstrumentation.callActivityOnPostCreate(mActivity, icicle, persistentState);
        return this;
    }

    public ActivityHelper onNewIntent(Intent intent) {
        mInstrumentation.callActivityOnNewIntent(mActivity, intent);
        return this;
    }

    public ActivityHelper onStart() {
        mInstrumentation.callActivityOnStart(mActivity);
        return this;
    }

    public ActivityHelper onRestart() {
        mInstrumentation.callActivityOnRestart(mActivity);
        return this;
    }

    public ActivityHelper onResume() {
        mInstrumentation.callActivityOnResume(mActivity);
        return this;
    }

    public ActivityHelper onStop() {
        mInstrumentation.callActivityOnStop(mActivity);
        return this;
    }

    public ActivityHelper onSaveInstanceState(Bundle outState) {
        mInstrumentation.callActivityOnSaveInstanceState(mActivity, outState);
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)
    public ActivityHelper onSaveInstanceState(Bundle outState,
                                                PersistableBundle outPersistentState) {
        mInstrumentation.callActivityOnSaveInstanceState(mActivity, outState, outPersistentState);
        return this;
    }

    public ActivityHelper onPause() {
        mInstrumentation.callActivityOnPause(mActivity);
        return this;
    }

    public ActivityHelper onUserLeaving() {
        mInstrumentation.callActivityOnUserLeaving(mActivity);
        return this;
    }

}
