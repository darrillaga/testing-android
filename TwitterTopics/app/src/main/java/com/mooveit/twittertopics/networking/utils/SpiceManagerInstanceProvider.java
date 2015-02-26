package com.mooveit.twittertopics.networking.utils;

import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;

public interface SpiceManagerInstanceProvider {

    TwitterRequestSpiceManager provideTwitterRequestSpiceManager();
}
