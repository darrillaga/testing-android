package com.mooveit.twittertopics.networking;

import com.mooveit.twittertopics.networking.requests.TwitterRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

public class TwitterRequestSpiceManager extends SpiceManager {

    private String mToken;

    public TwitterRequestSpiceManager(Class<? extends SpiceService> spiceServiceClass) {
        super(spiceServiceClass);
    }

    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public <T> void execute(CachedSpiceRequest<T> cachedSpiceRequest,
                            RequestListener<T> requestListener) {

        if (cachedSpiceRequest.getSpiceRequest() instanceof TwitterRequest) {
            TwitterRequest<T> twitterRequest =
                    (TwitterRequest<T>) cachedSpiceRequest.getSpiceRequest();

            twitterRequest.setToken(mToken);
        }

        super.execute(cachedSpiceRequest, requestListener);
    }
}
