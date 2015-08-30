package com.mooveit.android.testing.matchers.header;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.mockito.ArgumentMatcher;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

public class HasEqualHeaders extends ArgumentMatcher<HttpEntity> {

    private HttpHeaders mHeaders;

    public HasEqualHeaders(HttpHeaders headers) {
        mHeaders = headers;
    }

    @Override
    public boolean matches(Object argument) {
        if (argument instanceof HttpEntity) {
            HttpEntity entity = (HttpEntity) argument;

            return (entity.getHeaders() == null && mHeaders == null) ||
                    entity.getHeaders() != null && entity.getHeaders().equals(mHeaders);
        }
        return false;
    }

    public void describeTo(Description description) {
        description.appendText(mHeaders.toString());
    }

    @Factory
    public static <T> Matcher<HttpEntity> hasEqualHeaders(HttpHeaders headers) {
        return new HasEqualHeaders(headers);
    }

}
