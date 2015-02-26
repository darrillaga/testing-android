package com.mooveit.android.testing.matchers;

import com.mooveit.twittertopics.entities.Trend;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class TrendNameMatcher extends BaseMatcher<Trend> {

    private String trendName;

    public TrendNameMatcher(String trendName) {
        this.trendName = trendName;
    }

    @Override
    public boolean matches(Object o) {
        o.toString();
        return o instanceof Trend && ((Trend) o).getTrend().equals(trendName);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("The trend name is not " + trendName);
    }

    public static TrendNameMatcher hasName(String trendName) {
        return new TrendNameMatcher(trendName);
    }
}
