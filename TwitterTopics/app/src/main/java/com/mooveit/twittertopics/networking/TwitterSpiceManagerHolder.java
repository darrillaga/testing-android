package com.mooveit.twittertopics.networking;

public interface TwitterSpiceManagerHolder extends TwitterSpiceManagerProvider {

    void setTwitterSpiceManager(TwitterRequestSpiceManager twitterRequestSpiceManager);
}
