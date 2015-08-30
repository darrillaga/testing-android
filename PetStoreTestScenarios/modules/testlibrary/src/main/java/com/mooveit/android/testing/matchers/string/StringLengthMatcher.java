package com.mooveit.android.testing.matchers.string;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.equalTo;

public class StringLengthMatcher {

    @Factory
    public static Matcher<String> length(Matcher<? super Integer> lengthMatcher) {
        CharSequenceLengthMatcher matcher = new CharSequenceLengthMatcher(lengthMatcher);

        return new TypeSafeMatcher<String>() {
            @Override
            public void describeTo(Description description) {
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(String s) {
                return matcher.matchesSafely(s);
            }
        };
    }

    @Factory
    public static Matcher<String> length(int length) {
        return length(equalTo(length));
    }
}