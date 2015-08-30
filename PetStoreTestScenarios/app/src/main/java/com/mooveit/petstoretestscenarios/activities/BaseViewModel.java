package com.mooveit.petstoretestscenarios.activities;

import android.content.Context;
import android.view.View;

public abstract class BaseViewModel<INTERACTIONS extends BaseViewModel.Interactions> {

    private View mView;
    private INTERACTIONS mInteractions;

    public BaseViewModel(View view, INTERACTIONS interactions) {
        mView = view;
        mInteractions = interactions;
    }

    public void notifyError(Exception error) {
        getInteractions().getErrorsHandler().handle(error);
    }

    public void hideErrors() {
        getInteractions().getErrorsHandler().hide();
    }

    protected View getView() {
        return mView;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    protected INTERACTIONS getInteractions() {
        return mInteractions;
    }

    public interface Interactions {
        ErrorsHandler getErrorsHandler();
    }
}
