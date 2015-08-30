package com.mooveit.android.tasks;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public abstract class TaskListener<RESULT> implements RequestListener<RESULT> {

    public abstract void onSuccess(RESULT result);

    public abstract void onError(Exception e);

    @Override
    public void onRequestSuccess(RESULT result) {
        onSuccess(result);
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        onError(spiceException);
    }
}
