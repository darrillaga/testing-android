package com.mooveit.android.testing.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mooveit.android.testing.TestBlankActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.mooveit.android.testing.utils.UiThreadUtils.onUiThreadSync;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class FragmentFinderUtilsTestCase {

    @Rule
    public ActivityTestRule<TestBlankActivity> mActivityTestRule  =
            new ActivityTestRule<>(TestBlankActivity.class);

    private static final int FRAGMENT_1_CONTAINER_ID = 12341;
    private static final String FRAGMENT_PARENT_1_TAG = "fragmentParent1Tag";

    private static final int FRAGMENT_2_CONTAINER_ID = 12342;
    private static final String FRAGMENT_PARENT_2_TAG = "fragmentParent2Tag";

    private static final int FRAGMENT_1_CHILD_LAYOUT_ID = 123410;
    private static final int FRAGMENT_2_CHILD_LAYOUT_ID = 123420;

    private static final int FRAGMENT_11_CONTAINER_ID = 123411;
    private static final String FRAGMENT_11_TAG = "fragment11Tag";

    private static final int FRAGMENT_12_CONTAINER_ID = 123412;
    private static final String FRAGMENT_12_TAG = "fragment12Tag";

    private static final int FRAGMENT_13_CONTAINER_ID = 123413;
    private static final String FRAGMENT_13_TAG = "fragment13Tag";

    private static final int FRAGMENT_21_CONTAINER_ID = 123421;
    private static final String FRAGMENT_21_TAG = "fragment21Tag";

    private static final int FRAGMENT_22_CONTAINER_ID = 123422;
    private static final String FRAGMENT_22_TAG = "fragment22Tag";

    private Fragment fragmentParent1 = PrincipalParentColorFragment.newInstance(Color.BLACK);
    private Fragment fragment11 = ColorFragment.newInstance(Color.BLUE);
    private Fragment fragment12 = ColorFragment.newInstance(Color.RED);
    private Fragment fragment13 = ColorFragment.newInstance(Color.GREEN);

    private Fragment fragmentParent2 = OtherParentColorFragment.newInstance(Color.GRAY);
    private Fragment fragment21 = ColorFragment.newInstance(Color.BLUE);
    private Fragment fragment22 = ColorFragment.newInstance(Color.GREEN);

    private FragmentManager fragmentManager;

    @Before
    public void before() {
        fragmentManager = mActivityTestRule.getActivity().getSupportFragmentManager();
        setupFragmentsOnMainSync();
    }

    @Test
    public void searchParentFragment() {
        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                withTag(FRAGMENT_PARENT_2_TAG).find();

        assertEquals(fragmentParent2, fragment);
    }

    @Test
    public void searchChildFragment() {
        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                withTag(FRAGMENT_12_TAG).find();

        assertEquals(fragment12, fragment);
    }

    @Test
    public void searchFragmentByClass() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                find(OtherColorFragment.class);

        assertEquals(otherColorFragment, fragment);
    }

    @Test
    public void searchFragmentByTag() {}

    @Test
    public void searchFragmentByFragmentMatcher() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                matches(instanceOf(OtherColorFragment.class)).
                find();

        assertEquals(otherColorFragment, fragment);
    }

    @Test
    public void searchFragmentByParentFragmentMatcher() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                matches(instanceOf(OtherColorFragment.class)).
                withParentMatcher(instanceOf(PrincipalParentColorFragment.class)).
                withTag(FRAGMENT_12_TAG).
                find();

        assertEquals(otherColorFragment, fragment);
    }

    @Test
    public void searchFragmentByParentFragmentMatcherWithoutTag() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                matches(instanceOf(OtherColorFragment.class)).
                withParentMatcher(instanceOf(PrincipalParentColorFragment.class)).
                find();

        assertEquals(otherColorFragment, fragment);
    }

    @Test
    public void notFoundFragmentByParentFragmentMatcherWithWrongTag() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                matches(instanceOf(OtherColorFragment.class)).
                withParentMatcher(instanceOf(PrincipalParentColorFragment.class)).
                withTag(FRAGMENT_13_TAG).
                find();

        assertNotSame(otherColorFragment, fragment);
    }

    @Test
    public void notFoundFragmentWithWrongParentFragmentMatcher() {
        final OtherColorFragment otherColorFragment = setupOtherColorFragment();

        Fragment fragment = new FragmentFinderUtils.Finder(fragmentManager).
                matches(instanceOf(OtherColorFragment.class)).
                withParentMatcher(instanceOf(OtherParentColorFragment.class)).
                withTag(FRAGMENT_12_TAG).
                find();

        assertNotSame(otherColorFragment, fragment);
    }

    private OtherColorFragment setupOtherColorFragment() {
        OtherColorFragment otherColorFragment = OtherColorFragment.newInstance(Color.CYAN);

        setupFragmentIntoParentOnMainSync(
                fragmentParent1.getChildFragmentManager(),
                FRAGMENT_12_TAG,
                (LinearLayout) fragmentParent1.getView().findViewById(FRAGMENT_1_CHILD_LAYOUT_ID),
                otherColorFragment,
                123414
        );

        return otherColorFragment;
    }

    private void setupFragmentsOnMainSync() {
        onUiThreadSync(this::setUpFragments);
    }

    private void setUpFragments() {
        LinearLayout linearLayout = createFragmentsContainer();

        ((ViewGroup) mActivityTestRule.getActivity().
                findViewById(mActivityTestRule.getActivity().VIEW_ID)).
                addView(linearLayout);

        setUpFragmentParent1(linearLayout);
        setUpFragmentParent2(linearLayout);
    }

    private void setUpFragmentParent1(LinearLayout parentLinearLayout) {
        setupFragmentIntoParent(
                fragmentManager, FRAGMENT_PARENT_1_TAG, parentLinearLayout, fragmentParent1, FRAGMENT_1_CONTAINER_ID
        );

        parentLinearLayout = createFragmentsContainer();
        parentLinearLayout.setId(FRAGMENT_1_CHILD_LAYOUT_ID);
        ((ViewGroup) fragmentParent1.getView()).addView(parentLinearLayout);

        setupFragmentIntoParent(
                fragmentParent1.getChildFragmentManager(), FRAGMENT_11_TAG, parentLinearLayout, fragment11, FRAGMENT_11_CONTAINER_ID
        );

        setupFragmentIntoParent(
                fragmentParent1.getChildFragmentManager(), FRAGMENT_12_TAG, parentLinearLayout, fragment12, FRAGMENT_12_CONTAINER_ID
        );

        setupFragmentIntoParent(
                fragmentParent1.getChildFragmentManager(), FRAGMENT_13_TAG, parentLinearLayout, fragment13, FRAGMENT_13_CONTAINER_ID
        );
    }

    private void setUpFragmentParent2(LinearLayout parentLinearLayout) {
        setupFragmentIntoParent(
                fragmentManager, FRAGMENT_PARENT_2_TAG, parentLinearLayout, fragmentParent2, FRAGMENT_2_CONTAINER_ID
        );

        parentLinearLayout = createFragmentsContainer();
        parentLinearLayout.setId(FRAGMENT_2_CHILD_LAYOUT_ID);
        ((ViewGroup) fragmentParent2.getView()).addView(parentLinearLayout);

        setupFragmentIntoParent(
                fragmentParent2.getChildFragmentManager(), FRAGMENT_21_TAG, parentLinearLayout, fragment21, FRAGMENT_21_CONTAINER_ID
        );

        setupFragmentIntoParent(
                fragmentParent2.getChildFragmentManager(), FRAGMENT_22_TAG, parentLinearLayout, fragment22, FRAGMENT_22_CONTAINER_ID
        );
    }

    private void setupFragmentIntoParentOnMainSync(FragmentManager fragmentManager, String tag, LinearLayout layout, Fragment fragment, int id) {
        onUiThreadSync(() -> setupFragmentIntoParent(
                fragmentManager, tag, layout, fragment, id
        ));
    }

    private void setupFragmentIntoParent(FragmentManager fragmentManager, String tag, LinearLayout layout, Fragment fragment, int id) {
        layout.addView(createWeightedFrameLayout(id));
        layout.setWeightSum(layout.getChildCount());

        fragmentManager.beginTransaction().
                replace(id, fragment, tag).
                commit();

        fragmentManager.executePendingTransactions();
    }

    public LinearLayout createFragmentsContainer() {
        LinearLayout linearLayout = new LinearLayout(mActivityTestRule.getActivity());
        linearLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(createWeightedFrameLayout(1));
        linearLayout.setPadding(20, 20, 20, 20);

        return linearLayout;
    }

    public View createWeightedFrameLayout(int id) {
        FrameLayout frameLayout = new FrameLayout(mActivityTestRule.getActivity());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.MATCH_PARENT
        );

        layoutParams.weight = 1;

        frameLayout.setLayoutParams(
                layoutParams
        );

        frameLayout.setId(id);
        frameLayout.setPadding(20, 20, 20, 20);

        return frameLayout;
    }

    public static class ColorFragment extends Fragment {

        public static final String ARGUMENT_COLOR_INT = "Arguments:Color:Int";

        public static final void setColorArgumentToFragment(ColorFragment fragment, int color) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_COLOR_INT, color);

            fragment.setArguments(arguments);
        }

        public static ColorFragment newInstance(int color) {
            ColorFragment fragment = new ColorFragment();
            setColorArgumentToFragment(fragment, color);

            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            FrameLayout frameLayout = new FrameLayout(getActivity());

            frameLayout.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )
            );

            frameLayout.setBackgroundColor(getArguments().getInt(ARGUMENT_COLOR_INT));

            return frameLayout;
        }
    }

    public static class OtherColorFragment extends ColorFragment {
        public static OtherColorFragment newInstance(int color) {
            OtherColorFragment fragment = new OtherColorFragment();
            setColorArgumentToFragment(fragment, color);

            return fragment;
        }
    }

    public static class OtherParentColorFragment extends ColorFragment {
        public static OtherParentColorFragment newInstance(int color) {
            OtherParentColorFragment fragment = new OtherParentColorFragment();
            setColorArgumentToFragment(fragment, color);

            return fragment;
        }
    }

    public static class PrincipalParentColorFragment extends ColorFragment {
        public static PrincipalParentColorFragment newInstance(int color) {
            PrincipalParentColorFragment fragment = new PrincipalParentColorFragment();
            setColorArgumentToFragment(fragment, color);

            return fragment;
        }
    }
}
