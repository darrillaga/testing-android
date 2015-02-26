package com.mooveit.twittertopics.ui.activities.tweets;

import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;
import android.text.format.DateUtils;

import com.mooveit.android.networking.robospice.mock.MockResponseObjectsProvider;
import com.mooveit.android.networking.robospice.mock.ServerMock;
import com.mooveit.android.testing.matchers.TrendNameMatcher;
import com.mooveit.twittertopics.Application;
import com.mooveit.twittertopics.entities.SearchMetadata;
import com.mooveit.twittertopics.entities.Token;
import com.mooveit.twittertopics.entities.Trend;
import com.mooveit.twittertopics.entities.TrendList;
import com.mooveit.twittertopics.entities.Tweet;
import com.mooveit.twittertopics.entities.TweetList;
import com.mooveit.twittertopics.entities.User;
import com.mooveit.twittertopics.networking.TwitterApiResponseException;
import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.requests.TweetsRequest;
import com.mooveit.twittertopics.networking.utils.SpiceManagerInstanceProvider;
import com.mooveit.twittertopics.ui.activities.trends.TrendsActivity;
import com.mooveit.twittertopics.utils.TwitterHelper;
import com.octo.android.robospice.request.SpiceRequest;

import java.util.ArrayList;
import java.util.Date;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;


public class TweetsInstrumentationTestCase
        extends ActivityInstrumentationTestCase2<TweetsActivity>
        implements SpiceManagerInstanceProvider, MockResponseObjectsProvider {

    private TwitterRequestSpiceManager mTwitterRequestSpiceManager;

    public TweetsInstrumentationTestCase() {
        super(TweetsActivity.class);
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
        if (request.getResultType().equals(TweetList.class)) {
            String date = DateUtils.formatDateTime(
                    getInstrumentation().getTargetContext(),
                    new Date().getTime(),
                    0
            );

            TweetList tweetList = new TweetList();

            User user = new User();
            user.setScreenName("A");
            user.setDescription("b");
            user.setFollowersCount(1);
            user.setFriendsCount(2);
            user.setName("C");
            user.setProfileBackgroundImageUrl("http://www.google.com");
            user.setProfileImageUrl("http://www.google.com");
            user.setStatusesCount(3);
            user.setUrl("http://www.google.com");

            tweetList.setSearchMetadata(new SearchMetadata());
            tweetList.setStatuses(new ArrayList<Tweet>());

            TrendList trendList = new TrendList();

            Tweet tweet = new Tweet();

            tweet.setCreatedAt(date);
            tweet.setText("Tweet 1");
            tweet.setUser(user);

            tweetList.getStatuses().add(tweet);

            tweet = new Tweet();

            tweet.setCreatedAt(date);
            tweet.setText("Tweet 2");
            tweet.setUser(user);

            tweetList.getStatuses().add(tweet);

            return trendList;
        } else {
            return new TwitterApiResponseException(
                    getInstrumentation().getTargetContext(),
                    "Test error",
                    "Test error"
            );
        }
    }
}
