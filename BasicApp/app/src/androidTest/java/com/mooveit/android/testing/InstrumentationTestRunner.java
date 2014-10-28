package com.mooveit.android.testing;

import android.os.Bundle;

import com.google.android.apps.common.testing.testrunner.GoogleInstrumentationTestRunner;
import com.mooveit.android.testing.utils.FixturesLoader;

/**
 * Custom instrumentation test runner that allows execute general behaviour for the entire
 * test suite
 */
public class InstrumentationTestRunner extends GoogleInstrumentationTestRunner {

    @Override
    public void onCreate(Bundle arguments) {
        loadFixtures();
        super.onCreate(arguments);
    }

    private void loadFixtures() {
        if (FixturesLoader.getFixtures().isEmpty()) {
            FixturesLoader.getFixtures().putAll(
                    FixturesLoader.onLoad(
                            getContext(),
                            getContext().
                                    getString(com.mooveit.testing.android.basicapp.test.R.string.fixtures_path)
                    )
            );
        }
    }
}
