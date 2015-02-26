package com.mooveit.twittertopics.networking.utils;

import com.mooveit.twittertopics.networking.TwitterSpiceManagerHolder;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerProvider;

public class SpiceManagerUtils {

    public static void injectOrFail(Object from, TwitterSpiceManagerHolder to) {
        if (from instanceof TwitterSpiceManagerProvider) {
            inject((TwitterSpiceManagerProvider) from, to);
        } else {
            throw new RuntimeException(
                    "The provider object must be an instance of " +
                            "com.mooveit.twittertopics.networking.TwitterSpiceManagerProvider"
            );
        }
    }

    public static void inject(TwitterSpiceManagerProvider from, TwitterSpiceManagerHolder to) {
        to.setTwitterSpiceManager(from.getTwitterSpiceManager());
    }
}
