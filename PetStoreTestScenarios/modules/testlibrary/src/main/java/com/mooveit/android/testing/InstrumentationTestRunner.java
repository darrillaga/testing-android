package com.mooveit.android.testing;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

import com.mooveit.android.networking.squarepicasso.PicassoMocker;
import com.mooveit.android.testing.utils.FixturesLoader;

/**
 * Custom instrumentation test runner that allows execute general behaviour for the entire
 * test suite
 */
public class InstrumentationTestRunner extends AndroidJUnitRunner {

//    @Override
//    public void onCreate(Bundle arguments) {
//        super.onCreate(arguments);
//        loadFixtures();
//        mockPicasso();
//    }
//
//    private void loadFixtures() {
//        if (FixturesLoader.getFixtures().isEmpty()) {
//            int fixturesId = getContext().getResources().
//                    getIdentifier("fixtures_path", "string", getContext().getPackageName());
//
//            FixturesLoader.getFixtures().putAll(FixturesLoader.onLoad(
//                    getContext(),
//                    getContext().
//                            getString(fixturesId)
//            ));
//        }
//    }
//
//    private void mockPicasso() {
//        PicassoMocker.getInstance(getTargetContext());
//    }
}
