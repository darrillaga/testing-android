package com.mooveit.android.networking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mooveit.android.dependencies.ObjectMapperAware;
import com.octo.android.robospice.exception.RequestCancelledException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.text.ParseException;

public abstract class BaseRequestListener<T> implements RequestListener<T>, ObjectMapperAware {

    private ObjectMapper mObjectMapper;

    public BaseRequestListener() {}

    public BaseRequestListener(ObjectMapper objectMapper) {
        mObjectMapper = objectMapper;
    }

    @Override
    public final void onRequestSuccess(T t) {
        onSuccess(t);
        onComplete(null);
    }

    @Override
    public final void onRequestFailure(SpiceException exception) {
        if (exception.getCause() instanceof HttpClientErrorException) {

            handleApiResponseException(exception, (HttpClientErrorException) exception.getCause());

        } else if (exception instanceof RequestCancelledException) {
            onCancelled(exception);
        } else {
            onUnknownError(exception, null);
        }

        onError(exception);
        onComplete(exception);
    }

    public void onSuccess(T t) {
    }

    public void onError(SpiceException e){
    }

    public void onUnknownError(SpiceException e, Exception unknownError){
    }

    public void onCancelled(SpiceException e) {
    }

    public void onApiResponseException(ApiResponseException e) {
    }

    public void onComplete(SpiceException e) {
    }

    private void handleApiResponseException(SpiceException exception,
                                            HttpClientErrorException clientException) {
        try {
            onApiResponseException(createApiResponseException(clientException));
        } catch (Exception apiResponseException) {
            if (apiResponseException instanceof RuntimeException) {
                throw (RuntimeException) apiResponseException;
            } else {
                onUnknownError(exception, apiResponseException);
            }
        }
    }

    private ApiResponseException createApiResponseException(HttpClientErrorException exception)
            throws ParseException, IOException {

        ensureObjectMapper();

        ApiResponseException bancardApiResponseException = new ApiResponseException(
                mObjectMapper.readValue(
                        exception.getResponseBodyAsString(),
                        Object.class
                )
        );

        return bancardApiResponseException;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.mObjectMapper = objectMapper;
    }

    private void ensureObjectMapper() {
        if (mObjectMapper == null) {
            throw new RuntimeException("An ObjectMapper must be injected.");
        }
    }
}
