package com.mooveit.petstoretestscenarios;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class Application extends com.mooveit.android.Application{

    @Override
    public void onCreate() {
        Fabric.with(this, new Crashlytics());
        super.onCreate();
    }
}
