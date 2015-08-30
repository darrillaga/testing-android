package com.mooveit.android.testing.espresso;


import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.espresso.util.TreeIterables;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeoutException;

import static android.support.test.espresso.matcher.ViewMatchers.isRoot;

class WaitForViewViewAction implements ViewAction {

    private final Matcher<View> matcher;
    private final long millis;

    public WaitForViewViewAction(Matcher<View> matcher, long millis) {
        this.matcher = matcher;
        this.millis = millis;
    }

    @Override
    public Matcher<View> getConstraints() {
        return isRoot();
    }

    @Override
    public String getDescription() {
        return "wait for a specific view with matcher <" + matcher + "> during " +
                millis + " millis.";
    }

    @Override
    public void perform(final UiController uiController, final View view) {
        uiController.loopMainThreadUntilIdle();
        final long startTime = System.currentTimeMillis();
        final long endTime = startTime + millis;
        final Matcher<View> viewMatcher = matcher;

        do {
            for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                // found view with required ID
                if (viewMatcher.matches(child)) {
                    return;
                }
            }

            uiController.loopMainThreadForAtLeast(50);
        }
        while (System.currentTimeMillis() < endTime);

        // timeout happens
        throw new PerformException.Builder()
                .withActionDescription(this.getDescription())
                .withViewDescription(HumanReadables.describe(view))
                .withCause(new TimeoutException())
                .build();
    }
}
