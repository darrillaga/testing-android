package com.mooveit.android.networking.robospice.mock;

import android.support.test.espresso.contrib.CountingIdlingResource;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Answer used by Mockito to mock the SpiceManager's execute method
 */
public class CountingIdlingSpiceManagerObjectsSpyExecuteAnswer implements Answer {

    private SpiceManager mRealSpiceManager;
    private CountingIdlingResource mCountingResource;
    private MockResponseObjectsProvider mMockResponseObjectsProvider;

    /**
     * @param realSpiceManager The spice manager to be mocked
     * @param mockResponseObjectsProvider The mock responses provider
     * @param countingResource The CountingIdlingResource to make the test wait for the request
     *                         response
     */
    public CountingIdlingSpiceManagerObjectsSpyExecuteAnswer(
            SpiceManager realSpiceManager,
            MockResponseObjectsProvider mockResponseObjectsProvider,
            CountingIdlingResource countingResource) {

        mRealSpiceManager = realSpiceManager;
        mMockResponseObjectsProvider = mockResponseObjectsProvider;
        mCountingResource = countingResource;
    }

    @Override
    public Object answer(InvocationOnMock invocationOnMock) throws Throwable {

        SpiceRequest request = (SpiceRequest) invocationOnMock.getArguments()[0];
        RequestListener listener = (RequestListener) invocationOnMock.getArguments()[1];

        mCountingResource.increment();

        mRealSpiceManager.execute(
                new FakeRequest(
                        Object.class,
                        mMockResponseObjectsProvider.getResponseFor(request)
                ),
                new CountingIdlingRequestListener(listener, mCountingResource)
        );

        return null;
    }

    public static class FakeRequest extends SpiceRequest {

        private Object mReturnValue;

        public FakeRequest(Class clazz, Object returnValue) {
            super(clazz);
            mReturnValue = returnValue;
        }

        @Override
        public Object loadDataFromNetwork() throws Exception {
            Thread.sleep(100);

            if (mReturnValue instanceof Exception) {
                throw (Exception) mReturnValue;
            }

            return mReturnValue;
        }

        @Override
        public int compareTo(Object another) {
            return 0;
        }
    }
}
