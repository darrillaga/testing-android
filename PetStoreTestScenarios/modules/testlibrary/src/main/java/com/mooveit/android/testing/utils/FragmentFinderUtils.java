package com.mooveit.android.testing.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java8.util.stream.Stream;
import java8.util.stream.StreamSupport;

public class FragmentFinderUtils {

    public static class Finder {
        private FragmentManager mFragmentManager;
        private Matcher<Object> mParentFragmentMatcher;
        private Matcher<Object> mFragmentMatcher;
        private String mTag;

        public Finder(FragmentManager fragmentManager) {
            mFragmentManager = fragmentManager;
        }

        public Fragment find() {
            return find(Fragment.class);
        }

        public <T extends Fragment> T find(Class<T> fragmentClass) {

            if (mFragmentMatcher == null) {
                mFragmentMatcher = Matchers.instanceOf(fragmentClass);
            }

            if (mParentFragmentMatcher == null) {
                mParentFragmentMatcher = Matchers.instanceOf(Fragment.class);
            }

            return findFragment(
                    mFragmentManager,
                    mTag,
                    mParentFragmentMatcher,
                    mFragmentMatcher,
                    fragmentClass
            );
        }

        public Finder withParentMatcher(Matcher<Object> matcher) {
            mParentFragmentMatcher = matcher;
            return this;
        }

        public Finder matches(Matcher<Object> matcher) {
            mFragmentMatcher = matcher;
            return this;
        }

        public Finder withTag(String tag) {
            mTag = tag;
            return this;
        }
    }

    private static <T> T findFragment(FragmentManager fragmentManager,
                                                  String tag,
                                                  Matcher<Object> parentFragmentMatcher,
                                                  Matcher<Object> fragmentMatcher,
                                                  Class<T> fragmentClass) {

        Fragment foundFragment = fragmentManager.findFragmentByTag(tag);

        if (isRequiredFragment(foundFragment, parentFragmentMatcher, fragmentMatcher, fragmentClass)) {

            return (T) foundFragment;
        }

        if (fragmentManager.getFragments() == null) {
            return null;
        }

        Stream<T> stream = StreamSupport.stream(fragmentManager.getFragments()).map(
                fragment -> findFragmentInstanceInFragment(
                        fragment,
                        tag,
                        parentFragmentMatcher,
                        fragmentMatcher,
                        fragmentClass
                )
        );

        return stream.filter(fragment -> fragment != null).findFirst().orElse(null);
    }

    private static boolean isRequiredFragment(Fragment fragment, Matcher<Object> parentFragmentMatcher,
                                              Matcher<Object> fragmentMatcher,
                                              Class fragmentClass) {

        return fragmentClass.isInstance(fragment) &&
                fragmentMatcher.matches(fragment) &&
                (
                        fragment.getParentFragment() == null ||
                        parentFragmentMatcher.matches(fragment.getParentFragment())
                );
    }

    private static <T> T findFragmentInstanceInFragment(Fragment fragment,
                                                  String tag,
                                                  Matcher<Object> parentFragmentMatcher,
                                                  Matcher<Object> fragmentMatcher,
                                                  Class<T> fragmentClass) {

        if (tag == null && isRequiredFragment(fragment, parentFragmentMatcher, fragmentMatcher, fragmentClass)) {
            return (T) fragment;
        }

        return findFragment(
                fragment.getChildFragmentManager(),
                tag,
                parentFragmentMatcher,
                fragmentMatcher,
                fragmentClass
        );
    }
}
