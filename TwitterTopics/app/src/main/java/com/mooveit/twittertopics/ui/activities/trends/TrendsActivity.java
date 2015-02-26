package com.mooveit.twittertopics.ui.activities.trends;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.transition.Explode;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.ui.activities.BaseActivity;

public class TrendsActivity extends BaseActivity {

    @Override
    protected FragmentAdder getFragmentAdder(Bundle parentInstanceState) {
        return new FragmentAdder().fragment(new TrendsFragment()).withTag(TrendsFragment.TAG);
    }

    @Override
    protected void customizeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(
                getResources().getColor(R.color.action_bar_trend)));
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.action_bar_trend));
        actionBar.show();
    }

    @Override
    @TargetApi(21)
    protected void setLollipopTransitions() {
        getWindow().setExitTransition(new Explode());
        getWindow().setStatusBarColor(getResources().getColor(R.color.action_bar_trend));
        getWindow().setNavigationBarColor(getResources().getColor(R.color.action_bar_trend));
    }
}