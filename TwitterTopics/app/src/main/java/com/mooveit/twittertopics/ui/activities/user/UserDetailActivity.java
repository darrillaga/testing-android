package com.mooveit.twittertopics.ui.activities.user;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.Window;

import com.mooveit.twittertopics.ui.activities.BaseActivity;
import com.mooveit.twittertopics.ui.activities.tweets.TweetsActivity;

public class UserDetailActivity extends BaseActivity {

    public static final String TAG =
            "com.mooveit.twittertopics.ui.activities.user.UserDetailActivity";

    public final static String EXTRA_IMAGE_PROFILE = TAG + "Extras:ProfileImage:String";
    public final static String EXTRA_USER = TAG + "Extras:User:String";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected FragmentAdder getFragmentAdder(Bundle parentInstanceState) {
        UserDetailFragment userDetailFragment = new UserDetailFragment();

        Bundle arguments = new Bundle();

        arguments.putParcelable(
                UserDetailFragment.ARGUMENT_USER,
                getIntent().getParcelableExtra(EXTRA_USER)
        );

        arguments.putParcelable(
                UserDetailFragment.ARGUMENT_IMAGE_PROFILE,
                getIntent().getParcelableExtra(EXTRA_IMAGE_PROFILE)
        );

        userDetailFragment.setArguments(arguments);

        return new FragmentAdder().
                fragment(userDetailFragment).withTag(UserDetailFragment.TAG);
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent().getStringExtra(TweetsActivity.EXTRA_SCREEN_NAME));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.show();
    }

    @Override
    @TargetApi(21)
    protected void setLollipopTransitions() {
        getWindow().setEnterTransition(new Slide());
    }
}
