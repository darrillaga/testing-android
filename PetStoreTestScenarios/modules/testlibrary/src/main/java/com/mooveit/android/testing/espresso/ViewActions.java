package com.mooveit.android.testing.espresso;

import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ViewActions {
    /** Perform action of waiting for a specific view id. */
    public static ViewAction waitIdVisibility(int viewId, long millis) {
        return waitViewVisibility(withId(viewId), millis);
    }

    /** Perform action of waiting for a specific view matcher. */
    public static ViewAction waitViewVisibility(Matcher<View> viewMatcher, long millis) {
        return new WaitViewVisibilityViewAction(viewMatcher, millis);
    }

    /** Perform action of waiting for a specific view matcher. */
    public static ViewAction waitForView(Matcher<View> viewMatcher, long millis) {
        return new WaitForViewViewAction(viewMatcher, millis);
    }
}
