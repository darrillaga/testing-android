package com.mooveit.twittertopics.ui.activities.tweets;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.mooveit.twittertopics.networking.listeners.SuccessRequestListener;
import com.mooveit.twittertopics.parcels.UserParcel;
import com.mooveit.twittertopics.ui.activities.RobospiceListFragment;
import com.mooveit.twittertopics.ui.activities.user.UserDetailActivity;
import com.mooveit.twittertopics.ui.adapters.TweetAdapter;
import com.mooveit.twittertopics.entities.Tweet;
import com.mooveit.twittertopics.entities.TweetList;
import com.mooveit.twittertopics.entities.User;
import com.mooveit.twittertopics.networking.requests.TweetsRequest;
import com.mooveit.twittertopics.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import com.mooveit.twittertopics.R;

public class TweetsFragment extends RobospiceListFragment {

    public static final String TAG =
            "com.mooveit.twittertopics.ui.activities.tweets.TweetsFragment";

    public final static String ARGUMENT_TREND = TAG + "Arguments:Trend:String";
    public final static String ARGUMENT_TOKEN = "Arguments:Token:String";

    private String mTrend;
    private boolean mShouldClearTweets;
    private TweetAdapter mTweetAdapter;
    private List<Tweet> mTweetList = new ArrayList<>();
    private String mNextResults;
    private boolean mIsLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDividerHeight(0);

        getListView().setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onEndReached() {
                if (!mIsLoading) {
                    mIsLoading = true;
                    fetchNextTweets();
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        Tweet tweet = (Tweet) getListAdapter().getItem(position);
        startUserActivity(tweet.getUser(), (ImageView) view.findViewById(R.id.profile_image));
    }

    @Override
    public void onRefresh() {
        refreshTweets();
    }

    private void startUserActivity(User user, ImageView profileIV) {

        Intent userIntent = new Intent(getActivity(), UserDetailActivity.class);

        if (profileIV.getDrawable() != null) {

            Bitmap bitmap = ((BitmapDrawable) profileIV.getDrawable()).getBitmap();
            userIntent.putExtra(UserDetailActivity.EXTRA_IMAGE_PROFILE, bitmap);
        }

        userIntent.putExtra(UserDetailActivity.EXTRA_USER, new UserParcel(user));

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        getActivity(),
                        profileIV,
                        getActivity().getString(R.string.image_profile_transition));

        ActivityCompat.startActivity(getActivity(), userIntent, options.toBundle());
    }

    private void initializeFragment() {
        getBundleArguments();
        mTweetAdapter = new TweetAdapter(getActivity(), mTweetList);
        setListAdapter(mTweetAdapter);
        setListShownNoAnimation(false);
    }

    private void getBundleArguments() {
        mTrend = getArguments().getString(ARGUMENT_TREND);

        getTwitterSpiceManager().setToken(getArguments().getString(ARGUMENT_TOKEN));

        refreshTweets();
    }

    private void fetchNextTweets() {
        mShouldClearTweets = false;

        if (mNextResults != null) {
            TweetsRequest tweetsRequest = new TweetsRequest(getActivity(), mNextResults, true);
            fetchTweets(tweetsRequest);
        } else {
            onLoadCompleted();
        }
    }

    private void refreshTweets() {
        mShouldClearTweets = true;

        TweetsRequest tweetsRequest = new TweetsRequest(getActivity(), mTrend, false);

        fetchTweets(tweetsRequest);
    }

    private void fetchTweets(TweetsRequest tweetsRequest) {
        getTwitterSpiceManager().execute(tweetsRequest,
                new SuccessRequestListener<TweetList>(getActivity(), getErrorsHandler()) {
                    @Override
                    public void onRequestSuccess(TweetList tweetList) {
                        onTweetsResponse(tweetList);
                        super.onRequestSuccess(tweetList);
                    }

                    @Override
                    public void onComplete() {
                        onLoadCompleted();
                        super.onComplete();
                    }
                }
        );
    }

    private void onLoadCompleted() {
        setRefreshing(false);
        mIsLoading = false;
    }

    private void onTweetsResponse(TweetList tweets) {
        if (mShouldClearTweets) {
            mTweetAdapter.clear();
        }

        mShouldClearTweets = false;

        mNextResults = Uri.decode(tweets.getSearchMetadata().getNextResults());
        mTweetList.addAll(tweets.getStatuses());
        mTweetAdapter.notifyDataSetChanged();
        setListShown(true);
        mIsLoading = false;
    }
}