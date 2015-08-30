package com.mooveit.petstoretestscenarios.activities.pets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.pet.PetActivity;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;

public class PetsActivity extends AppCompatActivity implements PetsFragment.Interactions {

    private static final String PETS_FRAGMENT_TAG = "PetsFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragments_container);

        attachPetsFragment();
    }

    private void attachPetsFragment() {
        PetsFragment petsFragment = (PetsFragment) getSupportFragmentManager().findFragmentByTag(
                PETS_FRAGMENT_TAG
        );

        if (petsFragment != null) {
            return;
        }

        petsFragment = new PetsFragment();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragments_container, petsFragment, PETS_FRAGMENT_TAG).
                commit();

    }

    @Override
    public void onPetAction(Pet pet) {
        Intent petIntent = new Intent(this, PetActivity.class);
        petIntent.putExtra(PetActivity.EXTRA_PET_ID_LONG, pet.getId());

        startActivity(petIntent);
    }
}
