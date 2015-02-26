package com.mooveit.android.networking.robospice.mock;

import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestCancellationListener;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.octo.android.robospice.retry.RetryPolicy;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * This is a wrapper class for an SpringAndroidSpiceRequest that starts a mockWebServer to return
 * mock requests and removes a remote server dependency for tests
 * @param <T> SpringAndroidSpiceRequest generic
 */
public class SpiceServerMockRequest<T> extends SpringAndroidSpiceRequest<T> {
    private SpringAndroidSpiceRequest<T> request;
    private List<MockResponse> responseList;
    private MockResponseProvider mockResponseProvider;

    public SpiceServerMockRequest(
            SpringAndroidSpiceRequest<T> request,
            MockResponseProvider mockResponseProvider) {

        super(request.getResultType());
        this.request = request;
        this.mockResponseProvider = mockResponseProvider;
    }

    /**
     * Wrapper for loadDataFromNetwork method that starts the mock server before
     * the real loadDataFromNetwork method is called.
     * @return
     * @throws Exception
     */
    @Override
    public T loadDataFromNetwork() throws Exception {
        MockWebServer mockWebServer = new MockWebServer();

        responseList = mockResponseProvider.getResponses();

        Collections.reverse(responseList);

        for (MockResponse response : responseList) {
            mockWebServer.enqueue(response);
        }

        mockWebServer.play();
//        RequestHelper.getInstance().setDomain(mockWebServer.getUrl("/").toString());

        Exception exception = null;
        T data = null;

        try {
            data = request.loadDataFromNetwork();
        } catch (Exception ex) {
            exception = ex;
        }

        mockWebServer.shutdown();

        if (exception != null) {
            throw exception;
        }

        return data;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return null;
    }

    @Override
    public void setRetryPolicy(RetryPolicy retryPolicy) {
        request.setRetryPolicy(retryPolicy);
    }

    @Override
    public void setPriority(int priority) {
        request.setPriority(priority);
    }

    @Override
    public int getPriority() {
        return request.getPriority();
    }

    @Override
    public Class<T> getResultType() {
        return request.getResultType();
    }

    @Override
    public void cancel() {
        request.cancel();
    }


    @Override
    public boolean isCancelled() {
        return request.isCancelled();
    }

    @Override
    public boolean isAggregatable() {
        return request.isAggregatable();
    }

    @Override
    public void setAggregatable(final boolean isAggregatable) {
        request.setAggregatable(isAggregatable);
    }

    @Override
    public void setRequestCancellationListener(final RequestCancellationListener requestCancellationListener) {
        request.setRequestCancellationListener(requestCancellationListener);
    }

    @Override
    public int compareTo(SpiceRequest<T> other) {
        return request.compareTo(other);
    }

    @Override
    public RestTemplate getRestTemplate() {
        return request.getRestTemplate();
    }

    @Override
    public void setRestTemplate(RestTemplate restTemplate) {
        request.setRestTemplate(restTemplate);
    }
}
