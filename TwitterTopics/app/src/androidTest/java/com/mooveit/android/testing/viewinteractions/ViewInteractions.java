package com.mooveit.android.testing.viewinteractions;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.RootMatchers;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ViewInteractions {

    /**
     * Find a view interaction of a Toast with an specific text content.
     * @param text
     * @param activity
     * @return the view interaction
     */
    public static ViewInteraction onToastWithText(String text, Activity activity) {
        return onView(
            withText(text)
        ).inRoot(
            RootMatchers.withDecorView(
                    not(
                            is(
                                    activity.getWindow().getDecorView()
                            )
                    )
            )
        );
    }

    /**
     * @see .onToastWithText(String, android.app.Activity)
     *
     * The string text is retrieved with the resource id.
     *
     * @param textResId
     * @param activity
     * @return
     */
    public static ViewInteraction onToastWithText(int textResId, Activity activity) {
        return onToastWithText(activity.getString(textResId), activity);
    }
}
