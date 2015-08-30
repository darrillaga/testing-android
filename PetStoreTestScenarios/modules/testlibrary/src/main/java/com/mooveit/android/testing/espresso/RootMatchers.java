package com.mooveit.android.testing.espresso;

import android.os.IBinder;
import android.support.test.espresso.Root;
import android.view.WindowManager;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RootMatchers {

    public static Matcher<Root> isToast() {

        return new TypeSafeMatcher<Root>() {

            public void describeTo(Description description) {
                description.appendText("is toast");
            }

            public boolean matchesSafely(Root root) {

                return rootWindowHasType(root, WindowManager.LayoutParams.TYPE_TOAST) &&
                        hasSameApplicationAndWindowToken(root);
            }
        };
    }

    private static boolean rootWindowHasType(Root root, int expectedType) {
        int type = (root.getWindowLayoutParams().get()).type;

        return type == expectedType;
    }

    private static boolean hasSameApplicationAndWindowToken(Root root) {
        IBinder windowToken = root.getDecorView().getWindowToken();
        IBinder appToken = root.getDecorView().getApplicationWindowToken();

        return windowToken == appToken;
    }
}
