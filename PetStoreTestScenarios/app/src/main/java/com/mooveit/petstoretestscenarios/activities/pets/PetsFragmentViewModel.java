package com.mooveit.petstoretestscenarios.activities.pets;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.BaseViewModel;
import com.mooveit.petstoretestscenarios.activities.BasicAdapter;
import com.mooveit.petstoretestscenarios.activities.RecyclerItemClickListener;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;

import java.util.ArrayList;
import java.util.List;

class PetsFragmentViewModel extends BaseViewModel<PetsFragmentViewModel.Interactions>
        implements SwipeRefreshLayout.OnRefreshListener {

    private List<Pet> mPets = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private BasicAdapter<Pet, PetViewHolder> mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public PetsFragmentViewModel(View view, Interactions interactions) {
        super(view, interactions);

        fetchViews();
        setupViews();
    }

    public void fetchPets() {
        mSwipeRefreshLayout.setRefreshing(true);
        getInteractions().fetchPets();
    }

    public void onPetsFetched(List<Pet> pets) {
        mPets.clear();
        mPets.addAll(pets);

        mAdapter.notifyDataSetChanged();

        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void fetchViews() {
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.pets_recycler);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_refresh_layout);
    }

    private void setupViews() {
        setupAdapter();
        setupRecycler();
    }

    private void setupAdapter() {
        mAdapter = new BasicAdapter<>(R.layout.layout_pets_item, mPets, PetViewHolder.class);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setupRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        this::onPetsRecyclerItemClickAction
                )
        );
    }

    private void onPetsRecyclerItemClickAction(View view, int position) {
        PetViewHolder holder = (PetViewHolder) mRecyclerView.
                findViewHolderForAdapterPosition(position);

        getInteractions().onPetAction(holder.getCurrentItem());
    }

    @Override
    public void onRefresh() {
        fetchPets();
    }

    @Override
    public void notifyError(Exception error) {
        super.notifyError(error);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public interface Interactions extends BaseViewModel.Interactions{
        void onPetAction(Pet pet);
        void fetchPets();
    }
}
