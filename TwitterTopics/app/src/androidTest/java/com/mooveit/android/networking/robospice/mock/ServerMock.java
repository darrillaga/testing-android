package com.mooveit.android.networking.robospice.mock;

import android.support.test.espresso.contrib.CountingIdlingResource;

import com.mooveit.twittertopics.networking.TwitterRequestSpiceManager;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerHolder;
import com.mooveit.twittertopics.networking.TwitterSpiceManagerProvider;
import com.mooveit.twittertopics.networking.listeners.BaseRequestListener;
import com.mooveit.twittertopics.networking.utils.SpiceManagerInstanceProvider;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

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
     * @param mockResponseProvider
     *
     * @return the SpiceManager mock
     */
    public static SpiceManager mockSpiceManager(SpiceManager realSpiceManager,
                                                MockResponseProvider mockResponseProvider) {
        final CountingIdlingResource countingResource = new CountingIdlingResource("SpiceService");

        SpiceManager espressoSpiceManager = Mockito.spy(realSpiceManager);

        Mockito.doAnswer(
                new CountingIdlingSpiceManagerSpyExecuteAnswer(
                        realSpiceManager,
                        mockResponseProvider,
                        countingResource
                )
        ).
        when(espressoSpiceManager).
        execute(
                Mockito.any(SpringAndroidSpiceRequest.class),
                Mockito.anyObject(),
                Mockito.anyLong(),
                Mockito.any(BaseRequestListener.class)
        );

        registerIdlingResources(countingResource);

        return espressoSpiceManager;
    }

    /**
     * Mock a robospice manager into a specific provider and set it to respond the response provider responses
     *
     * @param realSpiceManager
     * @param mockResponseObjectsProvider
     *
     * @return the SpiceManager mock
     */
    public static <T extends SpiceManager> T mockSpiceManager(
            T realSpiceManager,
            MockResponseObjectsProvider mockResponseObjectsProvider) {

        final CountingIdlingResource countingResource = new CountingIdlingResource("SpiceService");

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
                        Mockito.any(SpiceRequest.class),
                        Mockito.any(RequestListener.class)
                );

        registerIdlingResources(countingResource);

        return espressoSpiceManager;
    }

}
