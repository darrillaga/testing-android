package com.mooveit.twittertopics.ui.activities.user;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.mooveit.twittertopics.R;
import com.mooveit.twittertopics.entities.User;
import com.mooveit.twittertopics.parcels.UserParcel;
import com.mooveit.twittertopics.utils.TwitterHelper;
import com.squareup.picasso.Picasso;

public class UserDetailFragment extends Fragment {

    public static final String TAG =
            "com.mooveit.twittertopics.ui.activities.user.UserDetailFragment";

    public final static String ARGUMENT_IMAGE_PROFILE = TAG + "Arguments:ProfileImage:String";
    public final static String ARGUMENT_USER = TAG + "Arguments:User:String";

    private User mUser;

    private ImageView mBackgroundImage;
    private ImageView mProfileImage;
    private TextView mScreenName;
    private TextView mUserName;
    private TextView mDescription;
    private TextView mWebSite;
    private TextView mCountFollowers;
    private TextView mCountFollowing;
    private TextView mCountTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_user_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBundleArguments();
        initializeLayout();
        setUpUserData();
    }

    private void initializeLayout() {
        mUserName = (TextView) getView().findViewById(R.id.user_name);
        mScreenName = (TextView) getView().findViewById(R.id.user_alias);
        mBackgroundImage = (ImageView) getView().findViewById(R.id.background_image);
        mDescription = (TextView) getView().findViewById(R.id.description);
        mWebSite = (TextView) getView().findViewById(R.id.website);
        mCountFollowers = (TextView) getView().findViewById(R.id.count_followers);
        mCountFollowing = (TextView) getView().findViewById(R.id.count_following);
        mCountTweets = (TextView) getView().findViewById(R.id.count_tweets);
        mProfileImage = (ImageView) getView().findViewById(R.id.profile_image);
    }

    private void getBundleArguments() {
        UserParcel userParcel = getArguments().
                getParcelable(ARGUMENT_USER);

        mUser = userParcel.getUser();
    }

    private void setUpProfileImage() {
        Bitmap bitmap = getArguments().
                getParcelable(ARGUMENT_IMAGE_PROFILE);

        if (bitmap == null) {
            Picasso.with(getActivity()).
                    load(mUser.getProfileImageUrl()).
                    transform(TwitterHelper.getInstance().getRoundedTransform()).
                    error(R.drawable.ic_launcher).
                    into(mProfileImage);

        } else {
            Drawable d = new BitmapDrawable(getResources(), bitmap);
            mProfileImage.setBackgroundDrawable(d);
        }
    }

    private void setUpUserData() {
        setUpProfileImage();

        mUserName.setText(mUser.getName());
        mScreenName.setText(mUser.getScreenName());

        if (mUser.getDescription().equals("")) {
            mDescription.setText(getString(R.string.no_description));
        } else {
            mDescription.setText(mUser.getDescription());
        }

        if (mUser.getUrl() == null || mUser.getUrl().equals(getString(R.string.null_str))) {
            mWebSite.setText(getString(R.string.no_website));
        } else {
            mWebSite.setText(mUser.getUrl());
        }

        mCountFollowers.setText(Integer.toString(mUser.getFollowersCount()));
        mCountFollowing.setText(Integer.toString(mUser.getFriendsCount()));
        mCountTweets.setText(Integer.toString(mUser.getStatusesCount()));

        Picasso.with(getActivity()).
                load(mUser.getProfileBackgroundImageUrl()).
                into(mBackgroundImage);
    }

}
