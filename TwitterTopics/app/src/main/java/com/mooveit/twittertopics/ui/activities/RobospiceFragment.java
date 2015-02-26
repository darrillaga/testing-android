package com.mooveit.twittertopics.ui.activities;

import android.app.Activity;

import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerHolder;
import com.mooveit.twittertopics.networking.utils.SpiceManagerUtils;
import com.mooveit.twittertopics.ui.ErrorsHandler;

public class RobospiceFragment extends SwipeRefreshListFragment
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

