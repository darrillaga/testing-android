package com.mooveit.android.networking.robospice.mock;

import com.google.android.apps.common.testing.ui.espresso.contrib.CountingIdlingResource;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Answer used by Mockito to mock the SpiceManager's execute method
 */
public class CountingIdlingSpiceManagerSpyExecuteAnswer implements Answer {

    private SpiceManager realSpiceManager;
    private CountingIdlingResource countingResource;
    private MockResponseProvider mockResponseProvider;

    /**
     * @param realSpiceManager The spice manager to be mocked
     * @param mockResponseProvider The mock responses provider
     * @param countingResource The CountingIdlingResource to make the test wait for the request
     *                         response
     */
    public CountingIdlingSpiceManagerSpyExecuteAnswer(
            SpiceManager realSpiceManager,
            MockResponseProvider mockResponseProvider,
            CountingIdlingResource countingResource) {

        this.realSpiceManager = realSpiceManager;
        this.mockResponseProvider = mockResponseProvider;
        this.countingResource = countingResource;
    }

    @Override
    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {

        SpringAndroidSpiceRequest request = (SpringAndroidSpiceRequest) invocationOnMock.getArguments()[0];
        Object requestCacheKey = invocationOnMock.getArguments()[1];
        Long cacheExpiryDuration = (Long) invocationOnMock.getArguments()[2];
        RequestListener listener = (RequestListener) invocationOnMock.getArguments()[3];

        countingResource.increment();

        realSpiceManager.execute(
                new SpiceServerMockRequest<Object>(request, mockResponseProvider),
                requestCacheKey,
                cacheExpiryDuration,
                new CountingIdlingRequestListener(listener, countingResource)
        );

        return null;
    }
}
