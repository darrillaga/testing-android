package com.mooveit.twittertopics.networking.utils;

import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;

public class SpiceManagerFactory {

    private SpiceManagerInstanceProvider mSpiceManagerInstanceProvider;

    public SpiceManagerFactory(SpiceManagerInstanceProvider spiceManagerInstanceProvider) {
        mSpiceManagerInstanceProvider = spiceManagerInstanceProvider;
    }

    public void setSpiceManagerInstanceProvider(
            SpiceManagerInstanceProvider spiceManagerInstanceProvider) {

        this.mSpiceManagerInstanceProvider = spiceManagerInstanceProvider;
    }

    public TwitterRequestSpiceManager getTwitterRequestSpiceManager() {
        return mSpiceManagerInstanceProvider.provideTwitterRequestSpiceManager();
    }
}
