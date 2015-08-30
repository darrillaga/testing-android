package com.mooveit.android.testing.espresso;

import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.AllOf.allOf;

class WaitViewVisibilityViewAction extends WaitForViewViewAction {

    public WaitViewVisibilityViewAction(Matcher<View> matcher, long millis) {
        super(allOf(matcher, isDisplayed()), millis);
    }
}
