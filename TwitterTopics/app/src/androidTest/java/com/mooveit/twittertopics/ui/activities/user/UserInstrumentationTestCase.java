package com.mooveit.twittertopics.ui.activities.user;

import android.test.ActivityInstrumentationTestCase2;

public class UserInstrumentationTestCase
        extends ActivityInstrumentationTestCase2<UserDetailActivity> {

    public UserInstrumentationTestCase() {
        super(UserDetailActivity.class);
    }

    public void testUserDetailActivityLoad() throws Exception {
        getActivity();
    }

}
