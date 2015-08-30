package com.mooveit.android.testing.utils;

import com.mooveit.android.testing.BuildConfig;

import java.lang.reflect.Method;

public class JacocoExecutionDataDumper {

    public static void setupJacoco() {
        System.setProperty("jacoco-agent.destfile", "/data/data/" + BuildConfig.APPLICATION_ID + "/coverage.ec");
    }

    public static void dump() {
        try {
            Class rt = Class.forName("org.jacoco.agent.rt.RT");
            Method getAgent = rt.getMethod("getAgent");
            Method dump = getAgent.getReturnType().getMethod("dump", boolean.class);
            Object agent = getAgent.invoke(null);
            dump.invoke(agent, false);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
