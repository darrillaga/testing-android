package com.mooveit.android.testing.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.internal.util.Checks.checkNotNull;

public class CompareMatchers {

    public static Matcher<Object> compareDoubleIsEqualTo(double number) {
        return compareIsEqualTo(number);
    }

    public static Matcher<Object> compareIsEqualTo(Comparable comparable) {
        checkNotNull(comparable);

        return new BaseMatcher<Object>() {
            @Override
            public boolean matches(Object comparableToCompareObject) {
                if (!(comparableToCompareObject instanceof Comparable)) {
                    return comparable.equals(comparableToCompareObject);
                }

                Comparable comparableToCompare = (Comparable) comparableToCompareObject;

                return comparable.compareTo(comparableToCompare) == 0;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is equal compared to ");
                description.appendValue(comparable);
            }
        };
    }

}
