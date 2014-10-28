package com.mooveit.android.networking.robospice.mock;

import com.google.android.apps.common.testing.ui.espresso.contrib.CountingIdlingResource;
import com.mooveit.android.networking.robospice.BaseRequestListener;
import com.mooveit.android.networking.robospice.RobospiceProvider;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.mockito.Mockito;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.registerIdlingResources;

/**
 * This class provides helpers for Robospice mocking
 */
public class ServerMock {

    /**
     * Mock a robospice manager into a specific provider and set it to respond the response provider responses
     *
     * @param robospiceProvider
     * @param mockResponseProvider
     *
     * @return the SpiceManager mock
     */
    public static SpiceManager mockSpiceManager(RobospiceProvider robospiceProvider, MockResponseProvider mockResponseProvider) {
        final CountingIdlingResource countingResource = new CountingIdlingResource("SpiceService");

        final SpiceManager realSpiceManager = robospiceProvider.getSpiceManager();
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

        robospiceProvider.setSpiceManager(espressoSpiceManager);

        registerIdlingResources(countingResource);

        return espressoSpiceManager;
    }

}
