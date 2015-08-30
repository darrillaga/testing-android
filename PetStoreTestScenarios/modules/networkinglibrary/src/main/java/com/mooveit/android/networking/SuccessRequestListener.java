package com.mooveit.android.networking;

import com.octo.android.robospice.persistence.exception.SpiceException;

import java.lang.ref.WeakReference;

public abstract class SuccessRequestListener<T> extends BaseRequestListener<T> {
    private static final String TAG = "SuccessMessageRequestListener";

    private WeakReference<ErrorsNotifier> mWeakErrorsNotifier;

    public SuccessRequestListener(ErrorsNotifier errorsNotifier) {
        mWeakErrorsNotifier = new WeakReference<>(errorsNotifier);
    }

    @Override
    public void onUnknownError(SpiceException e, Exception unknownError) {
        getErrorsNotifier().notifyError(e);
    }

    public void onCancelled(SpiceException e) {
        getErrorsNotifier().notifyError(e);
    }

    public void onApiResponseException(ApiResponseException e) {
        getErrorsNotifier().notifyError(e);
    }

    public ErrorsNotifier getErrorsNotifier() {
        return mWeakErrorsNotifier.get();
    }

    public interface ErrorsNotifier {
        void notifyError(Exception exception);
    }
}
