package com.mooveit.android.testing.espresso;

import android.graphics.Rect;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

public class ViewMatchers {

    private final static int VIEW_VISIBILITY_COMPARE = 85;
    /**
     * Sugar for withIndexInParent(is(int)).
     *
     * @param id the resource id.
     */
    public static Matcher<View> withIndexInParent(int id) {
        return withIndexInParent(is(id));
    }

    /**
     * Returns a matcher that matches {@link View}s based on its index in its parent.
     *
     * @param integerMatcher a Matcher for resource ids
     */
    public static Matcher<View> withIndexInParent(final Matcher<Integer> integerMatcher) {
        checkNotNull(integerMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with index in parent: ");
                integerMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return integerMatcher.matches(((ViewGroup) view.getParent()).indexOfChild(view));
            }
        };
    }

    /**
     * Returns a matcher that matches {@link View}s based on its children count.
     *
     * @param integerMatcher a Matcher for children count
     */
    public static Matcher<View> hasChildrenQuantity(final Matcher<Integer> integerMatcher) {
        checkNotNull(integerMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with children quantity: ");
                integerMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof ViewGroup)) {
                    return false;
                }
                return integerMatcher.matches(((ViewGroup) view).getChildCount());
            }
        };
    }

    /**
     * Returns a matcher that matches {@link RecyclerView}
     * based on its item count.
     *
     * @param integerMatcher a Matcher for item count
     */
    public static Matcher<View> hasItemQuantity(final Matcher<Integer> integerMatcher) {
        checkNotNull(integerMatcher);
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with item quantity: ");
                integerMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof ViewGroup)) {
                    return false;
                }
                return integerMatcher.matches(((RecyclerView) view).getAdapter().getItemCount());
            }
        };
    }

    public static Matcher<View> hasAGivenParentAndIsOnIndex(final Matcher<Integer> idMatcher, final Matcher<Integer> indexMatcher) {
        checkNotNull(idMatcher);
        checkNotNull(indexMatcher);

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendDescriptionOf(indexMatcher);
                description.appendText(" on parent with id: ");
                description.appendDescriptionOf(idMatcher);
            }

            @Override
            public boolean matchesSafely(View view) {
                return allOf(
                        withParent(withId(idMatcher)),
                        withIndexInParent(indexMatcher)
                ).matches(view);
            }
        };
    }

    public static Matcher<View> swipeRefreshLayoutState(boolean isRefreshing) {
        return new BoundedMatcher<View, SwipeRefreshLayout>(SwipeRefreshLayout.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("is");
                if (isRefreshing) {
                    description.appendText("not");
                }
                description.appendText("refreshing");
            }

            @Override
            protected boolean matchesSafely(SwipeRefreshLayout swipeRefreshLayout) {
                return swipeRefreshLayout.isRefreshing() == isRefreshing;
            }
        };
    }

    public static Matcher<View> withInputType(int inputType) {
        return withInputType(inputType, null);
    }

    public static Matcher<View> withInputType(int inputType, Integer kind) {
        return new BoundedMatcher<View, TextView>(TextView.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("is view input type equal to: ");
                description.appendText(Integer.toString(inputType));
            }

            @Override
            protected boolean matchesSafely(TextView textView) {
                int viewInputType = textView.getInputType();

                if (kind != null) {
                    viewInputType = viewInputType & kind;
                }

                return inputType == viewInputType;
            }
        };
    }

    public static Matcher<View> withInputTypeClass(int inputType) {
        return withInputType(inputType, EditorInfo.TYPE_MASK_CLASS);
    }

    public static Matcher<View> withInputTypeVariation(int inputType) {
        return withInputType(inputType, EditorInfo.TYPE_MASK_VARIATION);
    }

    /**
     * Returns a matcher that matches {@link EditText} based on edit text error string value.
     */
    public static Matcher<View> hasErrorText(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);
        return new BoundedMatcher<View, EditText>(EditText.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("with error: ");
                stringMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(EditText view) {
                return stringMatcher.matches(view.getError());
            }
        };
    }

    /**
     * Returns a matcher that matches {@link EditText} based on edit text error string value.
     *
     * Note: Sugar for hasErrorText(is("string")).
     */
    public static Matcher<View> hasErrorText(final String expectedError) {
        return hasErrorText(is(expectedError));
    }

    /**
     * Returns a matcher that returns true if the root view visibility is below eighty-five percent.
     * Used for testing if the soft keyboard is present on the screen
     */
    public static Matcher<View> isSoftKeyboardPresent() {
        return new BoundedMatcher<View, View>(View.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("view has soft keyboard present");
            }

            @Override
            protected boolean matchesSafely(View rootView) {
                Rect rect = new Rect();
                rootView.getWindowVisibleDisplayFrame(rect);

                return rect.height() <
                    (rootView.getRootView().getHeight() * VIEW_VISIBILITY_COMPARE) / 100;
            }
        };
    }

    public static Matcher<View> hasAGivenParentAndIsOnIndex(int resourceId, int index) {
        return hasAGivenParentAndIsOnIndex(is(resourceId), is(index));
    }
}
