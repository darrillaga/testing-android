package com.mooveit.twittertopics.networking.requests;

import com.mooveit.twittertopics.utils.TwitterHelper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public abstract class TwitterRequest<T> extends BaseRequest<T> {

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHORIZATION_PLACEHOLDER = "Bearer ";

    private String mToken;

    public TwitterRequest(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public HttpHeaders getAuthenticationHeaders() {
        HttpHeaders authenticationHeaders = super.getAuthenticationHeaders();

        authenticationHeaders.add(AUTHORIZATION_KEY, AUTHORIZATION_PLACEHOLDER + mToken);
        authenticationHeaders.setContentType(MediaType.APPLICATION_JSON);

        return authenticationHeaders;
    }

    @Override
    public final String getBaseRoute() {
        return TwitterHelper.getInstance().getBaseUrl();
    }

    public void setToken(String token) {
        mToken = token;
    }
}
