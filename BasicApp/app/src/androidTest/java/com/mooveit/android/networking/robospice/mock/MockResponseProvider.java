package com.mooveit.android.networking.robospice.mock;

import com.squareup.okhttp.mockwebserver.MockResponse;

import java.util.List;

/**
 * Mock provider interface that provides mock server responses
 */
public interface MockResponseProvider {
    /**
     *
     * @return A list of mock responses
     */
    List<MockResponse> getResponses();
}
