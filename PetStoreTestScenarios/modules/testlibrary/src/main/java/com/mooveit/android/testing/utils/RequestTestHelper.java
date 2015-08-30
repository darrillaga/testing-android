package com.mooveit.android.testing.utils;

import android.content.Context;

import com.mooveit.android.dependencies.Injector;
import com.octo.android.robospice.request.SpiceRequest;
import com.octo.android.robospice.request.listener.RequestListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import java8.util.stream.Collectors;
import java8.util.stream.IntStreams;

public class RequestTestHelper {

    public static Object defaultRequestFor(SpiceRequest request, RequestListener requestListener) throws Exception {
        Object result = null;

        return result;
    }

    public static void injectDependencies(SpiceRequest request, RequestListener requestListener, Context context) {
        Injector.inject(request, context);
        Injector.inject(requestListener, context);
    }

}
