package com.mooveit.twittertopics.ui;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ErrorsHandler {

    private Toast mCurrentToast;

    public void handleError(Context context, Exception error) {
        if (context instanceof Activity) {
            handleError((Activity) context, error);
        } else {
            showError(context, error);
        }
    }

    public void handleError(Activity activity, Exception error) {
        showError(activity, error);
    }

    private void showError(Context context, Exception error) {
        hideError();

        mCurrentToast = Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG);
        mCurrentToast.show();
    }

    public boolean hideError() {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
            return true;
        }

        return false;
    }
}
