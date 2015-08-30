package com.mooveit.petstoretestscenarios.activities.pet;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.BaseViewModel;
import com.mooveit.petstoretestscenarios.activities.BasicAdapter;
import com.mooveit.petstoretestscenarios.activities.RecyclerItemClickListener;
import com.mooveit.petstoretestscenarios.activities.pets.PetViewHolder;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;

import java.util.ArrayList;
import java.util.List;

class PetFragmentViewModel extends BaseViewModel<PetFragmentViewModel.Interactions>
        implements SwipeRefreshLayout.OnRefreshListener {

    private Pet mPet;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mSignatureActionView;

    public PetFragmentViewModel(View view, Interactions interactions) {
        super(view, interactions);

        fetchViews();
        setupViews();
    }

    public void fetchPet() {
        mSwipeRefreshLayout.setRefreshing(true);
        getInteractions().fetchPet();
    }

    public void onPetFetched(Pet pet) {
        mPet = pet;
        displayPetData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void fetchViews() {
        mSignatureActionView = getView().findViewById(R.id.action_signature);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
    }

    private void setupViews() {
        mSignatureActionView.setOnClickListener(this::onSignatureAction);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void displayPetData() {

    }

    private void onSignatureAction(View view) {
        getInteractions().onSignatureAction(mPet);
    }

    @Override
    public void onRefresh() {
        fetchPet();
    }

    @Override
    public void notifyError(Exception error) {
        super.notifyError(error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public interface Interactions extends BaseViewModel.Interactions{
        void onSignatureAction(Pet pet);
        void fetchPet();
    }
}
