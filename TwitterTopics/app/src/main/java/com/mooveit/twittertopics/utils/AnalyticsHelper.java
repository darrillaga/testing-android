package com.mooveit.twittertopics.utils;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mooveit.twittertopics.R;

import java.util.HashMap;

public class AnalyticsHelper {

    private static final String PROPERTY_ID = "UA-56767296-1";

    public static enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER,
        ECOMMERCE_TRACKER,
    }

    private Context mContext;

    public AnalyticsHelper(Context context) {
        mContext = context;
    }

    private HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public Tracker getTracker() {
        return getTracker(TrackerName.APP_TRACKER);
    }

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(mContext);

            Tracker t = analytics.newTracker(null);

            switch (trackerId) {
                case APP_TRACKER:
                    t = analytics.newTracker(R.xml.app_tracker);
                case GLOBAL_TRACKER:
                    analytics.newTracker(PROPERTY_ID);
            }

            mTrackers.put(trackerId, t);

        }

        return mTrackers.get(trackerId);
    }
}
