package com.mooveit.android.networking.requests;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;
import com.octo.android.robospice.retry.RetryPolicy;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class BaseRequest<RESULT_TYPE, BODY_TYPE> extends SpringAndroidSpiceRequest<RESULT_TYPE> {

    private static final String TAG = "com.mooveit.android.networking.requests.BaseRequest";

    private String mBaseRoute;
    private String mPath;
    private HttpMethod mMethod = HttpMethod.GET;
    private HttpHeaders mHeaders = new HttpHeaders();
    private HttpHeaders mAuthenticationHeaders = new HttpHeaders();
    private BODY_TYPE mBody;
    private String mParams;
    private String mEncodedParams;

    public BaseRequest(Class<RESULT_TYPE> clazz) {
        super(clazz);
        setRetryPolicy(null);
    }

    @Override
    public RESULT_TYPE loadDataFromNetwork() throws Exception {
        HttpEntity entity = new HttpEntity(getBody(), createHeaders());

        // TODO: Spring Android has a strange behaviour when encodes variable attributes, it will encode
        // always the Query params and values beyond that them were already encoded
        // eg: if you pass a ?a=x&b=z it will assume you have two params (a and b) and two values
        // (x and z) but if you have only one param a with value x&b=z your query should be like ?a=x%26b%3Dz
        // but spring android is going to re-encode it and your final url is going to be ?a=x%2526b%253Dz and your request is going to be wrong
        ResponseEntity<RESULT_TYPE> response = getRestTemplate().
                exchange(getRoute(), getMethod(), entity, getResultType());


        return response.getBody();
    }

    public final String getRoute() {
        return getRoute(false);
    }

    public final String getRoute(boolean encodeParams) {
        String route = "";

        if (getBaseRoute() != null) {
            route += getBaseRoute();
        }

        if (getPath() != null) {
            route += getPath();
        }

        if (getInternalParams(encodeParams) != null) {
            route += getInternalParams(encodeParams);
        }

        return route;
    }

    public String getBaseRoute() {
        return mBaseRoute;
    }

    public String getPath() {
        return mPath;
    }

    public void setBaseRoute(String baseRoute) {
        mBaseRoute = baseRoute;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getParams() {
        return mParams;
    }

    public void setParams(String params, String encodedParams) {
        mParams = params;
        mEncodedParams = encodedParams;
    }

    public String getEncodedParams() {
        return mEncodedParams;
    }

    public HttpMethod getMethod() {
        return mMethod;
    }

    public void setMethod(HttpMethod method) {
        mMethod = method;
    }

    public HttpHeaders getHeaders() {
        return mHeaders;
    }

    public void setHeaders(HttpHeaders headers) {
        mHeaders = headers;
    }

    public HttpHeaders getAuthenticationHeaders() {
        return mAuthenticationHeaders;
    }

    public void setAuthenticationHeaders(HttpHeaders authenticationHeaders) {
        mAuthenticationHeaders = authenticationHeaders;
    }

    public void setBody(BODY_TYPE body) {
        mBody = body;
    }

    public BODY_TYPE getBody() throws Exception {
        return mBody;
    }

    private String getInternalParams(boolean encoded) {
        return encoded ? getEncodedParams() : getParams();
    }

    private HttpHeaders createHeaders() {
        try {
            HttpHeaders headers = new HttpHeaders();

            if (getHeaders() != null) {
                headers.putAll(getHeaders());
            }

            HttpHeaders authHeaders = getAuthenticationHeaders();

            if (authHeaders != null) {
                headers.putAll(
                        authHeaders
                );
            }

            return headers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
