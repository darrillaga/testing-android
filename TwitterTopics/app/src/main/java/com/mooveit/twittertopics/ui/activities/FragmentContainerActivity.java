package com.mooveit.twittertopics.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.mooveit.twittertopics.R;

public abstract class FragmentContainerActivity extends RobospiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        attachFragment(savedInstanceState);
    }

    private void attachFragment(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentAdder fragmentAdder = getFragmentAdder(savedInstanceState);

        if (fragmentManager.findFragmentByTag(fragmentAdder.getTag()) == null) {

            fragmentAdder.
                    add(getSupportFragmentManager().beginTransaction())
                    .commit();
        }
    }

    protected abstract FragmentAdder getFragmentAdder(Bundle parentInstanceState);

    protected int getLayoutId() {
        return R.layout.activity_layout;
    }

    public static class FragmentAdder {
        private Fragment mFragment;
        private String mTag;
        private int mContainerId = R.id.container;

        public FragmentAdder() {

        }

        public FragmentAdder fragment(Fragment fragment) {
            mFragment = fragment;
            return this;
        }

        public FragmentAdder withTag(String tag) {
            mTag = tag;
            return this;
        }

        public FragmentAdder to(int containerId) {
            mContainerId = containerId;
            return this;
        }

        private FragmentTransaction add(FragmentTransaction fragmentTransaction) {
            return fragmentTransaction.add(mContainerId, mFragment, mTag);
        }

        public String getTag() {
            return mTag;
        }
    }
}
