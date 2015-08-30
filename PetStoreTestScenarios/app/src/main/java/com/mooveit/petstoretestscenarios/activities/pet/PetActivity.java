package com.mooveit.petstoretestscenarios.activities.pet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;

public class PetActivity extends AppCompatActivity
        implements PetFragment.Interactions,
        SignatureCaptorFragment.Interactions {

    public static final String TAG = "PetActivity:-7332945025879831749";
    public static final String EXTRA_PET_ID_LONG = TAG + ":Extras:PetId:Long";
    private static final String PET_FRAGMENT_TAG = "PetFragmentTag";
    private static final String SIGNATURE_CAPTOR_FRAGMENT_TAG = "SignatureCaptorFragmentTag";
    private static final String IMAGE_PNG_TYPE = "image/png";

    private Pet mPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        removeSignatureIfThereIsNoData();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragments_container);

        attachPetFragment();
    }

    @Override
    public void onSignatureAction(Pet pet) {
        mPet = pet;

        attachSignatureCaptorFragment();
    }

    @Override
    public Pet getPet() {
        return mPet;
    }

    @Override
    public void onSignatureCreated(Uri uri) {
        sendPetSignature(uri);
    }

    private void removeSignatureIfThereIsNoData() {
        if (mPet != null) {
            return;
        }

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(SIGNATURE_CAPTOR_FRAGMENT_TAG);

        if (fragment == null) {
            return;
        }

        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
    }

    private void attachPetFragment() {
        PetFragment petFragment = (PetFragment) getSupportFragmentManager().findFragmentByTag(
                PET_FRAGMENT_TAG
        );

        if (petFragment != null) {
            return;
        }

        petFragment = new PetFragment();
        petFragment.setArguments(buildPetFragmentArguments());

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragments_container, petFragment, PET_FRAGMENT_TAG).
                commit();

    }

    private void attachSignatureCaptorFragment() {
        SignatureCaptorFragment signatureCaptorFragment = (SignatureCaptorFragment)
                getSupportFragmentManager().findFragmentByTag(
                        SIGNATURE_CAPTOR_FRAGMENT_TAG
                );

        if (signatureCaptorFragment != null) {
            return;
        }

        signatureCaptorFragment = new SignatureCaptorFragment();

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragments_container, signatureCaptorFragment, SIGNATURE_CAPTOR_FRAGMENT_TAG).
                addToBackStack(null).
                commit();

    }

    private Bundle buildPetFragmentArguments() {
        Bundle arguments = new Bundle();

        arguments.putLong(
                PetFragment.ARGUMENT_PET_ID,
                getIntent().getLongExtra(EXTRA_PET_ID_LONG, -1)
        );

        return arguments;
    }

    private void sendPetSignature(Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType(IMAGE_PNG_TYPE);

        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }
}
