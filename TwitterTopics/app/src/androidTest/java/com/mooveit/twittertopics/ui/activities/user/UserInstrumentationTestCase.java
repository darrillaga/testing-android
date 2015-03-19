package com.mooveit.twittertopics.ui.activities.user;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.mooveit.twittertopics.entities.User;
import com.mooveit.twittertopics.parcels.UserParcel;

public class UserInstrumentationTestCase
        extends ActivityInstrumentationTestCase2<UserDetailActivity> {

    public UserInstrumentationTestCase() {
        super(UserDetailActivity.class);
    }

    public void testUserDetailActivityLoad() throws Exception {
        Intent intent = new Intent();

        intent.putExtra(UserDetailActivity.EXTRA_USER, new UserParcel(new User()));

        setActivityIntent(intent);
        getActivity();
    }

}
