package com.mooveit.android.networking.robospice.mock;

import com.mooveit.android.testing.utils.FixturesLoader;
import com.squareup.okhttp.mockwebserver.MockResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Helpers for mock response creation.
 */
public class MockServerDataHelper {

    public static final Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();

    static {
        DEFAULT_HEADERS.put("Content-Type", "application/json; charset=utf-8");
    }

    /**
     *
     * Creates MockResponse using body fixture. Status code is 200 by default.
     *
     * @param bodyFixture
     * @return
     */
    public static MockResponse createMockResponse(String bodyFixture) {
        return createMockResponse(bodyFixture, 200, null);
    }

    /**
     *  Creates MockResponse using body fixture and status code provided.
     *
     * @param bodyFixture
     * @param statusCode
     * @return
     */
    public static MockResponse createMockResponse(String bodyFixture, int statusCode) {
        return createMockResponse(bodyFixture, statusCode, null);
    }

    /**
     *  Creates MockResponse using body fixture, status code and headers provided.
     *
     * @param bodyFixture
     * @param statusCode
     * @param headers
     * @return
     */
    public static MockResponse createMockResponse(String bodyFixture, int statusCode, Map<String, String> headers) {
        Map<String, String> definitiveHeaders = new HashMap<String, String>();
        definitiveHeaders.putAll(DEFAULT_HEADERS);

        if (headers != null) {
            definitiveHeaders.putAll(headers);
        }

        MockResponse response = new MockResponse();

        for (Map.Entry<String, String> entry : definitiveHeaders.entrySet()) {
            response.addHeader(entry.getKey(), entry.getValue());
        }

        response.setResponseCode(statusCode);
        response.setBody(FixturesLoader.getFixtures().get(bodyFixture).toString());

        return response;
    }
}
