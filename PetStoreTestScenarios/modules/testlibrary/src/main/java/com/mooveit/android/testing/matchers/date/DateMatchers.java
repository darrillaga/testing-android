package com.mooveit.android.testing.matchers.date;

import org.hamcrest.Matcher;

import java.util.Locale;
import java.util.TimeZone;

public class DateMatchers {

    public static final Locale DEFAULT_LOCALE = new Locale("es");
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public static Matcher<String> hasFormat(final String format) {
        return hasFormat(format, DEFAULT_LOCALE, DEFAULT_TIME_ZONE);
    }


    public static Matcher<String> hasFormat(final String format,
                                            final Locale locale,
                                            final TimeZone timeZone) {

        return new StringDateFormatMatcher(format, locale, timeZone);
    }

}
