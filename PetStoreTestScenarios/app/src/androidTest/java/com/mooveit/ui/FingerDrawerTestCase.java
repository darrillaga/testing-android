package com.mooveit.ui;

import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import mooveit.testblankactivity.TestBlankActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.mooveit.android.testing.asserts.ViewAssert.assertEqualBitmap;
import static com.mooveit.android.testing.asserts.ViewAssert.assertNotEqualBitmap;
import static com.mooveit.android.testing.utils.UiThreadUtils.onUiThreadSync;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.lessThan;

@RunWith(AndroidJUnit4.class)
public class FingerDrawerTestCase {

    @Rule
    public ActivityTestRule<TestBlankActivity> mActivityTestRule =
            new ActivityTestRule<>(TestBlankActivity.class);

    private static final int FINGER_DRAWER_ID = TestBlankActivity.VIEW_ID + 1;

    private ViewGroup mParentView;
    private FingerDrawer subject;
    private Bitmap mEmptyBitmap;
    private Bitmap mDrawingBitmap;
    private Bitmap mSecondDrawingBitmap;

    @Before
    public void setup() {
        mParentView = (ViewGroup) mActivityTestRule.getActivity().
                findViewById(TestBlankActivity.VIEW_ID);

        onUiThreadSync(this::setupSubject);
    }

    @After
    public void cleanBitmaps() {
        if (mEmptyBitmap != null) {
            mEmptyBitmap.recycle();
        }

        if (mDrawingBitmap != null) {
            mDrawingBitmap.recycle();
        }

        if (mSecondDrawingBitmap != null) {
            mSecondDrawingBitmap.recycle();
        }
    }

    private void setupSubject() {
        subject = new FingerDrawer(mActivityTestRule.getActivity());

        subject.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        subject.setId(FINGER_DRAWER_ID);

        mParentView.addView(subject);
    }

    @Test
    public void onStartDrawingIsCalledOnListener() {
        AtomicBoolean isCalledFlag = new AtomicBoolean();

        onUiThreadSync(() ->
                        subject.setOnFingerDrawingStartEventsListener(
                                (x, y) -> isCalledFlag.set(true)
                        )
        );

        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        assertTrue(isCalledFlag.get());
    }

    @Test
    public void onStopDrawingIsCalledOnListener() {
        AtomicBoolean isCalledFlag = new AtomicBoolean();

        onUiThreadSync(() ->
                        subject.setOnFingerDrawingStopEventsListener(
                                (x, y) -> isCalledFlag.set(true)
                        )
        );

        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        assertTrue(isCalledFlag.get());
    }

    public void onStopCalledAfterOnStart() {
        AtomicLong eventsTimeDifference = new AtomicLong();

        onUiThreadSync(() -> {
            subject.setOnFingerDrawingStartEventsListener(
                    (x, y) -> eventsTimeDifference.set(System.nanoTime())
            );

            subject.setOnFingerDrawingStopEventsListener(
                    (x, y) -> eventsTimeDifference.set(
                            eventsTimeDifference.get() - System.nanoTime()
                    )
            );
        });

        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        assertThat(eventsTimeDifference.get(), lessThan(0L));
    }

    @Test
    public void emptyBitmapIsGeneratedWithNoDraw() {
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        mDrawingBitmap = subject.saveToBitmap();

        mEmptyBitmap = createEmptyBitmapWithSpecsOfAnother(mDrawingBitmap);

        assertEqualBitmap(mDrawingBitmap, mEmptyBitmap);
    }

    @Test
    public void rightBitmapIsGenerated() {
        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        mDrawingBitmap = subject.saveToBitmap();

        mEmptyBitmap = createEmptyBitmapWithSpecsOfAnother(mDrawingBitmap);

        assertNotEqualBitmap(mDrawingBitmap, mEmptyBitmap);
    }

    @Test
    public void differentMovementsGeneratesDifferentBitmaps() {
        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        mDrawingBitmap = subject.saveToBitmap();

        onUiThreadSync(() -> subject.clear());

        onView(withId(FINGER_DRAWER_ID)).perform(swipeUp());

        mSecondDrawingBitmap = subject.saveToBitmap();

        assertNotEqualBitmap(mDrawingBitmap, mSecondDrawingBitmap);
    }

    @Test
    public void clearGeneratesEmptyBitmap() {
        onView(withId(FINGER_DRAWER_ID)).perform(swipeLeft());

        onUiThreadSync(() -> subject.clear());

        mDrawingBitmap = subject.saveToBitmap();

        mEmptyBitmap = createEmptyBitmapWithSpecsOfAnother(mDrawingBitmap);

        assertEqualBitmap(mDrawingBitmap, mEmptyBitmap);
    }

    private Bitmap createEmptyBitmapWithSpecsOfAnother(Bitmap bitmap) {
        return Bitmap.createBitmap(
                bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig()
        );
    }
}

