package com.mooveit.twittertopics.ui.activities.trends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.networking.listeners.SuccessRequestListener;
import com.mooveit.twittertopics.ui.activities.RobospiceListFragment;
import com.mooveit.twittertopics.ui.activities.tweets.TweetsActivity;
import com.mooveit.twittertopics.ui.adapters.TrendAdapter;
import com.mooveit.twittertopics.entities.Token;
import com.mooveit.twittertopics.entities.Trend;
import com.mooveit.twittertopics.entities.TrendList;
import com.mooveit.twittertopics.networking.requests.TokenRequest;
import com.mooveit.twittertopics.networking.requests.TrendsRequest;

import java.util.ArrayList;
import java.util.List;

public class TrendsFragment extends RobospiceListFragment {

    public static final String TAG = "TrendsFragment";

    private String mReturnToken;
    private ArrayAdapter<Trend> mTrendAdapter;
    private List<Trend> mTrends = new ArrayList<>();

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
        fetchTwitterToken();
    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l, view, position, id);
        Trend trend = (Trend) getListAdapter().getItem(position);
        startTweetActivity(trend.getTrend(), position, view.findViewById(R.id.sharp));
    }

    private void initializeFragment() {
        mTrendAdapter = new TrendAdapter(getActivity(), mTrends);
        setListAdapter(mTrendAdapter);
        setListShownNoAnimation(false);
    }

    private void startTweetActivity(String item, int position, View trendText) {
        Intent tweetIntent = new Intent(getActivity(), TweetsActivity.class);

        tweetIntent.putExtra(TweetsActivity.EXTRA_TREND, item);
        tweetIntent.putExtra(TweetsActivity.EXTRA_ACTION_COLOR, position);
        tweetIntent.putExtra(TweetsActivity.EXTRA_TOKEN, mReturnToken);

        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                        getActivity(),
                        trendText,
                        getActivity().getString(R.string.actionbar_transition));

        ActivityCompat.startActivity(getActivity(), tweetIntent, options.toBundle());
    }

    @Override
    public void onRefresh() {
        fetchTrendings();
    }

    private void onTokenResponse(String token) {
        mReturnToken = token;
        getTwitterSpiceManager().setToken(mReturnToken);
        fetchTrendings();
    }

    private void onTrendingsResponse(List<Trend> trends) {
        mTrendAdapter.clear();

        mTrends.addAll(trends);
        mTrendAdapter.notifyDataSetChanged();
        setListShown(true);
    }

    private void fetchTrendings() {

        TrendsRequest trendsRequest = new TrendsRequest();

        getTwitterSpiceManager().execute(trendsRequest,

            new SuccessRequestListener<TrendList[]>(getActivity(), getErrorsHandler()) {
                @Override
                public void onRequestSuccess(TrendList[] trendLists) {
                    onTrendingsResponse(trendLists[0].getList());
                    super.onRequestSuccess(trendLists);
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                    setRefreshing(false);
                }
            }
        );
    }

    private void fetchTwitterToken() {
        TokenRequest tokenRequest = new TokenRequest(getActivity());

        getTwitterSpiceManager().execute(tokenRequest,
                new SuccessRequestListener<Token>(getActivity(), getErrorsHandler()) {
                    @Override
                    public void onRequestSuccess(Token token) {
                        onTokenResponse(token.getAccessToken());
                        super.onRequestSuccess(token);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        setRefreshing(false);
                    }
                }
        );
    }
}

