package com.mooveit.twittertopics.ui.activities.tweets;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import com.mooveit.twittertopics.ui.activities.BaseActivity;
import com.mooveit.twittertopics.utils.TwitterHelper;

public class TweetsActivity extends BaseActivity {

    public static final String TAG =
            "com.mooveit.twittertopics.ui.activities.tweets.TweetsActivity";

    public final static String EXTRA_TREND = TAG + "Extras:Trend:String";
    public final static String EXTRA_TOKEN = "Extras:Token:String";
    public final static String EXTRA_ACTION_COLOR = "Extras:ActionColor:Integer";

    public final static String EXTRA_SCREEN_NAME = "screen_name";

    @Override
    protected FragmentAdder getFragmentAdder(Bundle parentInstanceState) {
        TweetsFragment tweetsFragment = new TweetsFragment();
        tweetsFragment.setArguments(generateBundle());

        return new FragmentAdder().fragment(tweetsFragment).withTag(TweetsFragment.TAG);
    }

    private Bundle generateBundle() {
        Intent intentTrend = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(TweetsFragment.ARGUMENT_TREND,
                intentTrend.getStringExtra(EXTRA_TREND));

        bundle.putString(TweetsFragment.ARGUMENT_TOKEN,
                intentTrend.getStringExtra(EXTRA_TOKEN));

        return bundle;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void customizeActionBar() {
        Intent intentTrend = getIntent();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setBackgroundDrawable(new ColorDrawable(
                TwitterHelper.getInstance().setTrendCircleColor(
                        intentTrend.getIntExtra(EXTRA_ACTION_COLOR, 10))
        ));

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(intentTrend.getStringExtra(EXTRA_TREND));
        actionBar.show();
    }

    @Override
    @TargetApi(21)
    protected void setLollipopTransitions() {
        Intent intentTrend = getIntent();
        int actionColor = intentTrend.getIntExtra(EXTRA_ACTION_COLOR, 10);

        getWindow().setSharedElementReturnTransition(null);
        getWindow().setStatusBarColor(TwitterHelper.getInstance().setTrendCircleColor(actionColor));

        getWindow().setNavigationBarColor(
                TwitterHelper.getInstance().setTrendCircleColor(actionColor)
        );
    }
}
