package com.mooveit.twittertopics.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.mooveit.twittertopics.Application;
import com.mooveit.twittertopics.R;

public abstract class BaseActivity extends FragmentContainerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        customizeActionBar();
        setInternalLollipopTransitions();
    }

    private void setInternalLollipopTransitions() {
        if (Build.VERSION.SDK_INT >= 21) {
            setLollipopTransitions();
        }
    }

    protected void setLollipopTransitions() {
    }

    protected void customizeActionBar() {
    }
}
