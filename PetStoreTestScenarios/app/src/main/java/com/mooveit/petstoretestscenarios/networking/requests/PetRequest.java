package com.mooveit.petstoretestscenarios.networking.requests;

import com.mooveit.petstoretestscenarios.networking.entities.Pet;

import org.springframework.web.util.UriComponentsBuilder;

public class PetRequest extends PetStoreRequest<Pet, Object> {

    private static final String PATH = "/pet/%s";

    private Long mPetId;

    public PetRequest(Long petId) {
        super(Pet.class);

        mPetId = petId;

        setRetryPolicy(null);
    }

    @Override
    public void onPrepareRequest() {
        getRequest().setPath(String.format(PATH, mPetId.toString()));
    }
}
