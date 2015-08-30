package com.mooveit.android.testing.utils;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import java.util.List;

public class EspressoUtils {

    public static void unregisterIdlingResources() {
        List<IdlingResource> registerdIdlingResources = Espresso.getIdlingResources();

        Espresso.unregisterIdlingResources(Espresso.getIdlingResources().toArray(new IdlingResource[registerdIdlingResources.size()]));
    }
}
