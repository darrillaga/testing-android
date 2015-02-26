package com.mooveit.twittertopics.networking.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.octo.android.robospice.retry.RetryPolicy;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class BaseRequest<T> extends SpringAndroidSpiceRequest<T> {

    private static final String TAG = "com.mooveit.bancard.networking.requests.BaseRequest";

    public BaseRequest(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public T loadDataFromNetwork() throws Exception {
        HttpEntity entity = new HttpEntity(getBody(), createHeaders());

        ResponseEntity<T> response = getRestTemplate().
                exchange(getRoute(true), getMethod(), entity, getResultType());

        return response.getBody();
    }

    public String getRoute() {
        return getRoute(false);
    }

    public String getRoute(boolean encodeParams) {
        String route = "";

        if (getBaseRoute() != null) {
            route += getBaseRoute();
        }

        if (getPath() != null) {
            route += getPath();
        }

        if (getParams() != null) {
            route += getParams(encodeParams);
        }

        return route;
    }

    public abstract String getBaseRoute();

    public abstract String getPath();

    public final String getParams() {
        return getParams(false);
    }

    public String getParams(boolean encoded) {
        return "";
    }

    public HttpMethod getMethod() {
        return HttpMethod.GET;
    }

    private HttpHeaders createHeaders() {
        try {
            HttpHeaders headers = new HttpHeaders();

            if (getHeaders() != null) {
                headers.putAll(getHeaders());
            }

            HttpHeaders authHeaders = getAuthenticationHeaders();

            headers.putAll(
                    authHeaders
            );

            return headers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HttpHeaders getHeaders() {
        return new HttpHeaders();
    }

    public HttpHeaders getAuthenticationHeaders() {
        return new HttpHeaders();
    }

    public Object getBody() throws Exception {
        return null;
    }

    @Override
    public RetryPolicy getRetryPolicy() {
        return null;
    }
}
