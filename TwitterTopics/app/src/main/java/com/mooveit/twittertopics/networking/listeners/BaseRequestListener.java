package com.mooveit.twittertopics.networking.listeners;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooveit.twittertopics.Application;
import com.mooveit.twittertopics.networking.TwitterApiResponseException;
import com.mooveit.twittertopics.networking.TwitterErrorResponse;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.springframework.web.client.HttpClientErrorException;

public abstract class BaseRequestListener<T> implements RequestListener<T> {

    private Context mContext;

    public BaseRequestListener(Context context) {
        mContext = context;
    }

    @Override
    public void onRequestSuccess(T t) {
        onComplete();
    }

    @Override
    public final void onRequestFailure(SpiceException e) {

        if (e.getCause() instanceof HttpClientErrorException) {

            onHttpClientError(e, (HttpClientErrorException) e.getCause());
        } else if (e instanceof RequestCancelledException) {

            onCancelled(e);
        } else {

            onUnknownError(e);
        }

        onError(e);
        onComplete();
    }

    private void onHttpClientError(SpiceException originException,
                                   HttpClientErrorException exception) {

        TwitterApiResponseException twitterApiResponseException = parseTwitterError(exception);

        if (twitterApiResponseException != null) {
            onApiResponseException(twitterApiResponseException);
        } else {
            onUnknownError(originException);
        }
    }

    private TwitterApiResponseException parseTwitterError(HttpClientErrorException exception) {
        try {
            ObjectMapper mapper = Application.get(mContext).getObjectMapper();

            return new TwitterApiResponseException(
                    mContext,
                    mapper.readValue(
                            exception.getResponseBodyAsString(),
                            TwitterErrorResponse.class
                    )
            );
        } catch (Exception parseException) {
            return null;
        }
    }

    public void onError(SpiceException e){
    }

    public void onUnknownError(SpiceException e){
    }

    public void onCancelled(SpiceException e) {
    }

    public void onApiResponseException(TwitterApiResponseException e) {
    }

    public void onComplete() {
    }

}
