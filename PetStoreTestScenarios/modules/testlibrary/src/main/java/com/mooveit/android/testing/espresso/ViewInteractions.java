package com.mooveit.android.testing.espresso;

import android.app.Activity;
import android.support.test.espresso.ViewInteraction;
import android.view.View;

import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class ViewInteractions {

    /**
     * Find a view interaction of a Toast with an specific text content.
     *
     * @param text
     * @param activity
     * @return {@link android.support.test.espresso.ViewInteraction} the view interaction
     */
    public static ViewInteraction onToastWithText(String text, Activity activity) {
        return onViewWithTextOutsideDecorView(text, activity);
    }

    /**
     * @param textResId
     * @param activity
     * @return {@link android.support.test.espresso.ViewInteraction}
     * @see .onToastWithText(String, Activity)
     * <p>
     * The string text is retrieved with the resource id.
     */
    public static ViewInteraction onToastWithText(int textResId, Activity activity) {
        return onToastWithText(activity.getString(textResId), activity);
    }

    /**
     * Find a view interaction of a View with an specific
     * text content outside the window decor view.
     *
     * @param text
     * @param activity
     * @return {@link android.support.test.espresso.ViewInteraction} the view interaction
     */
    public static ViewInteraction onViewWithTextOutsideDecorView(String text, Activity activity) {
        return onView(
                withText(text)
        ).inRoot(
                withDecorView(not(is(activity.getWindow().getDecorView())))
        );
    }

    /**
     * Find a view interaction of a View with an specific
     * text in a dialog view.
     *
     * @param text
     * @return {@link android.support.test.espresso.ViewInteraction} the view interaction
     */
    public static ViewInteraction onViewWithTextInDialog(String text) {
        return onView(
                withText(text)
        ).inRoot(
                isDialog()
        );
    }


    /**
     * Clicks on a view with the specified id
     *
     * @param viewId
     */
    public static void clickOn(int viewId) {
        onView(withId(viewId)).perform(click());
    }

    /**
     * @param id      id for the specific parent matcher
     * @param matcher
     * @return
     * @see .onViewWithSomeParent( Matcher <View>, Matcher<View>)
     */
    public static ViewInteraction onViewWithSomeParent(int id,
                                                       Matcher<View> matcher) {

        return onView(allOf(isDescendantOfA(withId(id)), matcher));
    }

    /**
     * View interaction for a view with some parent with specific matcher
     *
     * @param parentMatcher
     * @param matcher
     * @return the view interaction
     */
    public static ViewInteraction onViewWithSomeParent(Matcher<View> parentMatcher,
                                                       Matcher<View> matcher) {

        return onView(allOf(isDescendantOfA(matcher), matcher));
    }
}
