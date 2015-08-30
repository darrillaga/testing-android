package com.mooveit.android.networking.robospice.mock;

import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

/**
 * Mock provider interface that provides mock response objects
 */
public interface MockResponseObjectsProvider {
    /**
     *
     * @return A response for an specific class
     */
    Object getResponseFor(SpiceRequest request, RequestListener requestListener) throws Exception;
}
