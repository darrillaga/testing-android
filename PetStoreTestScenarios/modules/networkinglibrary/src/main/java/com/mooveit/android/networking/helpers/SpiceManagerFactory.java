package com.mooveit.android.networking.helpers;

import com.octo.android.robospice.SpiceManager;

public class SpiceManagerFactory {

    private SpiceManagerInstanceProvider mSpiceManagerInstanceProvider;

    public SpiceManagerFactory(SpiceManagerInstanceProvider spiceManagerInstanceProvider) {
        mSpiceManagerInstanceProvider = spiceManagerInstanceProvider;
    }

    public void setSpiceManagerInstanceProvider(
            SpiceManagerInstanceProvider spiceManagerInstanceProvider) {

        this.mSpiceManagerInstanceProvider = spiceManagerInstanceProvider;
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManagerInstanceProvider.provideSpiceManager();
    }
}
