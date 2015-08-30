package com.mooveit.petstoretestscenarios.activities.pet;

import com.mooveit.android.networking.SuccessRequestListener;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;
import com.mooveit.petstoretestscenarios.networking.requests.PetRequest;
import com.octo.android.robospice.SpiceManager;

class PetFragmentBusiness implements SuccessRequestListener.ErrorsNotifier {

    private Interactions mInteractions;
    private Long mPetId;

    public PetFragmentBusiness(Interactions interactions, Long petId) {
        mInteractions = interactions;
        mPetId = petId;
    }

    public void fetchPet() {
        mInteractions.getSpiceManager().execute(
                new PetRequest(mPetId), new SuccessRequestListener<Pet>(this) {
                    @Override
                    public void onSuccess(Pet pet) {
                        onSuccessPetFetch(pet);
                    }
                });
    }

    private void onSuccessPetFetch(Pet pet) {
        mInteractions.onPetFetched(pet);
    }

    @Override
    public void notifyError(Exception exception) {
        mInteractions.notifyError(exception);
    }

    public interface Interactions {
        SpiceManager getSpiceManager();
        void notifyError(Exception exception);
        void onPetFetched(Pet pet);
    }
}
