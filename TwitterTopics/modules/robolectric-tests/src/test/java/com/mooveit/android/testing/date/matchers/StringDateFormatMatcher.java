package com.mooveit.android.testing.date.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StringDateFormatMatcher extends BaseMatcher<String> {

    private String format;
    private Locale locale;
    private TimeZone timeZone;

    public StringDateFormatMatcher(String format, Locale locale, TimeZone timeZone) {
        this.format = format;
        this.locale = locale;
        this.timeZone = timeZone;
    }

    @Override
    public boolean matches(final Object item) {
        final String dateString = (String) item;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format,
                locale);

        dateFormat.setTimeZone(timeZone);

        Date date;

        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            return false;
        }

        String reverseDateString = dateFormat.format(date);

        return dateString != null && dateString.equals(reverseDateString);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("the date should match ").appendValue(format);
    }

    //FIXME: I do not know what is happening here, if I uncomment the next line,
    // it compiles without problems on IntelliJ, but it does not compiles with gradle
    // @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("has not the format ").appendValue(format);
    }
}
