package com.mooveit.twittertopics.networking;

import java.util.List;

public class TwitterErrorResponse {

    public TwitterErrorResponse() {

    }

    private int statusCode;

    private List<TwitterError> errors;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<TwitterError> getErrors() {
        return errors;
    }

    public void setErrors(List<TwitterError> errors) {
        this.errors = errors;
    }
}
