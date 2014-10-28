package com.mooveit.android.networking.robospice.mock;

import com.google.android.apps.common.testing.ui.espresso.contrib.CountingIdlingResource;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * This class wraps a RequestListener to use a CountingIdlingResource on API service callbacks
 * @param <T> RequestListener generic
 */
public class CountingIdlingRequestListener<T> implements RequestListener<T> {

    private RequestListener<T> requestListener;
    private CountingIdlingResource countingResource;

    /**
     *
     * @param requestListener
     * @param countingResource
     */
    public CountingIdlingRequestListener(RequestListener<T> requestListener,
                                         CountingIdlingResource countingResource) {

        this.requestListener = requestListener;
        this.countingResource = countingResource;
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        requestListener.onRequestFailure(spiceException);
        countingResource.decrement();
    }

    @Override
    public void onRequestSuccess(T t) {
        requestListener.onRequestSuccess(t);
        countingResource.decrement();
    }
}
