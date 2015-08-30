package com.mooveit.petstoretestscenarios.activities.pet;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mooveit.android.Application;
import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.ActivityHelper;
import com.mooveit.petstoretestscenarios.activities.ErrorsHandler;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;
import com.octo.android.robospice.SpiceManager;

public class PetFragment extends Fragment implements PetFragmentBusiness.Interactions,
        PetFragmentViewModel.Interactions {

    public static final String TAG = "PetFragment:1571812560152531372";
    public static final String ARGUMENT_PET_ID = TAG + ":Arguments:PetId";

    private PetFragmentViewModel mPetFragmentViewModel;
    private PetFragmentBusiness mPetFragmentBusiness;
    private ErrorsHandler mErrorsHandler;
    private SpiceManager mSpiceManager;
    private Interactions mInteractions;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mPetFragmentBusiness = new PetFragmentBusiness(this, getArguments().getLong(ARGUMENT_PET_ID));

        setupErrorsHandler();

        mSpiceManager = Application.get(activity).getSpiceManagerFactory().getSpiceManager();

        mInteractions = ActivityHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet, container, false);

        mPetFragmentViewModel = new PetFragmentViewModel(view, this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSpiceManager.start(getActivity());
        mPetFragmentViewModel.fetchPet();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
    }

    private void setupErrorsHandler() {
        mErrorsHandler = new ErrorsHandler(getActivity());
        mErrorsHandler.setCurrentLayoutId(R.id.errors_wrapper);
    }

    @Override
    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    @Override
    public void notifyError(Exception exception) {
        mPetFragmentViewModel.notifyError(exception);
    }

    @Override
    public void onPetFetched(Pet pet) {
        mPetFragmentViewModel.onPetFetched(pet);
    }

    @Override
    public ErrorsHandler getErrorsHandler() {
        return mErrorsHandler;
    }

    @Override
    public void onSignatureAction(Pet pet) {
        mInteractions.onSignatureAction(pet);
    }

    @Override
    public void fetchPet() {
        mPetFragmentBusiness.fetchPet();
    }

    public interface Interactions {
        void onSignatureAction(Pet pet);
    }
}
