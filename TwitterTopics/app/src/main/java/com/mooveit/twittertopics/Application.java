package com.mooveit.twittertopics;

import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.services.NetworkingService;
import com.mooveit.twittertopics.networking.utils.SpiceManagerFactory;
import com.mooveit.twittertopics.networking.utils.SpiceManagerInstanceProvider;
import com.mooveit.twittertopics.utils.AnalyticsHelper;
import com.mooveit.twittertopics.utils.TwitterHelper;
import com.octo.android.robospice.persistence.CacheManager;

import java.util.HashMap;


public class Application extends android.app.Application implements SpiceManagerInstanceProvider {

    private CacheManager mCacheManager = new CacheManager();
    private ObjectMapper mObjectMapper = new ObjectMapper();
    private AnalyticsHelper mAnalyticsHelper;
    private SpiceManagerFactory mSpiceManagerFactory;

    public static Application get(Context context) {
        return (Application) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setUpObjectMapper();

        mAnalyticsHelper = new AnalyticsHelper(this);
        mSpiceManagerFactory = new SpiceManagerFactory(this);

        setUpCrashlytics();

        TwitterHelper.init(getApplicationContext());
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        mObjectMapper = objectMapper;
    }

    public void setCacheManager(CacheManager cacheManager) {
        mCacheManager = cacheManager;
    }

    public ObjectMapper getObjectMapper() {
        return mObjectMapper;
    }

    public CacheManager getCacheManager() {
        return mCacheManager;
    }

    private void setUpObjectMapper() {
        mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public AnalyticsHelper getAnalyticsHelper() {
        return mAnalyticsHelper;
    }

    public void setAnalyticsHelper(AnalyticsHelper analyticsHelper) {
        mAnalyticsHelper = analyticsHelper;
    }

    private void setUpCrashlytics() {
        if (BuildConfig.USE_CRASHLYTICS) {
            Crashlytics.start(this);
        }
    }

    public SpiceManagerFactory getSpiceManagerFactory() {
        return mSpiceManagerFactory;
    }

    @Override
    public TwitterRequestSpiceManager provideTwitterRequestSpiceManager() {
        return new TwitterRequestSpiceManager(NetworkingService.class);
    }
}
