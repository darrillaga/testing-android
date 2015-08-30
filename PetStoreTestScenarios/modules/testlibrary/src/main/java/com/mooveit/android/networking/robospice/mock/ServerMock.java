package com.mooveit.android.networking.robospice.mock;

import android.support.test.espresso.contrib.CountingIdlingResource;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.CachedSpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.registerIdlingResources;

/**
 * This class provides helpers for Robospice mocking
 */
public class ServerMock {

    /**
     * Mock a robospice manager into a specific provider and set it to respond the response provider responses
     *
     * @param realSpiceManager
     * @param mockResponseObjectsProvider
     * @return the SpiceManager mock
     */
    public static <T extends SpiceManager> T mockSpiceManager(
            T realSpiceManager,
            MockResponseObjectsProvider mockResponseObjectsProvider,
            String countingIdlingResourceName) {

        final CountingIdlingResource countingResource = new CountingIdlingResource(countingIdlingResourceName);
        T espressoSpiceManager = Mockito.spy(realSpiceManager);

        Mockito.doAnswer(
                new CountingIdlingSpiceManagerObjectsSpyExecuteAnswer(
                        realSpiceManager,
                        mockResponseObjectsProvider,
                        countingResource
                )
        ).
                when(espressoSpiceManager).
                execute(
                        Mockito.any(CachedSpiceRequest.class),
                        Mockito.any(RequestListener.class)
                );

        registerIdlingResources(countingResource);

        return espressoSpiceManager;
    }
}
