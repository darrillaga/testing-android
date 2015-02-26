package com.mooveit.twittertopics.ui.activities.trends;

import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;

import com.mooveit.android.networking.robospice.mock.MockResponseObjectsProvider;
import com.mooveit.android.networking.robospice.mock.ServerMock;
import com.mooveit.android.testing.matchers.TrendNameMatcher;
import com.mooveit.twittertopics.Application;
import com.mooveit.twittertopics.entities.SearchMetadata;
import com.mooveit.twittertopics.entities.Token;
import com.mooveit.twittertopics.entities.Trend;
import com.mooveit.twittertopics.entities.TrendList;
import com.mooveit.twittertopics.networking.TwitterApiResponseException;
import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.utils.SpiceManagerInstanceProvider;
import com.octo.android.robospice.request.SpiceRequest;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class TrendsInstrumentationTestCase
        extends ActivityInstrumentationTestCase2<TrendsActivity>
        implements SpiceManagerInstanceProvider, MockResponseObjectsProvider {

    private TwitterRequestSpiceManager mTwitterRequestSpiceManager;

    public TrendsInstrumentationTestCase() {
        super(TrendsActivity.class);
    }

    @Override
    public void setUp() {
        mTwitterRequestSpiceManager = ServerMock.mockSpiceManager(
                Application.get(getInstrumentation().getTargetContext()).
                        provideTwitterRequestSpiceManager(),
                this
        );

        Application.get(getInstrumentation().getTargetContext()).getSpiceManagerFactory().
                setSpiceManagerInstanceProvider(this);
    }

    public void testLoadListViewOnSuccessfulResponse() throws Exception {
        getActivity();

        onData(
                allOf(
                        is(instanceOf(Trend.class)), TrendNameMatcher.hasName("#BBB")
                )
        )
                .inAdapterView(withId(android.R.id.list))
                .atPosition(1)
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Override
    public TwitterRequestSpiceManager provideTwitterRequestSpiceManager() {
        return mTwitterRequestSpiceManager;
    }

    @Override
    public Object getResponseFor(SpiceRequest request) {
        if (request.getResultType().equals(Token.class)) {
            Token token = new Token();

            token.setAccessToken("adad");

            return token;
        } else if (request.getResultType().equals(TrendList[].class)) {
            TrendList trendList = new TrendList();

            trendList.setSearchMetadata(new SearchMetadata());
            trendList.setList(new ArrayList<Trend>());
            Trend trend = new Trend();
            trend.setTrend("#AAA");
            trendList.getList().add(trend);
            trend = new Trend();
            trend.setTrend("#BBB");
            trendList.getList().add(trend);

            return new TrendList[]{trendList};
        } else {
            return new TwitterApiResponseException(
                    getInstrumentation().getTargetContext(),
                    "Test error",
                    "Test error"
            );
        }
    }
}
