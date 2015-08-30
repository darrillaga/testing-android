package com.mooveit.petstoretestscenarios.activities.pet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.Application;
import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.ErrorsHandler;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;
import com.octo.android.robospice.SpiceManager;

import static com.mooveit.petstoretestscenarios.activities.ActivityHelper.ensureFragmentHasAttachedRequiredClassObject;

public class SignatureCaptorFragment extends Fragment
        implements SignatureCaptorViewManager.Interactions,
        SignatureCaptorFragmentBusiness.Interactions {

    private Interactions mInteractions;
    private ErrorsHandler mErrorsHandler;
    private SignatureCaptorFragmentBusiness mSignatureCaptorFragmentBusiness;
    private SignatureCaptorViewManager mSignatureCaptorViewManager;
    private SpiceManager mTasksManager;

    @Override
    public void onAttach(Activity activity) {
        setupInteractionsListener();

        super.onAttach(activity);

        mSignatureCaptorFragmentBusiness = new SignatureCaptorFragmentBusiness(this);

        mTasksManager = Application.get(activity).getTasksManager();

        setupErrorsHandler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signature_captor, container, false);

        mSignatureCaptorViewManager = new SignatureCaptorViewManager(view, this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTasksManager.start(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        mTasksManager.shouldStop();
    }

    @Override
    public Pet getPet() {
        return mInteractions.getPet();
    }

    @Override
    public void onSignatureCreated(Bitmap signature) {
        mSignatureCaptorFragmentBusiness.savePicture(signature);
    }

    private void setupErrorsHandler() {
        mErrorsHandler = new ErrorsHandler(getActivity());
        mErrorsHandler.setCurrentLayoutId(R.id.errors_wrapper);
    }

    @Override
    public ErrorsHandler getErrorsHandler() {
        return mErrorsHandler;
    }

    @Override
    public void onCreatePictureSuccess(Uri uri) {
        mInteractions.onSignatureCreated(uri);
    }

    @Override
    public void notifyError(Exception exception) {
        mSignatureCaptorViewManager.notifyError(exception);
    }

    @Override
    public SpiceManager getTasksManager() {
        return mTasksManager;
    }

    private void setupInteractionsListener() {
        mInteractions = ensureFragmentHasAttachedRequiredClassObject(
                this, Interactions.class
        );
    }

    public interface Interactions {
        Pet getPet();
        void onSignatureCreated(Uri uri);
    }
}
