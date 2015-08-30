package com.mooveit.android.testing;

import com.octo.android.robospice.request.SpiceRequest;

public class NonMockedRequestException extends UnsupportedOperationException {

    private static final String MESSAGE_TPL = "A result for %s must be registered";

    public NonMockedRequestException(SpiceRequest request) {
        super(buildMessage(request));
    }

    private static String buildMessage(SpiceRequest request) {
        return String.format(MESSAGE_TPL, request.getClass().getCanonicalName());
    }
}
