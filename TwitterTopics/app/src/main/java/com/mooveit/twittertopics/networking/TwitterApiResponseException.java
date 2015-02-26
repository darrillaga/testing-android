package com.mooveit.twittertopics.networking;

import android.content.Context;
import android.util.Log;

public class TwitterApiResponseException extends Exception {

    public static final String TAG = "TwitterApiResponseException";

    private static final long serialVersionUID = -5858303910602747087L;
    private String mErrorCode;

    private TwitterErrorResponse mErrorResponse;
    private Context mContext;

    public TwitterApiResponseException(Context context) {
        super();
        init(context);
    }

    public TwitterApiResponseException(Context context, String message, Throwable cause) {
        super(message, cause);
        init(context);
    }

    public TwitterApiResponseException(Context context, String message, String errorCode) {
        super(message);
        this.mErrorCode = errorCode;
        init(context);
    }

    public TwitterApiResponseException(Context context, TwitterErrorResponse error) {
        super("Api error message");
        this.mErrorResponse = error;
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        logError();
    }

    @Override
    public String getMessage() {
        if (mErrorResponse != null && !mErrorResponse.getErrors().isEmpty()) {
            StringBuilder errorStr = new StringBuilder();

            for (TwitterError error : mErrorResponse.getErrors()) {

                errorStr.
                        append(getTwitterErrorMessage(error)).
                        append("\n");
            }

            errorStr.delete(errorStr.length() - 1, errorStr.length());

            return errorStr.toString();
        } else {
            return super.getMessage();
        }
    }

    private String getTwitterErrorMessage(TwitterError error) {
        int errorId = retrieveErrorId(error);

        if (errorId != 0) {
            return mContext.getString(errorId);
        } else {
            return error.getMessage();
        }
    }

    private int retrieveErrorId(TwitterError error) {
        return mContext.getResources().getIdentifier(
                "twitter_api_error_" + error.getCode(),
                "string",
                mContext.getApplicationContext().getPackageName()
        );
    }

    public TwitterErrorResponse getErrorResponse() {
        return mErrorResponse;
    }

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String errorCode) {
        this.mErrorCode = errorCode;
    }

    private void logError() {
        String errorMsg = "";
        if (getErrorCode() != null) {
            errorMsg += "Error Code: " + getErrorCode() + ", ";
        }
        if (getMessage() != null) {
            errorMsg += "Error Message: " + getMessage() + ", ";
        }

        if (getCause() != null) {
            errorMsg += " Cause: " + getCause().getMessage();
        }

        if (errorMsg != "") {
            Log.w(TAG, errorMsg);
        } else {
            Log.w(TAG, this);
        }

    }
}
