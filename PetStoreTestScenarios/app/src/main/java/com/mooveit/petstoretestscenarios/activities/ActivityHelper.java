package com.mooveit.petstoretestscenarios.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java8.util.stream.StreamSupport;

public class ActivityHelper {

    public static <T> T ensureActivityHasAttachedRequiredClassObject(Activity activity,
                                                                     Class<T> requiredClass) {

        try {
            return (T) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement " + requiredClass.getCanonicalName());
        }
    }

    public static <T> T ensureFragmentHasAttachedRequiredClassObject(Fragment fragment,
                                                             Class<T> requiredClass) {

        Object requiredObject = (fragment.getParentFragment() != null) ?
                fragment.getParentFragment() : fragment.getActivity();

        try {
            return (T) requiredObject;
        } catch (ClassCastException e) {
            throw new ClassCastException(requiredObject.toString()
                    + " must implement " + requiredClass.getCanonicalName());
        }
    }

    public static void checkResultOnHierarchy(FragmentManager fragmentManager, int requestCode, int resultCode, Intent data) {
        StreamSupport.stream(fragmentManager.getFragments()).
                filter(f -> f != null).
                forEach(f -> f.onActivityResult(requestCode, resultCode, data));
    }
}
