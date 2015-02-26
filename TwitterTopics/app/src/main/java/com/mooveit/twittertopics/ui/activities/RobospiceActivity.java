package com.mooveit.twittertopics.ui.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;

import com.mooveit.twittertopics.Application;
import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerHolder;
import com.mooveit.twittertopics.networking.services.NetworkingService;
import com.mooveit.twittertopics.ui.ErrorsHandler;

public class RobospiceActivity extends ActionBarActivity
        implements TwitterSpiceManagerHolder {

    private TwitterRequestSpiceManager mSpiceManager;

    private ErrorsHandler mErrorsHandler = new ErrorsHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpiceManager = Application.get(this).getSpiceManagerFactory().
                getTwitterRequestSpiceManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
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

