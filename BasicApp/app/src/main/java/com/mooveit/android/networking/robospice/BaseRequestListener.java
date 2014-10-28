package com.mooveit.android.networking.robospice;

import android.util.Log;

import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public abstract class BaseRequestListener<T> implements RequestListener<T> {
    private static final String TAG = "BaseRequestListener";

    @Override
    public final void onRequestFailure(SpiceException e) {
        if (e.getCause() instanceof HttpClientErrorException) {
            HttpClientErrorException exception = (HttpClientErrorException) e.getCause();
            if (exception.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                onUnauthorizedRequest(exception);
            }
            else {
                onOtherNetworkException(exception);
            }
        }
        else if (e instanceof RequestCancelledException) {
            onCancelled(e);
        }
        else {
            onUnknownError(e);
        }
    }

    public void onUnauthorizedRequest(HttpClientErrorException e){
        Log.d(TAG, "401 ERROR");
    }

    public void onUnknownError(SpiceException e){
        Log.d(TAG, "Other exception");
    }

    public void onCancelled(SpiceException e) { Log.d(TAG, "Cancelled"); }

    public void onOtherNetworkException(HttpClientErrorException e){ Log.d(TAG, "Other Network exception"); }


}
