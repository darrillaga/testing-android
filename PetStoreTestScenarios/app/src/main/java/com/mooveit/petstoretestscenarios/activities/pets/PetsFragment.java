package com.mooveit.petstoretestscenarios.activities.pets;

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

import java.util.List;

public class PetsFragment extends Fragment
        implements PetsFragmentViewModel.Interactions, PetsFragmentBusiness.Interactions {

    private PetsFragmentViewModel mPetsFragmentViewModel;
    private PetsFragmentBusiness mPetsFragmentBusiness;
    private ErrorsHandler mErrorsHandler;
    private SpiceManager mSpiceManager;
    private Interactions mInteractions;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mPetsFragmentBusiness = new PetsFragmentBusiness(this);

        setupErrorsHandler();

        mSpiceManager = Application.get(activity).getSpiceManagerFactory().getSpiceManager();

        mInteractions = ActivityHelper.
                ensureFragmentHasAttachedRequiredClassObject(this, Interactions.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pets, container, false);

        mPetsFragmentViewModel = new PetsFragmentViewModel(view, this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSpiceManager.start(getActivity());
        mPetsFragmentViewModel.fetchPets();
    }

    @Override
    public void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
    }

    @Override
    public void onPetAction(Pet pet) {
        mInteractions.onPetAction(pet);
    }

    @Override
    public void fetchPets() {
        mPetsFragmentBusiness.fetchPets();
    }

    @Override
    public ErrorsHandler getErrorsHandler() {
        return mErrorsHandler;
    }

    @Override
    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    @Override
    public void notifyError(Exception exception) {
        mPetsFragmentViewModel.notifyError(exception);
    }

    @Override
    public void onPetsFetched(List<Pet> pets) {
        mPetsFragmentViewModel.onPetsFetched(pets);
    }

    private void setupErrorsHandler() {
        mErrorsHandler = new ErrorsHandler(getActivity());
        mErrorsHandler.setCurrentLayoutId(R.id.errors_container);
    }

    public interface Interactions {
        void onPetAction(Pet pet);
    }
}
