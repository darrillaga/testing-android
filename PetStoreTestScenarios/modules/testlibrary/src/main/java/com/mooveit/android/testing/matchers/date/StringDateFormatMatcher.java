package com.mooveit.android.testing.matchers.date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringDateFormatMatcher extends TypeSafeMatcher<String> {

    private String mFormat;
    private Locale mLocale;
    private TimeZone mTimeZone;

    public StringDateFormatMatcher(String format, Locale locale, TimeZone timeZone) {
        this.mFormat = format;
        this.mLocale = locale;
        this.mTimeZone = timeZone;
    }

    @Override
    public boolean matchesSafely(final String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(mFormat,
                mLocale);

        dateFormat.setTimeZone(mTimeZone);

        Date date;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return false;
        }

        String reverseDateString = dateFormat.format(date);

        return dateString.equals(reverseDateString);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the date should match ").appendValue(mFormat);
    }

    //FIXME: I do not know what is happening here, if I uncomment the next line,
    // it compiles without problems on IntelliJ, but it does not compiles with gradle
    // @Override
    public void describeMismatch(String item, Description description) {
        description.appendText("has not the format ").appendValue(mFormat);
    }
}
