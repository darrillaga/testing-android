package com.mooveit.twittertopics.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.Token;
import com.mooveit.twittertopics.entities.Trend;
import com.mooveit.twittertopics.entities.TrendList;
import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerHolder;
import com.mooveit.twittertopics.networking.requests.TokenRequest;
import com.mooveit.twittertopics.networking.requests.TrendsRequest;
import com.mooveit.twittertopics.networking.services.NetworkingService;
import com.mooveit.twittertopics.networking.utils.SpiceManagerUtils;
import com.mooveit.twittertopics.ui.ErrorsHandler;
import com.mooveit.twittertopics.ui.activities.trends.TrendsActivity;
import com.mooveit.twittertopics.ui.activities.tweets.TweetsActivity;
import com.mooveit.twittertopics.ui.adapters.TrendAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;

public class RobospiceListFragment extends SwipeRefreshListFragment
        implements TwitterSpiceManagerHolder {

    private TwitterRequestSpiceManager mSpiceManager;
    private ErrorsHandler mErrorsHandler = new ErrorsHandler();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        SpiceManagerUtils.injectOrFail(activity, this);
    }

    @Override
    public void setTwitterSpiceManager(TwitterRequestSpiceManager twitterRequestSpiceManager) {
        mSpiceManager = twitterRequestSpiceManager;
    }

    @Override
    public TwitterRequestSpiceManager getTwitterSpiceManager() {
        return mSpiceManager;
    }

    public ErrorsHandler getErrorsHandler() {
        return mErrorsHandler;
    }

    public void setErrorsHandler(ErrorsHandler errorsHandler) {
        mErrorsHandler = errorsHandler;
    }
}

