package com.mooveit.twittertopics.networking.listeners;

import android.app.Activity;
import android.content.Context;

import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.networking.TwitterApiResponseException;
import com.mooveit.twittertopics.ui.ErrorsHandler;
import com.octo.android.robospice.exception.NetworkException;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;

public abstract class SuccessRequestListener<T> extends BaseRequestListener<T> {
    private static final String TAG = "SuccessRequestListener";

    private Context mContext;
    private ErrorsHandler mErrorsHandler;

    public SuccessRequestListener(Context context, ErrorsHandler errorsHandler) {
        super(context);
        mContext = context;
        mErrorsHandler = errorsHandler;
    }

    @Override
    public void onRequestSuccess(T t) {
        super.onRequestSuccess(t);
        mErrorsHandler.hideError();
    }

    public void onUnknownError(SpiceException e) {
        handleSpiceException(e);
    }

    public void onCancelled(SpiceException e) {
        handleSpiceException(e);
    }

    private void handleSpiceException(SpiceException e) {
        String messageId;
        Exception exception = e;

        if (e instanceof NoNetworkException) {
            messageId = mContext.getString(R.string.NoNetworkException);
        } else if (e instanceof NetworkException) {
            messageId = mContext.getString(R.string.NoResponseFromHostError);
        } else {
           messageId = e.getMessage();
        }

        exception = new Exception(messageId, e);
        mErrorsHandler.handleError(mContext, exception);
    }

    public void onApiResponseException(TwitterApiResponseException e) {
        mErrorsHandler.handleError(mContext, e);
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
