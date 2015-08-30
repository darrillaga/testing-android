package com.mooveit.petstoretestscenarios.activities.pets;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mooveit.petstoretestscenarios.R;
import com.mooveit.petstoretestscenarios.activities.BaseViewHolder;
import com.mooveit.petstoretestscenarios.networking.entities.Pet;
import com.squareup.picasso.Picasso;

public class PetViewHolder extends BaseViewHolder<Pet> {

    private TextView mNameTextView;
    private ImageView mPictureImageView;

    public PetViewHolder(View itemView) {
        super(itemView);

        mNameTextView = (TextView) itemView.findViewById(R.id.name);
        mPictureImageView = (ImageView) itemView.findViewById(R.id.picture);
    }

    @Override
    public void onBind(Pet pet) {
        mNameTextView.setText(pet.getName());

        String photoUrl = pet.getMainPictureUrl();

        if (photoUrl != null) {
            Picasso.with(getContext()).load(photoUrl).into(mPictureImageView);
        }
    }
}
