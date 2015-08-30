package com.mooveit.android.tasks;

import com.octo.android.robospice.request.SpiceRequest;

public abstract class BaseTask<T> extends SpiceRequest<T> {

    public BaseTask(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public final T loadDataFromNetwork() throws Exception {
        return perform();
    }

    protected abstract T perform() throws Exception;
}
