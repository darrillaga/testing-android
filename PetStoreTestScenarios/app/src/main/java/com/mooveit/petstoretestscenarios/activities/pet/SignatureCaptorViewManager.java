package com.mooveit.petstoretestscenarios.activities.pet;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.BaseViewModel;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;

import com.mooveit.ui.FingerDrawer;

class SignatureCaptorViewManager extends
        BaseViewModel<SignatureCaptorViewManager.Interactions> {

    private FingerDrawer mFingerDrawer;
    private View mSignatureCaptorConfirmButton;
    private View mSignatureCaptorClearButton;

    private ViewGroup mSignatureCreatorActionBar;

    private TextView mPetNameTextView;

    public SignatureCaptorViewManager(View view, Interactions interactions) {
        super(view, interactions);
        fetchViews();
        setupViews();
    }

    private void fetchViews() {
        mFingerDrawer = (FingerDrawer) getView().findViewById(R.id.signature_captor);
        mPetNameTextView = (TextView) getView().findViewById(R.id.pet_name);

        mSignatureCreatorActionBar = (ViewGroup) getView().findViewById(
                R.id.signature_creator_action_bar);

        mSignatureCaptorClearButton = mSignatureCreatorActionBar
                .findViewById(R.id.action_clean);

        mSignatureCaptorConfirmButton = mSignatureCreatorActionBar
                .findViewById(R.id.action_confirm);
    }

    private void setupViews() {
        setUpSignatureCreatorActionBar();

        mPetNameTextView.setText(String.valueOf(getInteractions().getPet().getName()));

        setupSignatureCaptor();
    }

    public void onConfirmClick(View v) {
        getInteractions().onSignatureCreated(mFingerDrawer.saveToBitmap());
    }

    private void setupSignatureCaptor() {
        mFingerDrawer.setOnFingerDrawingStartEventsListener(
                (x, y) -> mSignatureCreatorActionBar.setVisibility(View.GONE)
        );

        mFingerDrawer.setOnFingerDrawingStopEventsListener(
                (x, y) -> mSignatureCreatorActionBar.setVisibility(View.VISIBLE)
        );
    }

    private void onActionClean(View v) {
        mFingerDrawer.clear();
    }

    private void setUpSignatureCreatorActionBar() {
        mSignatureCaptorClearButton.setOnClickListener(this::onActionClean);

        mSignatureCaptorConfirmButton.setOnClickListener(this::onConfirmClick);
    }

    public interface Interactions extends BaseViewModel.Interactions {
        Pet getPet();
        void onSignatureCreated(Bitmap signature);
    }
}
