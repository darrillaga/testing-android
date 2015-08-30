package com.mooveit.android.testing.matchers.string;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static org.hamcrest.Matchers.equalTo;

/**
 * Tests if the argument is a {@link CharSequence} that matches a specified length.
 */
public class CharSequenceLengthMatcher extends TypeSafeMatcher<CharSequence> {

    /**
     * Creates a matcher of {@link CharSequence} that matches when the examined {@link CharSequence} has the length
     * determined by the specified matcher.
     * <p/>
     * For example:
     * <pre>assertThat("myStringOfNote", length(not(equalTo(10))))</pre>
     * <pre>assertThat("myStringOfNote", length(lessThan(15))))</pre>
     *
     * @param lengthMatcher the matcher to apply to the examined {@link CharSequence}
     */

    @Factory
    public static CharSequenceLengthMatcher length(Matcher<? super Integer> lengthMatcher) {
        return new CharSequenceLengthMatcher(lengthMatcher);
    }

    /**
     * Creates a matcher of {@link CharSequence} that matches when the examined {@link CharSequence} has the specified
     * length.
     * <p/>
     * For example:
     * <pre>assertThat("myStringOfNote", length(10))</pre>
     *
     * @param length the length that the returned matcher will expect any examined string to have
     */
    @Factory
    public static CharSequenceLengthMatcher length(int length) {
        return length(equalTo(length));
    }

    private final Matcher<? super Integer> lengthMatcher;

    public CharSequenceLengthMatcher(Matcher<? super Integer> lengthMatcher) {
        this.lengthMatcher = lengthMatcher;
    }

    @Override
    public boolean matchesSafely(CharSequence text) {
        return lengthMatcher.matches(text != null ? text.length() : 0);
    }

    public void describeMismatchSafely(CharSequence item, Description mismatchDescription) {
        mismatchDescription.appendText("was length \"").appendText(item == null ? null : String.valueOf(item.length())).appendText("\"");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("has string length ").appendDescriptionOf(lengthMatcher);
    }
}