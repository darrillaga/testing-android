package com.mooveit.petstoretestscenarios.activities.pets;

import com.mooveit.android.networking.SuccessRequestListener;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;
import com.mooveit.petstoretestscenarios.networking.requests.PetsByStatusRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;

import java.util.Arrays;
import java.util.List;

class PetsFragmentBusiness implements SuccessRequestListener.ErrorsNotifier {

    private Interactions mInteractions;

    public PetsFragmentBusiness(Interactions interactions) {
        mInteractions = interactions;
    }

    public void fetchPets() {
        mInteractions.getSpiceManager().execute(
                new PetsByStatusRequest(), new SuccessRequestListener<Pet[]>(this) {
                    @Override
                    public void onSuccess(Pet[] pets) {
                        onSuccessPetsFetch(pets);
                    }

                    @Override
                    public void onError(SpiceException e) {
                        notifyError(e);
                    }
                });
    }

    private void onSuccessPetsFetch(Pet[] pets) {
        mInteractions.onPetsFetched(Arrays.asList(pets));
    }

    @Override
    public void notifyError(Exception exception) {
        mInteractions.notifyError(exception);
    }

    public interface Interactions {
        SpiceManager getSpiceManager();
        void notifyError(Exception exception);
        void onPetsFetched(List<Pet> pets);
    }
}
