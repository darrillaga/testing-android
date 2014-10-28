package com.mooveit.android.networking.robospice;

import com.octo.android.robospice.SpiceManager;

public interface RobospiceProvider {

    public SpiceManager getSpiceManager();

    public void setSpiceManager(SpiceManager spiceManager);
}