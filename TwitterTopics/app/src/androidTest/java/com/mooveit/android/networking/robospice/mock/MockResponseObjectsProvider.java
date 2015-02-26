package com.mooveit.android.networking.robospice.mock;

import com.octo.android.robospice.request.SpiceRequest;
import com.squareup.okhttp.mockwebserver.MockResponse;

import java.util.List;

/**
 * Mock provider interface that provides mock response objects
 */
public interface MockResponseObjectsProvider {
    /**
     *
     * @return A response for an specific class
     */
    Object getResponseFor(SpiceRequest request);
}
