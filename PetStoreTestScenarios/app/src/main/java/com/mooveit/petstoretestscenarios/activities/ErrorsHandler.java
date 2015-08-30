package com.mooveit.petstoretestscenarios.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mooveit.petstoretestscenarios.R;

public class ErrorsHandler {

    private static final String MESSAGES_SEPARATOR = "\n";

    private Toast mCurrentToast;
    private ViewGroup mParent;
    private ViewGroup mErrorsWrapper;
    private TextView mErrorTextView;
    private Activity mActivity;
    public int mCurrentLayoutId;

    public ErrorsHandler(Activity activity) {
        this(activity, View.NO_ID);
    }

    public ErrorsHandler(Activity activity, int layoutId) {
        mActivity = activity;
        mCurrentLayoutId = layoutId;
    }

    public void handle(Exception exception) {
        handle(exception, mCurrentLayoutId);
    }

    public void handle(Exception ex, int id) {
        if (mActivity != null && !mActivity.isFinishing()) {
            String message = getExceptionMessage(ex);

            message = message != null ? message : mActivity.getString(R.string.generic_operation_error);

            handleInternal(message, id);
        }
    }

    private void handleInternal(String message, int id) {
        recycleOrCreateErrorWrapperInParentWithId(id);
        prepareErrorsWrapperToBeingShown();

        if (mErrorTextView != null) {
            mErrorTextView.setText(message);
        }

        if (mParent == null) {
            showToastError();
        }
    }

    private void showToastError() {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }

        mCurrentToast = new Toast(mActivity);
        mCurrentToast.setDuration(Toast.LENGTH_LONG);
        mCurrentToast.setView(mErrorsWrapper);
        mCurrentToast.show();
    }

    public void hide() {
        if (!mActivity.isFinishing() && mErrorsWrapper != null) {
            mErrorsWrapper.setVisibility(View.GONE);
        }

        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
    }

    public void setCurrentLayoutId(int currentLayoutId) {
        mCurrentLayoutId = currentLayoutId;
    }

    private void prepareErrorsWrapperToBeingShown() {
        if (mErrorsWrapper != null) {
            mErrorsWrapper.setVisibility(View.VISIBLE);
            mErrorTextView = (TextView) mErrorsWrapper
                    .findViewById(R.id.error_text);
        }
    }

    private void recycleOrCreateErrorWrapperInParentWithId(int id) {
        if (mErrorsWrapper == null || (mParent != null && mParent.getId() != id)) {
            createErrorWrapperFromParentWithId(id);
        }
    }

    private void createErrorWrapperFromParentWithId(int id) {
        ViewGroup contentView = (ViewGroup) (mActivity.findViewById(id));

        mErrorsWrapper = (ViewGroup) LayoutInflater.from(mActivity)
                .inflate(R.layout.layout_errors_wrapper, contentView,
                        false);

        if (contentView != null) {
            assignErrorsWrapperFromParentOrRecycle(contentView);
            mParent = contentView;
        } else {
            mParent = null;
        }
    }

    private void assignErrorsWrapperFromParentOrRecycle(ViewGroup contentView) {
        ViewGroup v = (ViewGroup) contentView
                .findViewById(R.id.errors_wrapper);

        if (v != null) {
            mErrorsWrapper = v;
        } else {
            addErrorsWrapperToParent(contentView);
        }
    }

    private void addErrorsWrapperToParent(ViewGroup contentView) {
        if (contentView instanceof FrameLayout
                || contentView instanceof RelativeLayout) {
            contentView.addView(mErrorsWrapper,
                    contentView.getChildCount());
        } else {
            contentView.addView(mErrorsWrapper, 0);
        }
    }

    private String getExceptionMessage(Exception exception) {
        int errorId = mActivity.getResources().getIdentifier(
                exception.getClass().getSimpleName(),
                R.string.class.getSimpleName(),
                mActivity.getPackageName()
        );

        String result = null;

        try {
            result = mActivity.getString(errorId);
        } catch (Resources.NotFoundException notFoundException) {
//            throw new RuntimeException(
//                    "Message key not found: " + exception.getClass().getSimpleName(),
//                    notFoundException
//            );
        }

        return result;
    }
}
