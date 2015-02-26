package com.mooveit.twittertopics.parcels;

import android.os.Parcel;
import android.os.Parcelable;

import com.mooveit.twittertopics.entities.User;

public class UserParcel implements Parcelable {

    private User mUser;

    public UserParcel(User user) {
        mUser = user;
    }

    public UserParcel(Parcel in) {
        mUser = new User();

        mUser.setName(in.readString());
        mUser.setScreenName(in.readString());
        mUser.setDescription(in.readString());
        mUser.setUrl(in.readString());
        mUser.setProfileBackgroundImageUrl(in.readString());
        mUser.setProfileImageUrl(in.readString());
        mUser.setFriendsCount(in.readInt());
        mUser.setFollowersCount(in.readInt());
        mUser.setStatusesCount(in.readInt());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUser.getName());
        parcel.writeString(mUser.getScreenName());
        parcel.writeString(mUser.getDescription());
        parcel.writeString(mUser.getUrl());
        parcel.writeString(mUser.getProfileBackgroundImageUrl());
        parcel.writeString(mUser.getProfileImageUrl());
        parcel.writeInt(mUser.getFriendsCount());
        parcel.writeInt(mUser.getFollowersCount());
        parcel.writeInt(mUser.getStatusesCount());
    }

    public User getUser() {
        return mUser;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserParcel createFromParcel(Parcel in) {
            return new UserParcel(in);
        }

        public UserParcel[] newArray(int size) {
            return new UserParcel[size];
        }
    };
}
